import Vue from 'vue'
import Qs from 'qs'

const AFFIX = '/images'

const IMAGE = {
  CRUD_API: AFFIX,
  QUERY_API: AFFIX + '/query',
  QUERY_PAGE_API: AFFIX + '/query/page',
  QUERY_ALL_API: AFFIX + '/all',
  FILE_UPLOAD: AFFIX + '/upload',
  CHECK_NAME_API: AFFIX + '/check/name'
}

export function create (data, config) {
  return Vue.axios.post(IMAGE.CRUD_API, data, config)
}

// 需要手动stringify 存疑
export function update (data, config) {
  return Vue.axios.post(IMAGE.CRUD_API + '/' + data.id + '/patch', Qs.stringify(data), config)
}

export function upload (data, config) {
  return Vue.axios.post(IMAGE.FILE_UPLOAD, data, config)
}

export function del (id, config) {
  return Vue.axios.delete(IMAGE.CRUD_API + '/' + id, config)
}

export function get (id, config) {
  return Vue.axios.get(IMAGE.CRUD_API + '/' + id, config)
}

export function query (params, config) {
  return Vue.axios.get(IMAGE.QUERY_API, { params: params }, config)
}

export function queryPage (params, config) {
  return Vue.axios.get(IMAGE.QUERY_PAGE_API, { params: params }, config)
}

export function queryAll (config) {
  return Vue.axios.get(IMAGE.QUERY_ALL_API, config)
}

export function checkName (params, config) {
  return Vue.axios.get(IMAGE.CHECK_NAME_API, Object.assign({ params: params }, config))
}
