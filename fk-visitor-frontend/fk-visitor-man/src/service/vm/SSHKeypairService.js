import Qs from 'qs'
import Vue from 'vue'

const SSHKEYPAIR = {
  CRUD_API: '/keypairs',
  QUERY_API: '/keypairs/query',
  QUERY_PAGE_API: '/keypairs/query/page',
  QUERY_ALL_API: '/keypairs/all'
}

export function create (params, config) {
  return Vue.axios.post(SSHKEYPAIR.CRUD_API, Qs.stringify(params), config)
}

export function update (id, params, config) {
  return Vue.axios.put(SSHKEYPAIR.CRUD_API + '/' + id, Qs.stringify(params), config)
}

export function del (id, config) {
  return Vue.axios.deleted(SSHKEYPAIR.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(SSHKEYPAIR.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(SSHKEYPAIR.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(SSHKEYPAIR.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(SSHKEYPAIR.QUERY_ALL_API, config)
}
