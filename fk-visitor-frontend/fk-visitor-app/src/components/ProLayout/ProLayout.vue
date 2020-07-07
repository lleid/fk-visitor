<template>
  <div
    :class="['ant-design-pro','ant-pro-basicLayout',{
      [`screen-${colSize}`]: colSize,
      'ant-pro-basicLayout-topmenu': layout === 'topmenu',
      'ant-pro-basicLayout-fix-siderbar': fixSiderbar,
      'ant-pro-basicLayout-mobile': isMobile,
      'colorWeak': colorWeak
    }]"
  >
    <sider-menu-wrapper
      v-if="isMobile"
      v-bind="$props"
      :collapsed="collapsedData"
      :selected-keys="cachedSelectedKeysData"
      :open-keys="cachedOpenKeysData"
      :links="siderMenuLinks"
      :menu-header-click="onLogoClick"
      :menu-update="onMenuUpdate"
      :menu-select="onMenuSelect"
      :menu-open="onMenuOpen"
      @close="onSiderMenuWrapperClose"
    >
      <slot name="logo" slot="logo" />
      <slot name="menu" slot="menu" />
    </sider-menu-wrapper>
    <a-layout :style="{ minHeight: '100%', ...layoutStyle }" :has-sider="true">
      <sider-menu
        v-if="useSiderMenu && !isMobile"
        v-bind="$props"
        :collapsed="collapsedData"
        :selected-keys="cachedSelectedKeysData"
        :open-keys="cachedOpenKeysData"
        :links="siderMenuLinks"
        :menu-header-click="onLogoClick"
        :menu-update="onMenuUpdate"
        :menu-select="onMenuSelect"
        :menu-open="onMenuOpen"
      >
        <slot name="logo" slot="logo" />
        <slot name="menu" slot="menu" />
      </sider-menu>
      <a-layout
        :style="{ 'padding-left': (calcLeftPadding() !== undefined) ? calcLeftPadding() + 'px' : undefined, position: 'relative' }"
      >
        <header-view
          v-if="headerRender && !pure"
          v-bind="$props"
          :collapsed="collapsedData"
          :is-mobile="isMobile"
          @collapse="onCollapse"
          :menu-header-click="onLogoClick"
          :menu-update="onMenuUpdate"
          :menu-select="onMenuSelect"
          :menu-open="onMenuOpen"
        >
          <slot name="logo" slot="logo" />
          <slot name="header-right" slot="right" />
          <slot name="menu" slot="menu" />
        </header-view>
        <wrap-content
          :style="contentStyle"
          :class="{
            'ant-pro-basicLayout-content': true,
            'ant-pro-basicLayout-has-header': headerRender && !pure,
            'ant-pro-basicLayout-content-disable-margin': disableContentMargin,
          }"
        >
          <page-loading v-if="loading" />
          <slot v-else />
        </wrap-content>
        <footer-view v-if="footerRender && !pure" :links="footerLinks">
          <slot name="footer" />
        </footer-view>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
import omit from 'lodash.omit'
import VueTypes from 'vue-types'

import { regMediaQuery, unregMediaQuery, MediaQueryKey } from './utils/useMediaQuery'
import updateThemeColor from './utils/updateThemeColor'

import { SettingProps } from './ProLayoutSetting'
import HeaderView, { HeaderViewProps } from './Header'
import FooterView, { FooterViewProps } from './Footer'
import SiderMenu from './SiderMenu/SiderMenu'
import SiderMenuWrapper, { SiderMenuWrapperProps } from './SiderMenu/SiderMenuWrapper'
import WrapContent from './WrapContent'
import PageLoading from './PageLoading/PageLoading'

VueTypes.sensibleDefaults = false

export const ProLayoutProps = omit({
  ...SettingProps,
  ...HeaderViewProps,
  ...omit(FooterViewProps, ['links']),
  footerLinks: VueTypes.arrayOf(VueTypes.shape({
    key: String,
    title: String,
    href: String,
    blankTarget: VueTypes.bool.def(true)
  })),
  ...omit(SiderMenuWrapperProps, ['links']),
  siderMenuLinks: VueTypes.arrayOf(VueTypes.shape({
    name: String,
    path: String,
    target: VueTypes.oneOf(['_blank', '_self'])
  }).loose),
  pure: VueTypes.bool.def(false),
  loading: VueTypes.bool.def(true),
  locale: VueTypes.string.def(navigator.language),
  disableMobile: VueTypes.bool.def(false),
  layoutStyle: VueTypes.object,
  contentStyle: VueTypes.object,
  disableContentMargin: VueTypes.bool.def(false)
}, ['isMobile'])

export default {
  name: 'ProLayout',
  components: {
    HeaderView,
    FooterView,
    SiderMenu,
    SiderMenuWrapper,
    WrapContent,
    PageLoading
  },
  props: {
    ...ProLayoutProps
  },
  data () {
    return {
      colSize: '',
      collapsedData: this.collapsed,
      cachedCollapsedData: this.collapsed,
      cachedSelectedKeysData: [],
      cachedOpenKeysData: []
    }
  },
  watch: {
    colSize (newValue) {
      if (this.colSize === MediaQueryKey.MD || this.colSize === MediaQueryKey.SM || this.colSize === MediaQueryKey.XS) {
        if (this.cachedCollapsedData === undefined) {
          this.cachedCollapsedData = this.collapsedData
        }
        this.collapsedData = true
      } else {
        if (this.cachedCollapsedData !== undefined) {
          this.collapsedData = this.cachedCollapsedData
          this.cachedCollapsedData = undefined
        }
      }
      this.$emit('size-change', newValue)
    },
    isMobile (newValue) {
      this.$emit('mobile-change', newValue)
    },
    primaryColor (newValue) {
      updateThemeColor(newValue)
    }
  },
  computed: {
    isMobile: function () {
      return (this.colSize === MediaQueryKey.SM || this.colSize === MediaQueryKey.XS) && !this.disableMobile
    },
    hasLeftPadding: function () {
      return this.fixSiderbar && this.layout !== 'topmenu' && !this.isMobile
    },
    useSiderMenu () {
      return this.layout === 'sidemenu' && this.siderMenuRender && !this.pure
    }
  },
  mounted () {
    updateThemeColor(this.primaryColor)
    regMediaQuery(this.updateColSize)
  },
  destroyed () {
    unregMediaQuery()
  },
  methods: {
    updateColSize: function (colSize) {
      this.colSize = colSize
    },
    calcLeftPadding: function () {
      if (!this.siderMenuRender || this.pure) {
        return undefined
      } else {
        if (this.hasLeftPadding) {
          return this.collapsedData ? 80 : this.siderWidth
        } else {
          return undefined
        }
      }
    },
    onCollapse: function (collapsed) {
      this.collapsedData = collapsed
      this.$emit('collapse', this.collapsedData)
    },
    onSiderMenuWrapperClose: function () {
      this.onCollapse(true)
    },
    onMenuSelect: function (e) {
      this.cachedSelectedKeysData = e.selectedKeys
    },
    onMenuOpen: function (openKeysData) {
      this.cachedOpenKeysData = openKeysData
    },
    onMenuUpdate: function (e) {
      if (e.openKeys !== undefined) {
        this.cachedOpenKeysData = e.openKeys
      }
      if (e.selectedKeys !== undefined) {
        this.cachedSelectedKeysData = e.selectedKeys
      }
    },
    onMenuHeaderClick: function (e) {
      this.$emit('header-click', e)
    },
    onLogoClick: function (e) {
      this.$emit('logo-click', e)
    }
  }
}
</script>

<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

.colorWeak {
  filter: invert(80%);
}

@basicLayout-prefix-cls: ~'@{ant-prefix}-pro-basicLayout';

.@{basicLayout-prefix-cls} {
  &:not('.ant-pro-basicLayout-mobile') {
    ::-webkit-scrollbar {
      width: 6px;
      height: 6px;
    }

    ::-webkit-scrollbar-track {
      background: rgba(0, 0, 0, 0.06);
      border-radius: 3px;
      box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.08);
    }

    /* 滚动条滑块 */
    ::-webkit-scrollbar-thumb {
      background: rgba(0, 0, 0, 0.12);
      border-radius: 3px;
      box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.2);
    }
  }
  // BFC
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 100%;

  .ant-layout-header {
    &:not(.ant-pro-top-menu) {
      background: @component-background;
    }

    &.ant-pro-fixed-header {
      position: fixed;
      top: 0;
    }
  }

  &-content {
    position: relative;
    margin: 24px 24px 12px;
    transition: all 0.2s;
    .@{ant-prefix}-pro-page-header-wrap {
      margin: -24px -24px 0;
    }
    &-disable-margin {
      margin: 0;
      > div > .@{ant-prefix}-pro-page-header-wrap {
        margin: 0;
      }
    }
    > .ant-layout {
      max-height: 100%;
    }
  }

  // children should support fixed
  .@{basicLayout-prefix-cls}-is-children.@{basicLayout-prefix-cls}-fix-siderbar {
    height: 100vh;
    overflow: hidden;
    transform: rotate(0);
  }

  .@{basicLayout-prefix-cls}-has-header {
    .@{basicLayout-prefix-cls}-is-children {
      min-height: calc(100vh - @layout-header-height);
      &.@{basicLayout-prefix-cls}-fix-siderbar {
        height: calc(100vh - @layout-header-height);
      }
    }
  }

  // tech-page-container
  .@{basicLayout-prefix-cls}-has-header .tech-page-container {
    height: calc(100vh - @layout-header-height);
  }

  .@{basicLayout-prefix-cls}-has-header .@{basicLayout-prefix-cls}-is-children.@{basicLayout-prefix-cls}-has-header {
    .tech-page-container {
      height: calc(100vh - @layout-header-height - @layout-header-height);
    }
  }
}
</style>
