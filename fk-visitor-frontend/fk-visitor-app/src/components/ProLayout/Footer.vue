<template>
  <a-layout-footer :style="{ padding: 0}">
    <slot>
      <global-footer :links="links" :copyright="copyright" />
    </slot>
  </a-layout-footer>
</template>

<script>
import VueTypes from 'vue-types'

import { Layout, Icon } from 'ant-design-vue'
import GlobalFooter, { GlobalFooterProps } from './GlobalFooter/GlobalFooter'

export const FooterViewProps = {
  ...GlobalFooterProps,
  footerRender: VueTypes.bool.def(true)
}

export default {
  name: 'FooterView',
  components: {
    Layout,
    Icon,
    GlobalFooter
  },
  props: {
    ...FooterViewProps
  }
}
</script>
