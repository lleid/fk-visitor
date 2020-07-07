<template>
  <div :class="{ 'ant-pro-grid-content': true, 'wide' : contentWidth === 'Fixed' }">
    <slot />
  </div>
</template>

<script>
import VueTypes from 'vue-types'

export const GridContentProps = {
  contentWidth: VueTypes.oneOf(['Fluid', 'Fixed']).def('Fluid')
}

export default {
  name: 'GridContent',
  data () {
    return {}
  },
  props: {
    ...GridContentProps
  }
}
</script>

<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

@pro-layout-grid-content-prefix-cls: ~'@{ant-prefix}-pro-grid-content';

.@{pro-layout-grid-content-prefix-cls} {
  width: 100%;
  min-height: 100%;
  transition: 0.3s;
  &.wide {
    max-width: 1200px;
    margin: 0 auto;
  }
}
</style>
