import Vue from 'vue'

const SYSAPI = {
    ALL_API: '/sys/api/all',
    QUERY_API: '/sys/api/query'
}

export function all (config) {
    return Vue.axios.get(SYSAPI.ALL_API, config)
}

export function query (keyword, config) {
    return Vue.axios.get(SYSAPI.QUERY_API, Object.assign({ keyword: keyword }), config)
}
