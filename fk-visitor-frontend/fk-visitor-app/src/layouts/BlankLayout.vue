<template>
  <div class="layout-wrapper">
    <div class="layout-header">
      <div class="header-container">
        <img class="logo" src="~@/assets/logo.png" />
      </div>
      <div class="welcome" v-if="language === 'CN'">欢迎光临</div>
      <div class="welcome" v-else>WELCOME</div>
      <div class="operate">
        <span @click="handleLanguage">
          <c-icon type="fv-zhongwenyuyan" v-if="language === 'CN'"></c-icon>
          <c-icon type="fv-yingwenyuyan" v-else></c-icon>
        </span>
        <span @click="handleHome">
          <c-icon type="fv-shouye "></c-icon>
        </span>
      </div>
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
    }
  },
  computed: {
    ...mapState({
      loadingState: state => state.app.loading,
      operatorData: state => state.operator.operator,
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config,
      language: state => state.app.language
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
    async handleLanguage () {
      if (this.language === 'CN') {
        this.$store.commit(APP_MUTATIONS.UPDATE_LANGUAGE, 'EN')
      } else {
        this.$store.commit(APP_MUTATIONS.UPDATE_LANGUAGE, 'CN')
      }
    }
  }
}
</script>

<style scoped lang="less">
.layout-wrapper {
  height: 100%;
  background: url('../assets/bg3.jpg');
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
  padding-top: 120px;
  padding-bottom: 35px;
}

.layout-header {
  position: absolute;
  top: 0px;
  height: 120px;
  left: 120px;
  right: 120px;

  .welcome {
    font-weight: bold;
    color: #013b84;
    text-align: center;
    font-size: 32px;
    position: absolute;
    right: 0;
    left: 0;
    top: 36px;
  }

  .operate {
    position: absolute;
    right: 0;
    top: 42px;
    color: #013b84;

    i {
      font-size: 36px;
      margin-left: 24px;
    }
  }

  .header-container {
    width: 100%;
    position: relative;
    margin: 44px 0;

    .logo {
      width: 160px;
    }
  }
}

.layout-content {
  height: 100%;
  position: relative;
  padding-left: 120px;
  padding-right: 120px;
}

.layout-footer {
  line-height: 35px;
  position: absolute;
  bottom: 0;
  height: 35px;
  text-align: center;
  left: 0;
  right: 0;
  color: #ffffff;
}
</style>
