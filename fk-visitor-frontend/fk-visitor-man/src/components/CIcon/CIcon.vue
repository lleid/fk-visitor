<script>
import VueTypes from 'vue-types'
import { Icon } from 'ant-design-vue'

import ThemeConfig from '@/config/theme.config'

export const CIconProps = {
  type: VueTypes.string,
  spin: VueTypes.bool.def(false),
  rotate: VueTypes.number,
  theme: VueTypes.oneOf(['filled', 'outlined', 'twoTone']),
  twoToneColor: VueTypes.string
}

const CIcon = {
  name: 'CIcon',
  props: {
    ...CIconProps
  },
  data () {
    return {
      iconfontPrefix: ThemeConfig.iconfontPrefix,
      iconfontComponent: ThemeConfig.iconfontUrl !== undefined && ThemeConfig.iconfontUrl !== '' ? Icon.createFromIconfontCN({ scriptUrl: ThemeConfig.iconfontUrl }) : undefined
    }
  },
  render (h) {
    if (this.type === undefined || this.type === '') {
      return null
    }
    if (this.type.startsWith(this.iconfontPrefix) && this.iconfontComponent !== undefined) {
      return h(this.iconfontComponent, { props: { ...this.$props } })
    }
    return h(Icon, { props: { ...this.$props } })
  }
}

CIcon.install = function (Vue) {
  Vue.use(Icon)
  Vue.component(CIcon.name, CIcon)
}

export default CIcon

</script>
