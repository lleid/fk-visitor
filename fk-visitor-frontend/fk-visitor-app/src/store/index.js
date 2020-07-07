import Vue from 'vue'
import Vuex from 'vuex'

import createPersistedState from 'vuex-persistedstate'

// import getters from './getters'

import app from './modules/app-store'
import theme from './modules/theme-store'
import token from './modules/token-store'
import operator from './modules/operator-store'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    theme,
    token,
    operator
  },
  state: {

  },
  mutations: {

  },
  actions: {

  },
  // getters,
  plugins: [
    createPersistedState({
      key: (process.env.VUE_APP_PROJECT_CODE === undefined ? 'VUEX' : process.env.VUE_APP_PROJECT_CODE).toUpperCase() + '_' + process.env.NODE_ENV.toUpperCase(),
      paths: ['token']
    })
  ]
})
