<template>
  <div class="ant-pro-global-header">
    <span v-if="isMobile" class="ant-pro-global-header-logo" key="logo">
      <slot name="logo">
        <logo :logo="logo" :path="path" :show-title="false" />
      </slot>
    </span>
    <span
      v-if="collapsedButtonRender && siderMenuRender"
      class="ant-pro-global-header-trigger"
      @click="toggle"
    >
      <a-icon :type="collapsedData ? 'menu-unfold' : 'menu-fold'" />
    </span>
    <div :style="{ flex: 1 }" />
    <slot name="right" />
  </div>
</template>

<script>
import VueTypes from 'vue-types'

import { Icon } from 'ant-design-vue'

import Logo, { LogoProps } from '../Logo'
import { ProLayoutStateProps } from '../ProLayoutProps'

export const GlobalHeaderProps = {
  ...LogoProps,
  ...ProLayoutStateProps,
  collapsedButtonRender: VueTypes.bool.def(true),
  siderMenuRender: VueTypes.bool.def(true)
}

export default {
  name: 'GlobalHeader',
  components: {
    Icon,
    Logo
  },
  props: {
    ...GlobalHeaderProps
  },
  data () {
    return {
      collapsedData: this.collapsed
      // originalCollapsedData: this.collapsed
    }
  },
  created: function () {
  },
  watch: {
    // isMobile: function (newVal) {
    //   if (newVal) {
    //     this.originalCollapsedData = this.collapsedData
    //     this.collapsedData = true
    //   } else {
    //     this.collapsedData = this.originalCollapsedData
    //   }
    // },
    collapsed: function (newVal) {
      this.collapsedData = newVal
      // this.originalCollapsedData = newVal
    }
  },
  methods: {
    toggle () {
      this.collapsedData = !this.collapsedData
      this.$emit('collapse', this.collapsedData)
    }
  }
}
</script>

<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';
@pro-layout-global-header-prefix-cls: ~'@{ant-prefix}-pro-global-header';

@pro-layout-header-bg: @component-background;
@pro-layout-header-hover-bg: @component-background;
@pro-layout-header-box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

.@{pro-layout-global-header-prefix-cls} {
  position: relative;
  display: flex;
  align-items: center;
  height: @layout-header-height;
  padding: 0;
  background: @pro-layout-header-bg;
  box-shadow: @pro-layout-header-box-shadow;

  &-logo {
    display: inline-flex;
    align-items: center;
    height: @layout-header-height;
    padding: 0 0 0 24px;
    font-size: 20px;
    line-height: @layout-header-height;
    vertical-align: top;
    cursor: pointer;
    img {
      display: inline-block;
      height: 32px;
      vertical-align: middle;
    }
  }

  &-menu {
    .anticon {
      margin-right: 8px;
    }
    .ant-dropdown-menu-item {
      min-width: 160px;
    }
  }

  &-trigger {
    height: @layout-header-height;
    padding: 0 24px;
    font-size: 20px;
    cursor: pointer;
    transition: all 0.3s, padding 0s;
    &:hover {
      background: @pro-layout-header-hover-bg;
    }
  }

  .dark {
    height: @layout-header-height;
    .action {
      color: rgba(255, 255, 255, 0.85);
      > i {
        color: rgba(255, 255, 255, 0.85);
      }
      &:hover,
      &.opened {
        background: @primary-color;
      }
      .ant-badge {
        color: rgba(255, 255, 255, 0.85);
      }
    }
  }
}
</style>
