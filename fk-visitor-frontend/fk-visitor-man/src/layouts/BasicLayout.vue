<template>
  <pro-layout
    v-bind="themeConfig"
    :menu-data="menuData"
    :loading="loadingState"
    @mobile-change="handleMobileChange"
    @size-change="handleSizeChange"
  >
    <right-content
      slot="header-right"
      :current-user="userData"
      :theme="themeConfig.navTheme"
      :layout="themeConfig.layout"
      @logout="handleLogout"
    />
    <router-view />
  </pro-layout>
</template>

<script>
import { mapState } from 'vuex'

import { OPERATOR_ACTIONS } from '@/store/modules/operator-store'
import { APP_MUTATIONS } from '@/store/modules/app-store'

import ROUTE_PATH from '@/router/route-paths'

export default {
  name: 'BasicLayout',
  computed: {
    ...mapState({
      loadingState: state => state.app.loading,
      operatorData: state => state.operator.operator,
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config
    }),
    userData: function () {
      if (this.operatorData !== undefined) {
        return {
          name: this.operatorData.username
        }
      }
      return {}
    }
  },
  methods: {
    handleMobileChange (val) {
      this.$store.commit(APP_MUTATIONS.UPDATE_MOBILE_STATE, val)
    },
    handleSizeChange (val) {
      this.$store.commit(APP_MUTATIONS.UPDATE_MEDIA_SIZE, val)
    },
    handleLogout () {
      this.$store.dispatch(OPERATOR_ACTIONS.LOGOUT).then(() => {
        this.$router.push({ path: ROUTE_PATH.LOGIN_PATH })
      })
    }
  }
}
</script>

<style lang="less">
</style>
