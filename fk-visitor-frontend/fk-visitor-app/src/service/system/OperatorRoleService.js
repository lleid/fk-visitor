import Vue from 'vue'
import qs from 'qs'

export const OPERATOR_ROLE_API = {
  CRUD_API: '/operator/role',
  QUERY_API: '/operator/role/query',
  QUERY_PAGE_API: '/operator/role/query/page',
  QUERY_ALL_API: '/operator/role/all',
  CHECK_NAME_API: '/operator/role/check/name',
  CHECK_KEY_CODE_API: '/operator/role/check/key-code'
}

export function create (params, config) {
  return Vue.axios.post(OPERATOR_ROLE_API.CRUD_API, qs.stringify(params), config)
}

export function update (id, params, config) {
  return Vue.axios.put(OPERATOR_ROLE_API.CRUD_API + '/' + id, qs.stringify(params), config)
}

export function del (id, config) {
  return Vue.axios.delete(OPERATOR_ROLE_API.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(OPERATOR_ROLE_API.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(OPERATOR_ROLE_API.QUERY_API, Object.assign({ params: params }, config))
}

export function queryByPage (params, config) {
  return Vue.axios.get(OPERATOR_ROLE_API.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(OPERATOR_ROLE_API.QUERY_ALL_API, config)
}

export function checkName (params, config) {
  return Vue.axios.get(OPERATOR_ROLE_API.CHECK_NAME_API, Object.assign({ params: params }, config))
}

export function checkKeyCode (params, config) {
  return Vue.axios.get(OPERATOR_ROLE_API.CHECK_KEY_CODE_API, Object.assign({ params: params }, config))
}
