// with polyfills
import 'regenerator-runtime/runtime'

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// mock
// WARNING: `mockjs` NOT SUPPORT `IE` PLEASE DO NOT USE IN `production` ENV.
// import './mock'

import './bootstrap/bootstrap'
// 引入echarts
import echarts from 'echarts'

Vue.prototype.$echarts = echarts
Vue.config.productionTip = false

new Vue({
  router,
  store,
  // created: bootstrap,
  render: h => h(App)
}).$mount('#app')
