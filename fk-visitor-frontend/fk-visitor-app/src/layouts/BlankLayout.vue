<template>
  <div class="layout-wrapper">
    <div class="layout-header">
      <img class="logo" src="~@/assets/logo.png" />
      <div class="welcome" v-if="language === 'CN'">欢迎光临</div>
      <div class="welcome" v-else>WELCOME</div>
      <div class="operate">
        <span v-if="language === 'CN'" @click="handleLanguage" class="language">English</span>
        <span v-else @click="handleLanguage" class="language">中</span>
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
  background-color: rgba(255, 255, 255, 0.2);
  padding-top: 180px;
  padding-bottom: 35px;
}

.layout-header {
  position: absolute;
  top: 0px;
  height: 180px;
  left: 180px;
  right: 180px;

  .logo {
    position: absolute;
    left: 0;
    top: 64px;
    width: 300px;
  }

  .welcome {
    font-weight: bold;
    letter-spacing: 16px;
    color: #013b84;
    text-align: center;
    font-size: 48px;
    position: absolute;
    right: 0;
    left: 0;
    top: 54px;
    text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
  }

  .operate {
    position: absolute;
    right: 0;
    bottom: 10px;
    color: #013b84;
    text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);

    .language {
      font-size: 20px;
      font-weight: bold;
      width: 80px;
      display: inline-block;
      text-align: right;
      height: 40px;
      line-height: 40px;
    }

    i {
      font-size: 32px;
      margin-left: 24px;
    }
  }
}

.layout-content {
  height: 100%;
  position: relative;
  padding-left: 180px;
  padding-right: 180px;
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
