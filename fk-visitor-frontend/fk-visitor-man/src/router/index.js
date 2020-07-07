import Vue from 'vue'
import Router from 'vue-router'

import store from '@/store'

import { DYNAMIC_ROUTES } from './routes/dynamic-routes'
import { LOGIN_ROUTES, BASIC_ROUTES } from './routes/basic-routes'

import ROUTE_PATH from './route-paths'
import * as RouteConverter from './route-converter'

import { APP_MUTATIONS } from '@/store/modules/app-store'
import { TOKEN_ACTIONS } from '@/store/modules/token-store'
import { OPERATOR_ACTIONS } from '@/store/modules/operator-store'

import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style

NProgress.configure({ showSpinner: false })

Vue.use(Router)

const setTitle = function (title) {
  document.title = title
  const ua = navigator.userAgent
  // eslint-disable-next-line
  const regex = /\bMicroMessenger\/([\d\.]+)/
  if (regex.test(ua) && /ip(hone|od|ad)/i.test(ua)) {
    const i = document.createElement('iframe')
    i.src = '/favicon.ico'
    i.style.display = 'none'
    i.onload = function () {
      setTimeout(function () {
        i.remove()
      }, 9)
    }
    document.body.appendChild(i)
  }
}

const APP_TITLE = process.env.VUE_APP_NAME
const APP_ROUTES = BASIC_ROUTES.concat(DYNAMIC_ROUTES).concat({
  path: '*',
  redirect: ROUTE_PATH.ERROR_PATH.ERROR_404_PATH
})

const APP_LOGIN_PATH = RouteConverter.getPaths(LOGIN_ROUTES)
const APP_BASIC_PATH = RouteConverter.getPaths(BASIC_ROUTES)

const router = new Router({
  mode: process.env.NODE_ENV === 'development' ? 'hash' : 'history',
  base: process.env.BASE_URL,
  scrollBehavior: () => ({ y: 0 }),
  routes: APP_ROUTES
})

const forceLogout = async (to, next) => {
  await store.dispatch(OPERATOR_ACTIONS.LOGOUT)
  next({ path: ROUTE_PATH.LOGIN_PATH, query: { redirect: to.path } })
}

const accessableNext = async (to, from, next) => {
  try {
    if (!store.getters.isOperatorLoaded) {
      await store.dispatch(OPERATOR_ACTIONS.INIT)
    }
    if (APP_LOGIN_PATH.includes(to.path)) {
      next(false)
      afterRoute()
    } else if (APP_BASIC_PATH.includes(to.path) || store.state.operator.urls.includes(to.path)) {
      next()
    } else {
      next({ path: ROUTE_PATH.HOME_PATH })
    }
  } catch (e) {
    await forceLogout(to, next)
  }
}

const afterRoute = () => {
  NProgress.done() // finish progress bar
  store.commit(APP_MUTATIONS.UPDATE_LOADING_STATE, false)
}

router.beforeEach(async (to, from, next) => {
  NProgress.start() // start progress bar
  store.commit(APP_MUTATIONS.UPDATE_LOADING_STATE, true)
  to.meta && to.meta.title ? setTitle(`${to.meta.title} - ${APP_TITLE}`) : setTitle(`${APP_TITLE}`)
  if (store.state.token.accessToken === undefined) {
    if (APP_LOGIN_PATH.includes(to.path)) {
      next()
    } else {
      next({ path: ROUTE_PATH.LOGIN_PATH, query: { redirect: to.path } })
    }
  } else {
    if (store.getters.tokenAccessable()) {
      await accessableNext(to, from, next)
    } else if (store.getters.tokenRefreshable()) {
      try {
        await store.dispatch(TOKEN_ACTIONS.REFRESH_TOKEN)
        await accessableNext(to, from, next)
      } catch (e) {
        await forceLogout(to, next)
      }
    } else {
      await forceLogout(to, next)
    }
  }
})

router.afterEach(() => {
  afterRoute()
})

export default router
