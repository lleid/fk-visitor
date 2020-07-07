<template>
  <div
    :class="{ 'pro-components-global-header-right': true, 'dark' : theme === 'dark' && layout === 'topmenu' }"
  >
    <span v-if="envColor !== undefined">
      <a-tag :color="envColor">{{ env }}</a-tag>
    </span>
    <avatar-dropdown :current-user="currentUser" @logout="handleLogout" setting-path="/account/settings" />
  </div>
</template>

<script>
import VueTypes from 'vue-types'
import { Tag } from 'ant-design-vue'

import AvatarDropdown, { AvatarDropdownProps } from './AvatarDropdown'

const ENVTagColor = {
  development: '#f5222d',
  staging: '#faad14'
}

export const RightContentProps = {
  theme: VueTypes.oneOf(['light', 'dark']),
  layout: VueTypes.oneOf(['sidemenu', 'topmenu']),
  ...AvatarDropdownProps
}

const RightContent = {
  name: 'RightContent',
  props: {
    ...RightContentProps
  },
  components: {
    ATag: Tag,
    AvatarDropdown
  },
  data () {
    return {
      env: process.env.NODE_ENV,
      envColor: ENVTagColor[process.env.NODE_ENV]
    }
  },
  methods: {
    handleLogout () {
      this.$emit('logout')
    }
  }
}

RightContent.install = function (Vue) {
  Vue.component(RightContent.name, RightContent)
}

export default RightContent
</script>

<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

@pro-header-hover-bg: rgba(0, 0, 0, 0.025);

.pro-components-global-header-menu {
  .anticon {
    margin-right: 8px;
  }
  .ant-dropdown-menu-item {
    min-width: 160px;
  }
}

.pro-components-global-header-right {
  display: flex;
  float: right;
  height: @layout-header-height;
  margin-left: auto;
  margin-right: 8px;
  overflow: hidden;
  .action {
    display: flex;
    align-items: center;
    height: 100%;
    padding: 0 12px;
    cursor: pointer;
    transition: all 0.3s;
    > span {
      color: @text-color;
      vertical-align: middle;
    }
    &:hover {
      background: @pro-header-hover-bg;
    }
    &.opened {
      background: @pro-header-hover-bg;
    }
    &.loading {
      &:hover {
        background: transparent;
      }
    }
  }
  .account {
    .avatar {
      margin: ~'calc((@{layout-header-height} - 24px) / 2)' 0;
      margin-right: 8px;
      color: @primary-color;
      vertical-align: top;
      background: rgba(255, 255, 255, 0.85);
    }
    .name {
      padding: 0 8px;
    }
    .ant-spin {
      margin: 0 8px;
    }
  }
  &.dark {
    .action {
      color: rgba(255, 255, 255, 0.85);
      > span {
        color: rgba(255, 255, 255, 0.85);
      }
      &:hover,
      &.opened {
        background: @primary-color;
      }
      &.loading {
        &:hover {
          background: transparent;
        }
      }
    }
  }
}

.ant-pro-global-header {
  .pro-components-global-header-right {
    &.dark {
      .action {
        color: @text-color;
        > span {
          color: @text-color;
        }
        &:hover {
          color: rgba(255, 255, 255, 0.85);
          > span {
            color: rgba(255, 255, 255, 0.85);
          }
        }
      }
    }
  }
}

@media only screen and (max-width: @screen-md) {
  .pro-components-global-header-right {
    position: absolute;
    top: 0;
    right: 12px;
    .account {
      .avatar {
        margin-right: 0;
      }
    }
    .account {
      .name {
        &.has-avatar {
          display: none;
        }
      }
    }
  }
}
</style>
