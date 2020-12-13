import Vue from 'vue'

import ProLayoutPlugin from './plugins/pro-layout-plugin'
import AxiosPlugin from './plugins/axios-plugin'
import AntDesignPlugin from './plugins/ant-design-plugin'
import VueStoragePlugin from './plugins/vue-storage-plugin'
import DefinedComponentsPlugin from './plugins/defined-components-plugin'

import BaseService from '@/service/BaseService'

import '@/styles/global.less'

import VueAwesomeSwiper from 'vue-awesome-swiper'
// import style (>= Swiper 6.x)
import 'swiper/swiper-bundle.css'

Vue.use(AntDesignPlugin)
Vue.use(ProLayoutPlugin)
Vue.use(AxiosPlugin, BaseService)
Vue.use(VueStoragePlugin)
Vue.use(DefinedComponentsPlugin)
Vue.use(VueAwesomeSwiper /* { default options with global component } */)
