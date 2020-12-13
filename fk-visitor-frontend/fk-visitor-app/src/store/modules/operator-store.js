import * as AuthService from '@/service/AuthService'
import * as PersonalService from '@/service/system/PersonalService'

import { TOKEN_MUTATIONS } from './token-store'

const getUrls = (menus) => {
    let _urls = []
    let queue = Array.isArray(menus) ? menus : [menus]
    while (queue.length) {
        const queueSize = queue.length
        for (let i = 0; i < queueSize; i++) {
            const menu = queue.shift()
            if (menu.url !== undefined) {
                _urls.push(menu.url)
            }
            if (menu.relatedUrls !== undefined && Array.isArray(menu.relatedUrls) && menu.relatedUrls.length > 0) {
                _urls = _urls.concat(menu.relatedUrls)
            }
            if (menu.subMenus === undefined || !Array.isArray(menu.subMenus) || menu.subMenus.length === 0) continue
            queue = queue.concat(menu.subMenus)
        }
    }
    return _urls
}

const genMenuData = (menus) => {
    return menus.map(item => {
        const menuData = {
            icon: item.icon,
            name: item.name,
            key: item.alias,
            path: item.url,
            relatedPath: item.relatedUrls
        }
        if (item.subMenus && item.subMenus.length > 0) {
            menuData['children'] = genMenuData(item.subMenus)
        }
        return menuData
    })
}

export const OPERATOR_MUTATIONS = {
    SET_OPERATOR: 'SET_OPERATOR',
    SET_MENUS: 'SET_MENUS',
    INVALIDATE_OPERATOR: 'INVALIDATE_OPERATOR'
}

export const OPERATOR_ACTIONS = {
    RELOAD: 'reloadOperator',
    INIT: 'initOperator',
    LOGIN: 'login',
    LOGOUT: 'logout'
}

const operator = {
    state: {
        operator: undefined,
        menus: undefined,
        urls: undefined
    },
    mutations: {
        SET_OPERATOR: (state, operator) => {
            state.operator = operator
        },
        SET_MENUS: (state, menus) => {
            state.menus = genMenuData(JSON.parse(JSON.stringify(menus)))
            state.urls = getUrls(menus)
        },
        INVALIDATE_OPERATOR (state) {
            state.operator = undefined
            state.menus = undefined
            state.urls = undefined
        }
    },
    actions: {
        async reloadOperator ({ commit }, showLoading = false) {
            try {
                commit(OPERATOR_MUTATIONS.SET_OPERATOR, await PersonalService.queryMyInfo({ showLoading: showLoading }))
            } catch (e) {
            }
        },
        async initOperator ({ getters, commit }, showLoading = true) {
            if (!getters.isOperatorLoaded) {
                try {
                    commit(OPERATOR_MUTATIONS.SET_OPERATOR, await PersonalService.queryMyInfo({ showLoading: showLoading }))
                    commit(OPERATOR_MUTATIONS.SET_MENUS, await PersonalService.queryMyMenus({ showLoading: showLoading }))
                } catch (e) {
                }
            }
        },
        async login ({ dispatch, commit }, loginParams) {
            try {
                const tokenData = await AuthService.login(loginParams)
                if (tokenData !== undefined) {
                    await commit(TOKEN_MUTATIONS.SAVE_TOKEN, tokenData)
                    await dispatch(OPERATOR_ACTIONS.INIT)
                }
            } catch (e) {
            }
        },
        async logout ({ commit, rootGetters }) {
            if (rootGetters.tokenAccessable()) {
                try {
                    await AuthService.logout({ showSuccess: false, showLoading: false })
                } catch (_) {
                }
            }
            commit(TOKEN_MUTATIONS.INVALIDATE_TOKEN)
            commit(OPERATOR_MUTATIONS.INVALIDATE_OPERATOR)
        }
    },
    getters: {
        isOperatorLoaded: state => {
            return state.operator !== undefined
        }
    }
}

export default operator
