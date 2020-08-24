import Qs from 'qs'
import Vue from 'vue'

const VISIT_AREA = {
  CRUD_API: '/visit/area',
  QUERY_API: '/visit/area/query',
  QUERY_PAGE_API: '/visit/area/query/page',
  QUERY_ALL_API: '/visit/area/all',
  CHECK_CNNAME: '/visit/area/check/cnname',
  CHECK_ENNAME: '/visit/area/check/enname'
}

export function create (params, config) {
  return Vue.axios.post(VISIT_AREA.CRUD_API, Qs.stringify(params), config)
}

export function update (id, params, config) {
  return Vue.axios.put(VISIT_AREA.CRUD_API + '/' + id, Qs.stringify(params), config)
}

export function del (id, config) {
  return Vue.axios.delete(VISIT_AREA.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(VISIT_AREA.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(VISIT_AREA.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(VISIT_AREA.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(VISIT_AREA.QUERY_ALL_API, config)
}

export function checkCnName (params, config) {
  return Vue.axios.get(VISIT_AREA.CHECK_CNNAME, Object.assign({ params: params }, config))
}

export function checkEnName (params, config) {
  return Vue.axios.get(VISIT_AREA.CHECK_ENNAME, Object.assign({ params: params }, config))
}
