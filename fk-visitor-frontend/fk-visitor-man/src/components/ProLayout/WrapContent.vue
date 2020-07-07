<template>
  <a-layout-content>
    <div class="ant-pro-basicLayout-children-content-wrap">
      <grid-content>
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
