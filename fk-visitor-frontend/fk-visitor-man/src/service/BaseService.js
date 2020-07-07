import axios from 'axios'
import qs from 'qs'

import message from 'ant-design-vue/es/message'
import notification from 'ant-design-vue/es/notification'

import store from '@/store'
import {
  TOKEN_ACTIONS
} from '@/store/modules/token-store'
import {
  OPERATOR_ACTIONS
} from '@/store/modules/operator-store'

import router from '@/router'
import ROUTE_PATH from '@/router/route-paths'

import {
  AUTH_API
} from './AuthService'

const REQ_LOADING_KEY = 'REQ_LOADING_KEY'
const REQ_RESET_CONFIG = {
  paramsSerializer: function (params) {
    return qs.stringify(params, {
      arrayFormat: 'repeat'
    })
  }
}
const REQ_CUSTOM_CONFIG = {
  showLoading: true,
  showSuccess: true,
  showError: true
}

let loadingHider
let requestCount = 0

// 声明一个 Map 用于存储每个请求的标识 和 取消函数
const pendingRequest = new Map()

const addRequest = (config) => {
  const url = [
    config.method,
    config.url,
    qs.stringify(config.params),
    qs.stringify(config.data)
  ].join('&')
  config.cancelToken = config.cancelToken || new axios.CancelToken(cancel => {
    if (!pendingRequest.has(url)) { // 如果 pending 中不存在当前请求，则添加进去
      pendingRequest.set(url, cancel)
    }
  })
}

const removeRequest = (config) => {
  const url = [
    config.method,
    config.url,
    qs.stringify(config.params),
    qs.stringify(config.data)
  ].join('&')
  if (pendingRequest.has(url)) { // 如果在 pending 中存在当前请求标识，需要取消当前请求，并且移除
    const cancel = pendingRequest.get(url)
    cancel(url)
    pendingRequest.delete(url)
  }
}

export const clearRequest = () => {
  for (const [url, cancel] of pendingRequest) {
    cancel(url)
  }
  pendingRequest.clear()
}

const handleRequestComplete = () => {
  requestCount--
  if (requestCount === 0 && typeof loadingHider === 'function') {
    loadingHider()
    loadingHider = undefined
  }
}

const handleRequestError = error => {
  handleRequestComplete()
  if (axios.isCancel(error)) {
    return Promise.reject(error)
  } else {
    notification.error({
      message: '错误',
      description: '网络连接或系统服务异常'
    })
    return Promise.reject(error)
  }
}

const handleNoPermission = (responseData, config) => {
  const errorMessage = responseData.message === undefined ? '用户凭证已过期，请重新登陆' : responseData.message
  if (config.showError === true) {
    notification.error({
      message: '失败',
      description: errorMessage
    })
  }
  forceLogout()
  return Promise.reject(new Error(errorMessage))
}

const forceLogout = async () => {
  clearRequest()
  await store.dispatch(OPERATOR_ACTIONS.LOGOUT)
  router.push({
    path: ROUTE_PATH.LOGIN_PATH
  })
}

// 创建 axios 实例
const BaseService = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL
})

// request interceptor
BaseService.interceptors.request.use(async (config) => {
  if (config.url === AUTH_API.REFRESH_API) {
    if (store.getters.tokenRefreshable) {
      config.headers['K-Client'] = 'ID: ' + store.state.token.clientId
      config.headers['K-Refresh-Token'] = store.state.token.refreshToken
    } else {
      forceLogout()
    }
  } else {
    removeRequest(config)
    addRequest(config)
    if (store.state.token.accessToken !== undefined) {
      try {
        if (!store.getters.tokenAccessable() && store.getters.tokenRefreshable()) {
          await store.dispatch(TOKEN_ACTIONS.REFRESH_TOKEN)
        }
        if (store.getters.tokenAccessable()) {
          config.headers['K-Client'] = 'ID: ' + store.state.token.clientId
          config.headers['K-Access-Token'] = store.state.token.accessToken
        } else {
          forceLogout()
        }
      } catch (e) {
        forceLogout()
      }
    }
  }
  const newConfig = Object.assign({}, REQ_RESET_CONFIG, REQ_CUSTOM_CONFIG, config)
  if (newConfig.showLoading === true && requestCount === 0) {
    loadingHider = message.loading({
      content: '请稍候...',
      key: REQ_LOADING_KEY,
      duration: 0
    })
  }
  requestCount++
  return newConfig
}, handleRequestError)

// response interceptor
BaseService.interceptors.response.use(async (response) => {
  handleRequestComplete()

  const config = response.config
  removeRequest(config) // 在请求结束后，移除本次请求
  if (response.status === 200) {
    const responseData = response.data
    if (responseData !== undefined && responseData.code !== undefined) {
      if (responseData.code === 0) {
        if (config.showSuccess === true) {
          notification.success({
            message: '成功',
            description: responseData.message === undefined ? '操作成功' : responseData.message
          })
        }
        return Promise.resolve(responseData)
      } else if (responseData.code === 4033) {
        if (store.getters.tokenRefreshable()) {
          try {
            await store.dispatch(TOKEN_ACTIONS.REFRESH_TOKEN)
          } catch (e) {
            return handleNoPermission(responseData, config)
          }
          config.headers['K-Client'] = 'ID: ' + store.state.token.clientId
          config.headers['K-Access-Token'] = store.state.token.accessToken
          const retryResponse = await axios.request(config)
          return Promise.resolve(retryResponse.data)
        } else {
          return handleNoPermission(responseData, config)
        }
      } else {
        let errorMessage = responseData.message === undefined ? '操作失败' : responseData.message
        if (responseData.values !== undefined && responseData.values.detail !== undefined && typeof responseData.values.detail === 'string') {
          errorMessage = responseData.values.detail
        }
        if (config.showError === true) {
          notification.error({
            message: '失败',
            description: errorMessage
          })
        }
        return Promise.reject(new Error(errorMessage))
      }
    } else {
      return Promise.resolve(responseData)
    }
  } else {
    const errorMessage = '系统服务异常(' + response.status + ')'
    if (config.showError === true) {
      notification.error({
        message: '异常',
        description: errorMessage
      })
    }
    return Promise.reject(new Error(errorMessage))
  }
}, handleRequestError)

export default BaseService
