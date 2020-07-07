import Qs from 'qs'
import Vue from 'vue'

const INSTANCE = {
  CRUD_API: '/instances',
  QUERY_API: '/instances/query',
  QUERY_PAGE_API: '/instances/query/page',
  QUERY_ALL_API: '/instances/all'
}

export function create (params, config) {
  console.log(Qs.stringify(params, { indices: false }))
  return Vue.axios.post(INSTANCE.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(INSTANCE.CRUD_API + '/' + id, Qs.stringify(params), config)
}

export function del (id, config) {
  return Vue.axios.deleted(INSTANCE.CRUD_API + '/' + id, config)
}

export function start (id, config) {
  return Vue.axios.get(INSTANCE.CRUD_API + '/' + id + '/start', config)
}

export function stop (id, config) {
  return Vue.axios.get(INSTANCE.CRUD_API + '/' + id + '/stop', config)
}

export function get (id, config) {
  return Vue.axios.get(INSTANCE.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(INSTANCE.QUERY_API, { params: params }, config)
}

export function queryPage (params, config) {
  return Vue.axios.get(INSTANCE.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(INSTANCE.QUERY_ALL_API, config)
}
