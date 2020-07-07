import Qs from 'qs'
import Vue from 'vue'

import merge from 'lodash.merge'

export const AUTH_API = {
    LOGIN_API: '/auth/token',
    LOGOUT_API: '/auth/logout',
    REFRESH_API: '/auth/refresh'
}

export function login (loginParams, config) {
    return Vue.axios.post(AUTH_API.LOGIN_API, Qs.stringify(loginParams), config)
}

export function logout (config) {
    return Vue.axios.request(merge({
        url: AUTH_API.LOGOUT_API
    }, config))
}

export function refresh (config) {
    return Vue.axios.request(merge({
        url: AUTH_API.REFRESH_API,
        method: 'POST'
    }, config))
}
