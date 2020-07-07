import Qs from 'qs'
import Vue from 'vue'

const TEMPLATE = {
  CRUD_API: '/templates',
  QUERY_API: '/templates/query',
  QUERY_PAGE_API: '/templates/query/page',
  QUERY_ALL_API: '/templates/all',
  FIND_CPU_DISTINCT_ALL_API: '/templates/findCpuDistinctAll',
  FIND_RAM_DISTINCTBYCPU_API: '/templates/findRamDistinctByCpu',
  FIND_SIZE_DISTINCTBYCPUANDRAM_API: '/templates/findSizeDistinctByCpuAndRam'
}

export function create (params, config) {
  return Vue.axios.post(TEMPLATE.CRUD_API, Qs.stringify(params), config)
}

export function del (id, config) {
  return Vue.axios.delete(TEMPLATE.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(TEMPLATE.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(TEMPLATE.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(TEMPLATE.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(TEMPLATE.QUERY_ALL_API, config)
}

export function findCpuDistinctAll (config) {
  return Vue.axios.get(TEMPLATE.FIND_CPU_DISTINCT_ALL_API, config)
}

export function findRamDistinctByCpu (params, config) {
  return Vue.axios.get(TEMPLATE.FIND_RAM_DISTINCTBYCPU_API, Object.assign({ params: params }, config))
}

export function findSizeDistinctByCpuAndRam (params, config) {
  return Vue.axios.get(TEMPLATE.FIND_SIZE_DISTINCTBYCPUANDRAM_API, Object.assign({ params: params }, config))
}
