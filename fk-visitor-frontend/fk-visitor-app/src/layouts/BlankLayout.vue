<template>
  <div class="layout-wrapper">
    <div class="layout-header">
      <div class="header-container">
        <img class="logo" src="~@/assets/logo.png" />
      </div>
      <div class="operate">
        <span @click="handleLanguage">
          <c-icon type="fv-zhongwenyuyan" v-if="languare==='CN'"></c-icon>
          <c-icon type="fv-yingwenyuyan" v-else></c-icon>
        </span>
        <span @click="handleHome">
          <c-icon type="fv-shouye "></c-icon>
        </span>
      </div>
      <div class="welcome" v-if="languare==='CN'">欢迎光临</div>
      <div class="welcome" v-else>WELCOME</div>
    </div>
    <div class="layout-content">
      <router-view />
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
  data () {
    return {
      languare: 'CN'
    }
  },
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
    },
    handleLanguage () {
      if (this.languare === 'CN') {
        this.languare = 'EN'
        this.$router.push({ path: ROUTE_PATH.HOME_PATH })
      } else {
        this.languare = 'CN'
        this.$router.push({ path: ROUTE_PATH.HOME_PATH })
      }
    }
  }
}
</script>

<style scoped lang="less">
.layout-wrapper {
  height: 100%;
  background: url('http://localhost:89/019.jpg');
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
  padding-top: 160px;
  padding-bottom: 80px;
}

.layout-header {
  position: absolute;
  top: 0px;
  height: 160px;
  left: 80px;
  right: 80px;

  .welcome {
    font-weight: bold;
    color: #274075;
    text-align: center;
    font-size: 28px;
    margin-top: -10px;
  }

  .operate {
    position: absolute;
    right: 0;
    top: 60px;
    color: #274075;

    i {
      font-size: 50px;
      margin-right: 24px;
    }
  }

  .header-container {
    width: 100%;
    position: relative;
    margin: 50px 0;
    margin-bottom: 10px;

    .logo {
      width: 250px;
    }
  }
}

.layout-content {
  height: 100%;
  position: relative;
  padding-left: 80px;
  padding-right: 80px;
}

.layout-footer {
  line-height: 50px;
  position: absolute;
  bottom: 0;
  height: 50px;
  text-align: center;
  left: 0;
  right: 0;
  color: #fff;
}
</style>
