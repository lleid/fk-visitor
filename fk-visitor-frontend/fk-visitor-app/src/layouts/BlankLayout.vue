<template>
  <div class="layout-wrapper">
    <img class="logo" src="~@/assets/logo.png" />

    <div class="layout-header">
      <template v-if="isHome">
        <div class="welcome cn" v-if="language === 'CN' ">欢迎光临</div>
        <div class="welcome" v-else>Welcome</div>
      </template>
      <div v-if="language === 'CN' " @click="handleLanguage" class="language">English</div>
      <div v-else @click="handleLanguage" class="language">中文</div>
      <div @click="handleHome" class="home">
        <c-icon type="fv-shouye "></c-icon>
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
      language: state => state.app.language,
      isHome: state => state.app.isHome
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
      this.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, true)
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
  background-color: rgba(255, 255, 255, 0.2);
  padding-top: 180px;
  padding-bottom: 55px;
}

.logo {
  position: absolute;
  left: 48px;
  top: 48px;
  width: 280px;
}

.layout-header {
  position: absolute;
  top: 0px;
  height: 180px;
  left: 120px;
  right: 120px;

  .welcome {
    font-weight: bold;
    color: #3263ad;
    text-align: center;
    font-size: 56px;
    position: absolute;
    right: 0;
    left: 0;
    top: 58px;
  }

  .cn {
    letter-spacing: 16px;
  }

  .language {
    position: absolute;
    right: 60px;
    bottom: 12px;
    color: #3263ad;
    font-size: 24px;
    font-weight: bold;
    width: 80px;
    display: inline-block;
    text-align: right;
    height: 40px;
    line-height: 40px;
  }

  .home {
    position: absolute;
    right: 5px;
    bottom: 12px;
    color: #3263ad;
    text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
    width: 50px;

    i {
      font-size: 38px;
      margin-left: 24px;
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
