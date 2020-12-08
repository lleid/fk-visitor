import Qs from 'qs'
import Vue from 'vue'

const PROTOCOL = {
  CRUD_API: '/employee',
  QUERY_API: '/employee/query',
  QUERY_PAGE_API: '/employee/query/page',
  CHECK_NAME_API: '/employee/check/name',
  QUERY_ALL_API: '/employee/all'
}

export function create (params, config) {
  return Vue.axios.post(PROTOCOL.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(PROTOCOL.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.deleted(PROTOCOL.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(PROTOCOL.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(PROTOCOL.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(PROTOCOL.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(PROTOCOL.QUERY_ALL_API, config)
}

export function checkName (params, config) {
  return Vue.axios.get(PROTOCOL.CHECK_NAME_API, Object.assign({ params: params }, config))
}