<script>
import VueTypes from 'vue-types'

import { isUrl } from './utils/utils'

export const LogoProps = {
  path: VueTypes.string.def('/'),
  logo: VueTypes.string.def('https://gw.alipayobjects.com/zos/antfincdn/PmY%24TNNDBI/logo.svg'),
  title: VueTypes.string,
  showTitle: VueTypes.bool.def(true)
}

export default {
  name: 'Logo',
  props: {
    ...LogoProps
  },
  computed: {
    renderLogo: function () {
      return this.logo !== undefined && this.logo.trim() !== ''
    },
    renderTitle: function () {
      return this.showTitle && this.title !== undefined && this.title.trim() !== ''
    }
  },
  methods: {
    onClick (e) {
      if (this.path !== undefined && this.path.trim() !== '') {
        if (isUrl(this.path)) {
          window.open(this.path, '_blank')
        } else {
          if (!this.$route.matched.some((route) => route.regex.test(this.path))) {
            this.$router.push({ path: this.path })
          }
        }
      }
      this.$emit('click', e)
    }
  },
  render (h) {
    if (this.renderLogo || this.renderTitle) {
      return h('a', {
        on: {
          click: this.onClick
        }
      }, [
        this.renderLogo ? h('img', { attrs: { src: this.logo, alt: 'logo' } }) : null,
        this.renderTitle ? h('h1', this.title) : null
      ])
    }
    return null
  }
}
</script>
