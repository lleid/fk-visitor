import Qs from 'qs'
import Vue from 'vue'

export const SYS_MENU_API = {
    CRUD_API: '/sys/menu',
    ALL_API: '/sys/menu/all',
    CODE_API: '/sys/menu/code',
    CHECK_CODE_API: '/sys/menu/check/code',
    CHECK_ALIAS_API: 'sys/menu/check/alias'
}

export function create (menu, config) {
    return Vue.axios.post(SYS_MENU_API.CRUD_API, Qs.stringify(menu), config)
}

export function update (menu, config) {
    return Vue.axios.put(SYS_MENU_API.CRUD_API + '/' + menu.id, Qs.stringify(menu), config)
}

export function del (id, config) {
    return Vue.axios.delete(SYS_MENU_API.CRUD_API + '/' + id, config)
}

export function get (id, config) {
    return Vue.axios.get(SYS_MENU_API.CRUD_API + '/' + id, config)
}

export function all (config) {
    return Vue.axios.get(SYS_MENU_API.ALL_API, config)
}

export function checkCode (params, config) {
    return Vue.axios.get(SYS_MENU_API.CHECK_CODE_API, Object.assign({ params: params }, config))
}

export function checkAlias (params, config) {
    return Vue.axios.get(SYS_MENU_API.CHECK_ALIAS_API, Object.assign({ params: params }, config))
}
