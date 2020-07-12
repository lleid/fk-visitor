<template>
  <div class="layout-wrapper">
    <div class="layout-header">
      <div class="header-container">
        <img class="logo" src="~@/assets/logo.png" />
        <span class="language">中</span>
        <a-icon type="home" class="logout" @click="handleHome" />
      </div>
    </div>
    <div class="layout-content">
      <div class="content-container">
        <router-view />
      </div>
    </div>
    <div class="layout-footer">Copyright &copy; 2020 复星凯特生物科技有限公司</div>
  </div>
</template>

<script>

import { mapState } from 'vuex'

import { APP_MUTATIONS } from '@/store/modules/app-store'

import ROUTE_PATH from '@/router/route-paths'

export default {
  name: 'BlankLayout',
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
    handleHome () {
      this.$router.push({ path: ROUTE_PATH.HOME_PATH })
    }
  }
}
</script>

<style scoped lang="less">
.layout-wrapper {
  height: 100%;
  /* background: url('~@/assets/bg.jpg'); */
  background: #013b7a;
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
}
.layout-header {
  position: absolute;
  top: 0px;
  height: 120px;
  left: 50px;
  right: 50px;

  .header-container {
    width: 100%;
    position: relative;
    margin: 35px 0;

    .logo {
      width: 230px;
    }

    .logout {
      position: absolute;
      right: 0;
      height: 50px;
      top: 0;
      line-height: 50px;
      width: 50px;
      font-size: 24px;
      line-height: 50px;
      color: #ffffff !important;
    }
    .language {
      position: absolute;
      right: 80px;
      top: 10px;
      height: 30px;
      width: 30px;
      font-size: 16px;
      line-height: 30px;
      color: #013b7a;
      text-align: center;
      font-weight: bold;
      background: #fff;
    }
  }
}
.layout-content {
  position: fixed;
  top: 120px;
  bottom: 100px;
  left: 50px;
  right: 50px;

  .content-container {
    height: 100%;
    width: 100%;
    position: relative;
  }
}

.layout-footer {
  line-height: 50px;
  position: absolute;
  bottom: 0;
  height: 100px;
  text-align: center;
  left: 0;
  right: 0;
  color: #fff;
  padding-top: 50px;
}
</style>
