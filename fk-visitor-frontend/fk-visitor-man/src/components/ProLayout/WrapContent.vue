<template>
  <a-layout-content>
    <div class="ant-pro-basicLayout-children-content-wrap">
      <grid-content class="ant-pro-basicLayout-children-content-grid">
        <slot />
      </grid-content>
    </div>
  </a-layout-content>
</template>

<script>
import { Layout } from 'ant-design-vue'

import GridContent from './GridContent/GridContent'

export default {
  name: 'WrapContent',
  data () {
    return {}
  },
  components: {
    ALayoutContent: Layout.Content,
    GridContent
  }
}
</script>
<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

@basicLayout-prefix-cls: ~'@{ant-prefix}-pro-basicLayout';

.@{basicLayout-prefix-cls}-children-content-wrap {
  .@{basicLayout-prefix-cls}-children-content-grid {
    padding-bottom: 12px;
  }
}
</style>
