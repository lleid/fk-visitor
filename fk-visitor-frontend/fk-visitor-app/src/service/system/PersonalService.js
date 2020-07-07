import qs from 'qs'
import Vue from 'vue'

import merge from 'lodash.merge'

export const PERSONAL_API = {
    INFO_API: '/personal/info',
    MENUS_API: '/personal/menus',
    UPDATE_API: '/personal/update/info',
    UPDATE_PASSWORD_API: '/personal/update/password',
    CHECK_MOBILE_API: '/personal/check/mobile',
    CHECK_EMAIL_API: '/personal/check/email'
}

export function queryMyInfo (config) {
    return Vue.axios.request(merge({
        url: PERSONAL_API.INFO_API
    }, config))
}

export function queryMyMenus (config) {
    return Vue.axios.request(merge({
        url: PERSONAL_API.MENUS_API
    }, config))
}

export function updateMyInfo (params, config) {
    return Vue.axios.post(PERSONAL_API.UPDATE_API, qs.stringify(params, { allowDots: true }), config)
}

export function updateMyPassword (params, config) {
    return Vue.axios.post(PERSONAL_API.UPDATE_PASSWORD_API, qs.stringify(params), config)
}

export function checkMyMobile (mobile, config) {
    return Vue.axios.post(PERSONAL_API.CHECK_MOBILE_API, qs.stringify({ mobile: mobile }), config)
}

export function checkMyEmail (email, config) {
    return Vue.axios.post(PERSONAL_API.CHECK_EMAIL_API, qs.stringify({ email: email }), config)
}
