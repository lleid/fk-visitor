import Qs from 'qs'
import Vue from 'vue'

const OPERATOR_GROUP = {
  CRUD_API: '/operator-group',
  QUERY_API: '/operator-group/query',
  QUERY_PAGE_API: '/operator-group/query/page',
  QUERY_ALL_API: '/operator-group/all',
  QUERY_GROUPED_API: '/operator-group/grouped',
  ADD_OPERATORS_API: '/operator-group/add/operator'
}

export function create (params, config) {
  return Vue.axios.post(OPERATOR_GROUP.CRUD_API, Qs.stringify(params), config)
}

export function update (id, params, config) {
  return Vue.axios.put(OPERATOR_GROUP.CRUD_API + '/' + id, Qs.stringify(params), config)
}

export function del (id, config) {
  return Vue.axios.delete(OPERATOR_GROUP.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(OPERATOR_GROUP.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(OPERATOR_GROUP.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(OPERATOR_GROUP.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(OPERATOR_GROUP.QUERY_ALL_API, config)
}

export function groups (config) {
  return Vue.axios.get(OPERATOR_GROUP.QUERY_GROUPED_API, config)
}

export function addOperator (params, config) {
  return Vue.axios.post(OPERATOR_GROUP.ADD_OPERATORS_API, Qs.stringify(params), config)
}
