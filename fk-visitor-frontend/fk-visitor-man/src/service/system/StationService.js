import Qs from 'qs'
import Vue from 'vue'

const STATION = {
  CRUD_API: '/station',
  QUERY_API: '/station/query',
  QUERY_PAGE_API: '/station/query/page',
  QUERY_ALL_API: '/station/all',
  CHECK_NAME: '/station/check/name'
}

export function create (params, config) {
  return Vue.axios.post(STATION.CRUD_API, Qs.stringify(params), config)
}

export function update (id, params, config) {
  return Vue.axios.patch(STATION.CRUD_API + '/' + id, Qs.stringify(params), config)
}

export function del (id, config) {
  return Vue.axios.delete(STATION.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(STATION.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(STATION.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(STATION.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(STATION.QUERY_ALL_API, config)
}

export function checkName (params, config) {
  return Vue.axios.get(STATION.CHECK_NAME, Object.assign({ params: params }, config))
}
