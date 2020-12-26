import Qs from 'qs'
import Vue from 'vue'

const ORDER = {
  CRUD_API: '/order',
  QUERY_API: '/order/query',
  SIGN_OUT_API: '/order/signout',
  QUERY_PAGE_API: '/order/query/page',
  QUERY_ALL_API: '/order/all',
  EXPORT_API: '/order/export',
  GROUP_MONTH_API: '/order/group/month',
  GROUP1_MONTH_API: '/order/group/month1',
  GROUP2_MONTH_API: '/order/group/month2',
  GROUP3_MONTH_API: '/order/group/month3',
  GROUP_QUARTER_API: '/order/group/quarter',
  GROUP_YEAR_API: '/order/group/year',
  GROUP_DEPARTMENT_API: '/order/group/department'
}

export function create (params, config) {
  return Vue.axios.post(ORDER.CRUD_API, Qs.stringify(params, { allowDots: true }), config)
}

export function update (id, params, config) {
  return Vue.axios.put(ORDER.CRUD_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function singOut (id, params, config) {
  return Vue.axios.post(ORDER.SIGN_OUT_API + '/' + id, Qs.stringify(params, { allowDots: true }), config)
}

export function del (id, config) {
  return Vue.axios.deleted(ORDER.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(ORDER.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(ORDER.QUERY_API, Object.assign({ params: params }, config))
}

export function queryPage (params, config) {
  return Vue.axios.get(ORDER.QUERY_PAGE_API, Object.assign({ params: params }, config))
}

export function queryAll (config) {
  return Vue.axios.get(ORDER.QUERY_ALL_API, config)
}

export function exportOrder (params, config) {
  return Vue.axios.post(ORDER.EXPORT_API, Qs.stringify(params, { allowDots: true }), config)
}

export function groupMonth (config) {
  return Vue.axios.get(ORDER.GROUP_MONTH_API, config)
}

export function groupMonth1 (config) {
  return Vue.axios.get(ORDER.GROUP1_MONTH_API, config)
}

export function groupMonth2 (config) {
  return Vue.axios.get(ORDER.GROUP2_MONTH_API, config)
}

export function groupMonth3 (config) {
  return Vue.axios.get(ORDER.GROUP3_MONTH_API, config)
}

export function groupQuarter (config) {
  return Vue.axios.get(ORDER.GROUP_QUARTER_API, config)
}

export function groupYear (config) {
  return Vue.axios.get(ORDER.GROUP_YEAR_API, config)
}

export function groupDepartment (config) {
  return Vue.axios.get(ORDER.GROUP_DEPARTMENT_API, config)
}
