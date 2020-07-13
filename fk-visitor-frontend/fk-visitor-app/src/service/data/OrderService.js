import Qs from 'qs'
import Vue from 'vue'

const ORDER = {
  CRUD_API: '/order',
  QUERY_API: '/order/query',
  QUERY_PAGE_API: '/order/query/page',
  QUERY_ALL_API: '/order/all'
}

export function create (data, config) {
  return Vue.axios.post(ORDER.CRUD_API, data, config)
}

export function update (id, params, config) {
  return Vue.axios.put(ORDER.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.deleted(ORDER.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(ORDER.CRUD_API + '/' + id, config)
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
