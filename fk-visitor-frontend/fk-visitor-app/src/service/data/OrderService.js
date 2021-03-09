import Qs from 'qs'
import Vue from 'vue'

const ORDER = {
  CRUD_API: '/order',
  SIGN_OUT_API: '/order/signout',
  QUERY_API: '/order/query',
  QUERY_HISTORY_API: '/order/query/history',
  QUERY_TODAY_API: '/order/query/today',
  HISTORY_API: '/order/history',
  QUERY_PAGE_API: '/order/query/page',
  QUERY_ALL_API: '/order/all'
}

export function create (params, config) {
  return Vue.axios.post(ORDER.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(ORDER.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function singOut (id, config) {
  return Vue.axios.post(ORDER.SIGN_OUT_API + '/' + id, config)
}

export function del (id, config) {
  return Vue.axios.deleted(ORDER.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(ORDER.CRUD_API + '/' + id, config)
}

export function history (id, config) {
  return Vue.axios.get(ORDER.HISTORY_API + '/' + id, config)
}

export function queryHistory (params, config) {
  return Vue.axios.get(ORDER.QUERY_HISTORY_API, Object.assign({ params: params }, config))
}

export function queryToday (config) {
  return Vue.axios.get(ORDER.QUERY_TODAY_API, config)
}

export function query (params, config) {
  return Vue.axios.get(ORDER.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(ORDER.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(ORDER.QUERY_ALL_API, config)
}
