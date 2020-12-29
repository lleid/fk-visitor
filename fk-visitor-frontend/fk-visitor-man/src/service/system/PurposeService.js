import Qs from 'qs'
import Vue from 'vue'

const PURPOSE = {
  CRUD_API: '/purpose',
  QUERY_API: '/purpose/query',
  QUERY_PAGE_API: '/purpose/query/page',
  QUERY_ALL_API: '/purpose/all',
  CHECK_CNNAME: '/purpose/check/cnname',
  CHECK_ENNAME: '/purpose/check/enname'
}

export function create (params, config) {
  return Vue.axios.post(PURPOSE.CRUD_API, Qs.stringify(params), config)
}

export function update (id, params, config) {
  return Vue.axios.patch(PURPOSE.CRUD_API + '/' + id, Qs.stringify(params), config)
}

export function del (id, config) {
  return Vue.axios.delete(PURPOSE.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(PURPOSE.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(PURPOSE.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(PURPOSE.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(PURPOSE.QUERY_ALL_API, config)
}

export function checkCnName (params, config) {
  return Vue.axios.get(PURPOSE.CHECK_CNNAME, Object.assign({ params: params }, config))
}

export function checkEnName (params, config) {
  return Vue.axios.get(PURPOSE.CHECK_ENNAME, Object.assign({ params: params }, config))
}
