import Qs from 'qs'
import Vue from 'vue'

const SIGN_OUT_REASON = {
  CRUD_API: '/signoutreason',
  QUERY_API: '/signoutreason/query',
  QUERY_PAGE_API: '/signoutreason/query/page',
  CHECK_NAME_API: '/signoutreason/check/name',
  QUERY_ALL_API: '/signoutreason/all'
}

export function create (params, config) {
  return Vue.axios.post(SIGN_OUT_REASON.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.patch(SIGN_OUT_REASON.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.delete(SIGN_OUT_REASON.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(SIGN_OUT_REASON.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(SIGN_OUT_REASON.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(SIGN_OUT_REASON.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(SIGN_OUT_REASON.QUERY_ALL_API, config)
}

export function checkName (params, config) {
  return Vue.axios.get(SIGN_OUT_REASON.CHECK_NAME_API, Object.assign({ params: params }, config))
}
