<template>
  <div>
    <a-layout-header v-if="fixedHeader" />
    <a-layout-header
      :class="{
        'ant-pro-fixed-header': fixedHeader,
        'ant-pro-top-menu': useTopMenu
      }"
      :style="{
        padding: 0,
        width: needSettingWidth ? `calc(100% - ${collapsedData ? 80 : siderWidth}px)` : '100%',
        zIndex: 9,
        right: fixedHeader ? 0 : undefined,
      }"
    >
      <top-nav-header v-if="useTopMenu && !isMobile" v-bind="$props">
        <slot name="logo" slot="logo" />
        <slot name="right" slot="right" />
        <slot name="menu" slot="menu" />
      </top-nav-header>
      <global-header v-else v-bind="$props" @collapse="onCollapse">
        <slot name="logo" slot="logo" />
        <slot name="right" slot="right" />
      </global-header>
    </a-layout-header>
  </div>
</template>

<script>
import VueTypes from 'vue-types'
import { Layout } from 'ant-design-vue'

import { SettingProps } from './ProLayoutSetting'
import GlobalHeader, { GlobalHeaderProps } from './GlobalHeader/GlobalHeader'
import TopNavHeader, { TopNavHeaderProps } from './TopNavHeader/TopNavHeader'

export const HeaderViewProps = {
  ...SettingProps,
  ...GlobalHeaderProps,
  ...TopNavHeaderProps,
  siderWidth: VueTypes.number.def(256),
  headerRender: VueTypes.bool.def(true)
}

export default {
  name: 'HeaderView',
  components: {
    ALayoutHeader: Layout.Header,
    GlobalHeader,
    TopNavHeader
  },
  props: {
    ...HeaderViewProps
  },
  data () {
    return {
      collapsedData: this.collapsed
    }
  },
  watch: {
    collapsed (newValue) {
      this.collapsedData = this.collapsed
    }
  },
  computed: {
    needSettingWidth: function () {
      return this.fixedHeader && this.siderMenuRender && !this.useTopMenu && !this.isMobile
    },
    useTopMenu: function () {
      return this.layout === 'topmenu'
    }
  },
  methods: {
    onCollapse: function (collapsed) {
      this.collapsedData = collapsed
      this.$emit('collapse', this.collapsedData)
    }
  }
}
</script>

<style lang="less" scoped>
@import '~ant-design-vue/es/style/themes/default.less';

@pro-layout-fixed-header-prefix-cls: ~'@{ant-prefix}-pro-fixed-header';

.@{pro-layout-fixed-header-prefix-cls} {
  z-index: 9;
  width: 100%;
  transition: width 0.2s;
}
</style>
