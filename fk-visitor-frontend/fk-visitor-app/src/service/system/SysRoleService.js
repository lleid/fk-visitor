import Qs from 'qs'
import Vue from 'vue'

const SYSROLE = {
    CRUD_API: '/sys/role',
    ALL_API: '/sys/role/all'
  }

export function create (params, config) {
    return Vue.axios.post(SYSROLE.CRUD_API, Qs.stringify(params), config)
  }

export function update (id, params, config) {
    return Vue.axios.put(SYSROLE.CRUD_API + '/' + id, Qs.stringify(params), config)
}

export function del (id, config) {
    return Vue.axios.delete(SYSROLE.CRUD_API + '/' + id, config)
}

export function get (id, config) {
    return Vue.axios.get(SYSROLE.CRUD_API + '/' + id, config)
}

export function all (config) {
    return Vue.axios.get(SYSROLE.ALL_API, config)
}
