import qs from 'qs'
import Vue from 'vue'

const OPERATOR = {
  CRUD_API: '/operator',
  REGISTER_API: '/pub/register',
  QUERY_API: '/operator/query',
  QUERY_PAGE_API: '/operator/query/page',
  QUERY_ALL_API: '/operator/all',
  CREATE_CHECK_USERNAME: '/operator/check/create/username',
  CREATE_CHECK_MOBILE: '/operator/check/create/mobile',
  CHECK_USERNAME: '/operator/check/username',
  CHECK_MOBILE: '/operator/check/mobile',
  CHECK_EMAIL: '/operator/check/email'
}

export function create (params, config) {
  return Vue.axios.post(OPERATOR.CRUD_API, qs.stringify(params, { allowDots: true }), config)
}

export function register (params, config) {
  return Vue.axios.post(OPERATOR.REGISTER_API, qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(OPERATOR.CRUD_API + '/' + id, qs.stringify(params, { allowDots: true }), config)
}

export function patch (id, params, config) {
  return Vue.axios.patch(OPERATOR.CRUD_API + '/' + id, qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.delete(OPERATOR.CRUD_API + '/' + id, config)
}

export function batchDel (params, config) {
  return Vue.axios.delete(OPERATOR.CRUD_API + '/delete', Object.assign({ params: params }, config))
}

export function get (id, config) {
  return Vue.axios.get(OPERATOR.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(OPERATOR.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(OPERATOR.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(OPERATOR.QUERY_ALL_API, config)
}

export function checkUsername (params, config) {
  return Vue.axios.get(OPERATOR.CHECK_USERNAME, Object.assign({ params: params }, config))
}

export function checkMobile (params, config) {
  return Vue.axios.get(OPERATOR.CHECK_MOBILE, Object.assign({ params: params }, config))
}

export function checkEmail (params, config) {
  return Vue.axios.get(OPERATOR.CHECK_EMAIL, Object.assign({ params: params }, config))
}
