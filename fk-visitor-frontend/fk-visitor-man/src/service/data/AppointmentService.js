import Qs from 'qs'
import Vue from 'vue'

const APPOINTMENT = {
  CRUD_API: '/appointment',
  QUERY_API: '/appointment/query',
  QUERY_PAGE_API: '/appointment/query/page',
  QUERY_ALL_API: '/appointment/all'
}

export function create (params, config) {
  return Vue.axios.post(APPOINTMENT.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.patch(APPOINTMENT.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.deleted(APPOINTMENT.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(APPOINTMENT.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(APPOINTMENT.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(APPOINTMENT.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (params, config) {
  return Vue.axios.get(APPOINTMENT.QUERY_ALL_API, config)
}
