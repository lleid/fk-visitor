import Qs from 'qs'
import Vue from 'vue'

const CUSTOMER = {
  CRUD_API: '/customer',
  QUERY_API: '/customer/query',
  QUERY_PAGE_API: '/customer/query/page',
  QUERY_ALL_API: '/customer/all'
}

export function create (params, config) {
  return Vue.axios.post(CUSTOMER.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(CUSTOMER.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.deleted(CUSTOMER.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(CUSTOMER.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(CUSTOMER.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(CUSTOMER.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(CUSTOMER.QUERY_ALL_API, config)
}
