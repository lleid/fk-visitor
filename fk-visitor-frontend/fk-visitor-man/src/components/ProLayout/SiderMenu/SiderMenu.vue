<template>
  <a-layout-sider
    v-if="siderMenuRender"
    collapsible
    :trigger="null"
    :collapsed="collapsed"
    :breakpoint="(breakpoint === undefined || breakpoint.trim() === '') ? undefined : breakpoint"
    :width="siderWidth"
    :theme="navTheme"
    :class="{
      ...siderMenuClass,
      'ant-pro-sider-menu': true,
      'ant-pro-sider-menu-sider': true,
      'fix-sider-bar': fixSiderbar,
      'light': navTheme === 'light'
    }"
    :style="{
      ...siderMenuStyle
    }"
  >
    <div v-if="menuHeaderRender" id="logo" class="ant-pro-sider-menu-logo">
      <slot name="logo">
        <logo
          :logo="logo"
          :path="path"
          :title="title"
          :show-title="!collapsed"
          @click="menuHeaderClick"
        />
      </slot>
    </div>
    <slot name="menu">
      <base-menu
        v-bind="$props"
        mode="inline"
        style="padding: 16px 0px;"
        @select="menuSelect"
        @open="menuOpen"
        @update="menuUpdate"
      />
    </slot>
    <div v-if="links !== undefined && links.length > 0" class="ant-pro-sider-menu-links">
      <a-menu
        :theme="navTheme"
        :class="{ 'ant-pro-sider-menu-link-menu': true }"
        mode="inline"
        :selected-keys="[]"
        :open-keys="[]"
      >
        <a-menu-item v-for="(link, index) in links" :key="index">
          <a
            v-if="isUrl(link.path)"
            :href="link.path"
            :target="link.target === undefined ? '_blank' : link.target"
          >
            <span>{{ link.name }}</span>
          </a>
          <router-link
            v-else
            :to="{ path: link.path }"
            :target="link.target === undefined ? '_self' : link.target"
          >
            <span>{{ link.name }}</span>
          </router-link>
        </a-menu-item>
      </a-menu>
    </div>
  </a-layout-sider>
</template>

<script>
import VueTypes from 'vue-types'

import { Layout } from 'ant-design-vue'

import { SettingProps } from '../ProLayoutSetting'
import { ProLayoutStateProps } from '../ProLayoutProps'
import Logo, { LogoProps } from '../Logo'
import BaseMenu, { BaseMenuProps } from '../BaseMenu/BaseMenu'

import { isUrl } from '../utils/utils'

export const SiderMenuProps = {
  ...SettingProps,
  ...ProLayoutStateProps,
  ...LogoProps,
  ...BaseMenuProps,
  siderWidth: VueTypes.number.def(256),
  breakpoint: VueTypes.oneOf(['xs', 'sm', 'md', 'lg', 'xl', 'xxl']),
  links: VueTypes.arrayOf(VueTypes.shape({
    name: String,
    path: String,
    target: VueTypes.oneOf(['_blank', '_self'])
  }).loose),
  siderMenuRender: VueTypes.bool.def(true),
  siderMenuClass: VueTypes.object.def({}),
  siderMenuStyle: VueTypes.object.def({}),
  menuHeaderRender: VueTypes.bool.def(true),
  menuHeaderClick: VueTypes.func,
  menuUpdate: VueTypes.func,
  menuSelect: VueTypes.func,
  menuOpen: VueTypes.func
}

export default {
  name: 'SiderMenu',
  components: {
    BaseMenu,
    Logo,
    ALayoutSider: Layout.Sider
  },
  props: {
    ...SiderMenuProps,
    menus: VueTypes.array.def([])
  },
  data () {
    return {
    }
  },
  methods: {
    isUrl
  }
}
</script>

<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

@pro-layout-sider-menu-prefix-cls: ~'@{ant-prefix}-pro-sider-menu';

@nav-header-height: @layout-header-height;

.@{pro-layout-sider-menu-prefix-cls} {
  position: relative;
  &-logo {
    position: relative;
    padding: 0 (@menu-collapsed-width - 32px) / 2;
    overflow: hidden;
    background: @layout-sider-background;
    cursor: pointer;
    transition: all 0.3s;

    > a {
      display: flex;
      align-items: center;
      height: 64px;
    }

    img {
      display: inline-block;
      height: 32px;
      vertical-align: middle;
    }
    h1 {
      display: inline-block;
      margin: 0 0 0 12px;
      color: white;
      font-weight: 600;
      font-size: 20px;
      vertical-align: middle;
      animation: fade-in;
      animation-duration: 0.3s;
    }
  }
  &-sider {
    position: relative;
    z-index: 10;
    min-height: 100%;
    box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
    &.fix-sider-bar {
      position: fixed;
      top: 0;
      left: 0;
      height: 100%;
      overflow: auto;
      box-shadow: 2px 0 8px 0 rgba(29, 35, 41, 0.05);
      > .ant-menu-root {
        :not(.@{pro-layout-sider-menu-prefix-cls}-link-menu) {
          height: ~'calc(100vh - @{nav-header-height})';
          overflow-y: auto;
        }
      }

      .ant-menu-inline {
        background-color: none;
        border-right: 0;

        .ant-menu-item,
        .ant-menu-submenu-title {
          width: 100%;
        }
      }
    }
    &.light {
      background-color: @white;
      box-shadow: 2px 0 8px 0 rgba(29, 35, 41, 0.05);
      .@{pro-layout-sider-menu-prefix-cls}-logo {
        background-color: @white;
        h1 {
          color: @primary-color;
        }
      }
      .ant-menu-light {
        border-right-color: transparent;
      }
    }
  }

  &-icon {
    height: 14px;
    width: 14px;
    vertical-align: baseline;
  }

  &-links {
    position: absolute;
    bottom: 12px;
    width: 100%;
    ul.ant-menu-root {
      height: auto;
    }
  }

  .top-nav-menu li.ant-menu-item {
    height: @nav-header-height;
    line-height: @nav-header-height;
  }
  .drawer .drawer-content {
    background: @layout-sider-background;
  }
  .ant-menu-inline-collapsed {
    & > .ant-menu-item .sider-menu-item-img + span,
    & > .ant-menu-item-group > .ant-menu-item-group-list > .ant-menu-item .sider-menu-item-img + span,
    & > .ant-menu-submenu > .ant-menu-submenu-title .sider-menu-item-img + span {
      display: inline-block;
      max-width: 0;
      opacity: 0;
    }
  }
  .ant-menu-item .sider-menu-item-img + span,
  .ant-menu-submenu-title .sider-menu-item-img + span {
    opacity: 1;
    transition: opacity 0.3s @ease-in-out, width 0.3s @ease-in-out;
  }
  .ant-drawer-body {
    padding: 0;
  }
}

@keyframes fade-in {
  0% {
    opacity: 0;
  }
  40% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
</style>
