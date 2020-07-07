import Qs from 'qs'
import Vue from 'vue'

const CSSECURITYGROUP = {
  CRUD_API: '/security-groups',
  QUERY_API: '/security-groups/query',
  QUERY_PAGE_API: '/security-groups/query/page',
  QUERY_ALL_API: '/security-groups/all'
}

export function create (params, config) {
  return Vue.axios.post(CSSECURITYGROUP.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(CSSECURITYGROUP.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.deleted(CSSECURITYGROUP.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(CSSECURITYGROUP.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(CSSECURITYGROUP.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(CSSECURITYGROUP.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(CSSECURITYGROUP.QUERY_ALL_API, config)
}
