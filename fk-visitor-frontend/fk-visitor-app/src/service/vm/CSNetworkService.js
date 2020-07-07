import Qs from 'qs'
import Vue from 'vue'

const CSNETWORK = {
  CRUD_API: '/networks',
  QUERY_API: '/networks/query',
  QUERY_PAGE_API: '/networks/query/page',
  QUERY_ALL_API: '/networks/all'
}

export function create (params, config) {
  return Vue.axios.post(CSNETWORK.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(CSNETWORK.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.deleted(CSNETWORK.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(CSNETWORK.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(CSNETWORK.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(CSNETWORK.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(CSNETWORK.QUERY_ALL_API, config)
}
