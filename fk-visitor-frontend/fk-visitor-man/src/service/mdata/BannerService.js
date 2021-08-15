import Qs from 'qs'
import Vue from 'vue'

const BANNER = {
  CRUD_API: '/banner',
  QUERY_API: '/banner/query',
  QUERY_PAGE_API: '/banner/query/page',
  QUERY_ALL_API: '/banner/all'
}

export function create (params, config) {
  return Vue.axios.post(BANNER.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(BANNER.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.delete(BANNER.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(BANNER.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(BANNER.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(BANNER.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(BANNER.QUERY_ALL_API, config)
}
