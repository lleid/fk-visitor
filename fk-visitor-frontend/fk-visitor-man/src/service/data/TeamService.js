import Qs from 'qs'
import Vue from 'vue'

const TEAM = {
  CRUD_API: '/team',
  QUERY_API: '/team/query',
  QUERY_PAGE_API: '/team/query/page',
  CHECK_NAME_API: '/team/check/name',
  QUERY_ALL_API: '/team/all',
  STAFF_API: '/team/staff'
}

export function create (params, config) {
  return Vue.axios.post(TEAM.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(TEAM.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.delete(TEAM.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(TEAM.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(TEAM.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(TEAM.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(TEAM.QUERY_ALL_API, config)
}

export function checkName (params, config) {
  return Vue.axios.get(TEAM.CHECK_NAME_API, Object.assign({ params: params }, config))
}

export function staff (id, params, config) {
  return Vue.axios.post(TEAM.STAFF_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}
