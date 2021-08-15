<template>
  <div class="account-settings-main">
    <div class="account-settings-left">
      <a-menu :mode="isMobile ? 'horizontal' : 'inline'" :style="{ border: '0', width: isMobile ? '560px' : 'auto'}" :selectedKeys="selectedKeys">
        <a-menu-item key="/account/settings/basic">
          <router-link :to="{ name: 'account-setting-basic' }">基本信息</router-link>
        </a-menu-item>
        <a-menu-item key="/account/settings/security">
          <router-link :to="{ name: 'account-setting-security' }">账号密码</router-link>
        </a-menu-item>
      </a-menu>
    </div>
    <div class="account-settings-right">
      <div class="account-settings-title">
        <span>{{ $route.meta.title }}</span>
      </div>
      <div class="account-settings-view">
        <route-view />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import { RouteView } from '@/layouts'

export default {
  components: {
    RouteView
  },
  data () {
    return {
      selectedKeys: []
    }
  },
  computed: {
    ...mapState({
      isMobile: state => state.app.isMobile
    })
  },
  mounted () {
    this.updateMenu()
  },
  methods: {
    updateMenu () {
      this.selectedKeys = [this.$route.matched.concat().pop().path]
    }
  },
  watch: {
    '$route' (val) {
      this.updateMenu()
    }
  }
}
</script>

<style lang="less">
@import '~ant-design-vue/lib/style/themes/default';

.account-settings-main {
  display: flex;
  width: 100%;
  height: 100%;
  padding-top: 16px;
  padding-bottom: 16px;
  overflow: auto;
  background-color: #fff;

  .account-settings-left {
    width: 224px;
    border-right: 1px solid #f0f0f0;
  }

  .account-settings-right {
    flex: 1 1;
    padding: 8px 40px;
    .account-settings-title {
      margin-bottom: 12px;
      color: rgba(0, 0, 0, 0.85);
      font-weight: 500;
      font-size: 20px;
      line-height: 28px;
    }
    .account-settings-view {
      max-width: 448px;
      padding-top: 12px;
    }
  }

  @media screen and (max-width: @screen-md) {
    flex-direction: column;
    padding-top: 0px;
    .account-settings-left {
      width: 100%;
      border: none;
    }
    .account-settings-right {
      padding: 40px;
    }
  }
  .setting-submit {
    padding-top: 10px;
  }
}
</style>
