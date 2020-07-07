<template>
  <a-dropdown v-if="currentUser !== undefined && currentUser.name !== undefined">
    <span class="action account">
      <a-avatar
        v-if="currentUser.avatar !== undefined"
        class="avatar"
        size="small"
        :src="currentUser.avatar"
      />
      <span
        :class="{ 'name': true, 'has-avatar': currentUser.avatar !== undefined && currentUser.avatar.trim() !== '' }"
      >{{ currentUser.name }}</span>
    </span>
    <a-menu slot="overlay" class="pro-components-global-header-menu">
      <a-menu-item key="center" v-if="centerPath !== undefined && centerPath.trim() !== ''">
        <router-link :to="{ path: centerPath }">
          <a-icon type="user" />
          <span>个人中心</span>
        </router-link>
      </a-menu-item>
      <a-menu-item key="settings" v-if="settingPath !== undefined && settingPath.trim() !== ''">
        <router-link :to="{ path: settingPath }">
          <a-icon type="setting" />
          <span>个人设置</span>
        </router-link>
      </a-menu-item>
      <a-menu-divider
        v-if="(centerPath !== undefined && centerPath.trim() !== '') || (settingPath !== undefined && settingPath.trim() !== '')"
      />
      <a-menu-item key="logout">
        <a href="javascript:;" @click="handleLogout">
          <a-icon type="logout" />
          <span>退出登录</span>
        </a>
      </a-menu-item>
    </a-menu>
  </a-dropdown>
  <span v-else class="action account loading">
    <a-spin size="small" :style="{ marginLeft: '8px', marginRight: '8px' }" />
  </span>
</template>

<script>
import VueTypes from 'vue-types'

import { Menu, Icon, Spin } from 'ant-design-vue'

export const AvatarDropdownProps = {
  currentUser: VueTypes.shape({
    avatar: VueTypes.string,
    name: VueTypes.string
  }).loose,
  settingPath: VueTypes.string,
  centerPath: VueTypes.string
}

export default {
  name: 'AvatarDropdown',
  props: {
    ...AvatarDropdownProps
  },
  components: {
    AMenu: Menu,
    AIcon: Icon,
    ASpin: Spin
  },
  methods: {
    handleLogout () {
      this.$emit('logout')
    }
  }
}
</script>
