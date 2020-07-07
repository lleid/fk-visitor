import * as AuthService from '@/service/AuthService'

export const TOKEN_MUTATIONS = {
    SAVE_TOKEN: 'SAVE_TOKEN',
    INVALIDATE_TOKEN: 'INVALIDATE_TOKEN'
}

export const TOKEN_ACTIONS = {
    REFRESH_TOKEN: 'refreshToken'
}

let isRefreshing = false
const waitRefresh = async (wait) => {
    return new Promise(resolve => setTimeout(resolve, wait))
}

const token = {
    state: {
        clientId: undefined,
        accessToken: undefined,
        accessExpires: undefined,
        refreshToken: undefined,
        refreshExpires: undefined
    },
    mutations: {
        SAVE_TOKEN (state, tokenResponse) {
            if (tokenResponse) {
                const _now = new Date().getTime()
                state.clientId = tokenResponse.clientId
                if (tokenResponse.accessToken !== undefined) {
                    state.accessToken = tokenResponse.accessToken.token
                    state.accessExpires = _now + parseInt(tokenResponse.accessToken.expires * 0.9 * 1000)
                }
                if (tokenResponse.refreshToken !== undefined) {
                    state.refreshToken = tokenResponse.refreshToken.token
                    state.refreshExpires = _now + parseInt(tokenResponse.refreshToken.expires * 0.95 * 1000)
                }
            }
        },
        INVALIDATE_TOKEN (state) {
            state.clientId = undefined
            state.accessToken = undefined
            state.accessExpires = undefined
            state.refreshToken = undefined
            state.refreshExpires = undefined
        }
    },
    actions: {
        async refreshToken ({ commit }) {
            if (isRefreshing) {
                // eslint-disable-next-line no-unmodified-loop-condition
                while (isRefreshing) {
                    await waitRefresh(300)
                }
            } else {
                isRefreshing = true
                const result = await AuthService.refresh({ showLoading: false, showError: false })
                commit(TOKEN_MUTATIONS.SAVE_TOKEN, result)
                isRefreshing = false
            }
            return Promise.resolve()
        }
    },
    getters: {
        tokenAccessable: (state) => () => {
            if (state.accessToken !== undefined && state.accessExpires !== undefined) {
                return new Date().getTime() <= state.accessExpires
            }
            return false
        },
        tokenRefreshable: (state) => () => {
            if (state.refreshToken !== undefined && state.refreshExpires !== undefined) {
                return new Date().getTime() <= state.refreshExpires
            }
            return false
        }
    }
}

export default token
