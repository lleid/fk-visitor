<template>
  <div :class="{ 'ant-pro-top-nav-header': true, 'light': navTheme === 'light' }">
    <div :class="{ 'ant-pro-top-nav-header-main': true, ' wide' : contentWidth === 'Fixed' }">
      <div v-if="menuHeaderRender" class="ant-pro-top-nav-header-left">
        <div id="logo" class="ant-pro-top-nav-header-logo" key="logo">
          <slot name="logo">
            <logo
              :logo="logo"
              :path="path"
              :title="title"
              :show-title="true"
              @click="menuHeaderClick"
            />
          </slot>
        </div>
      </div>
      <div :style="{ flex: 1 }" class="ant-pro-top-nav-header-menu">
        <slot name="menu">
          <base-menu
            v-bind="$props"
            mode="horizontal"
            @select="menuSelect"
            @open="menuOpen"
            @update="menuUpdate"
          />
        </slot>
      </div>
      <slot name="right" />
    </div>
  </div>
</template>

<script>
import VueTypes from 'vue-types'

import { SettingProps } from '../ProLayoutSetting'
import { ProLayoutStateProps } from '../ProLayoutProps'
import Logo, { LogoProps } from '../Logo'
import BaseMenu, { BaseMenuProps } from '../BaseMenu/BaseMenu'

export const TopNavHeaderProps = {
  ...SettingProps,
  ...ProLayoutStateProps,
  ...LogoProps,
  ...BaseMenuProps,
  menuHeaderRender: VueTypes.bool.def(true),
  menuHeaderClick: VueTypes.func,
  menuUpdate: VueTypes.func,
  menuSelect: VueTypes.func,
  menuOpen: VueTypes.func
}

export default {
  name: 'TopNavHeader',
  components: {
    Logo,
    BaseMenu
  },
  props: {
    ...TopNavHeaderProps
  },
  data () {
    return {
    }
  },
  mounted () {
  },
  methods: {
  }
}
</script>

<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

@top-nav-header-prefix-cls: ~'@{ant-prefix}-pro-top-nav-header';

.@{top-nav-header-prefix-cls} {
  position: relative;
  width: 100%;
  height: @layout-header-height;
  box-shadow: @box-shadow-base;
  transition: background 0.3s, width 0.2s;

  .ant-menu-submenu.ant-menu-submenu-horizontal {
    height: 100%;
    line-height: @layout-header-height;
    top: 0px;
    .ant-menu-submenu-title {
      height: 100%;
    }
  }

  &.light {
    background-color: @component-background;
    h1 {
      color: #002140;
    }
  }

  &-main {
    display: flex;
    height: @layout-header-height;
    padding-left: 24px;
    &.wide {
      max-width: 1200px;
      margin: auto;
      padding-left: 0;
    }
  }

  &-logo {
    position: relative;
    height: @layout-header-height;
    overflow: hidden;
    line-height: @layout-header-height;
    transition: all 0.3s;
    img {
      display: inline-block;
      height: 32px;
      vertical-align: middle;
      margin: 0 12px 0 0;
    }
    h1 {
      display: inline-block;
      color: @btn-primary-color;
      margin: 0 12px 0 0;
      font-weight: 400;
      font-size: 16px;
      vertical-align: top;
    }
  }
  &-menu {
    min-width: 0;
    .ant-menu.ant-menu-horizontal {
      height: @layout-header-height;
      line-height: @layout-header-height;
      border: none;
    }
  }
}
</style>
