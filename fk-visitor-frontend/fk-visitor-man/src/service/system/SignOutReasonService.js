import Qs from 'qs'
import Vue from 'vue'

const SIGNOUTREASON = {
  CRUD_API: '/signoutreason',
  QUERY_API: '/signoutreason/query',
  QUERY_PAGE_API: '/signoutreason/query/page',
  CHECK_NAME_API: '/signoutreason/check/name',
  QUERY_ALL_API: '/signoutreason/all'
}

export function create (params, config) {
  return Vue.axios.post(SIGNOUTREASON.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(SIGNOUTREASON.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.deleted(SIGNOUTREASON.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(SIGNOUTREASON.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(SIGNOUTREASON.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(SIGNOUTREASON.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(SIGNOUTREASON.QUERY_ALL_API, config)
}

export function checkName (params, config) {
  return Vue.axios.get(SIGNOUTREASON.CHECK_NAME_API, Object.assign({ params: params }, config))
}
