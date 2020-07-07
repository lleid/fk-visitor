import Qs from 'qs'
import Vue from 'vue'

const STORAGE_UNIT = {
  CRUD_API: '/storage-units',
  QUERY_API: '/storage-units/query',
  QUERY_PAGE_API: '/storage-units/query/page',
  QUERY_ALL_API: '/storage-units/all',
  EXTEND_API: '/storage-units/extend',
  ATTACH_API: '/storage-units/attach',
  DETACH_API: '/storage-units/detach'
}

export function create (params, config) {
  return Vue.axios.post(STORAGE_UNIT.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(STORAGE_UNIT.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.delete(STORAGE_UNIT.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(STORAGE_UNIT.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(STORAGE_UNIT.QUERY_API, { params: params }, config)
}

export function queryPage (params, config) {
  return Vue.axios.get(STORAGE_UNIT.QUERY_PAGE_API, { params: params }, config)
}

export function queryAll (config) {
  return Vue.axios.get(STORAGE_UNIT.QUERY_ALL_API, config)
}

export function extend (params, config) {
  return Vue.axios.post(STORAGE_UNIT.EXTEND_API, Qs.stringify(params, { allowDots: true }), config)
}

export function attach (params, config) {
  return Vue.axios.post(STORAGE_UNIT.ATTACH_API, Qs.stringify(params, { allowDots: true }), config)
}

export function detach (id, config) {
  return Vue.axios.post(STORAGE_UNIT.DETACH_API + '/' + id, config)
}
