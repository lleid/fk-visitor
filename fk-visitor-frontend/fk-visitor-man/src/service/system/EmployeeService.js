import Qs from 'qs'
import Vue from 'vue'

const EMPLOYEE = {
  CRUD_API: '/employee',
  QUERY_API: '/employee/query',
  QUERY_PAGE_API: '/employee/query/page',
  CHECK_NAME_API: '/employee/check/name',
  QUERY_ALL_API: '/employee/all',
  UPLOAD_API: '/employee/import'
}

export function create (params, config) {
  return Vue.axios.post(EMPLOYEE.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.patch(EMPLOYEE.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.delete(EMPLOYEE.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(EMPLOYEE.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(EMPLOYEE.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(EMPLOYEE.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(EMPLOYEE.QUERY_ALL_API, config)
}

export function checkName (params, config) {
  return Vue.axios.get(EMPLOYEE.CHECK_NAME_API, Object.assign({ params: params }, config))
}

export function upload (forms, config) {
  return Vue.axios.post(EMPLOYEE.UPLOAD_API, forms, config)
}
