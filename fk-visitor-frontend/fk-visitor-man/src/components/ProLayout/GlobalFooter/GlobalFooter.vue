<template>
  <footer class="ant-pro-global-footer">
    <div v-if="links !== undefined && links.length > 0" class="ant-pro-global-footer-links">
      <a
        v-for="link in links"
        :key="link.key"
        :title="link.title"
        :target="link.blankTarget ? '_blank' : '_self'"
        :href="link.href"
      >{{ link.title }}</a>
    </div>
    <div v-if="copyright !== undefined && copyright !== ''" class="ant-pro-global-footer-copyright">
      版权所有
      <a-icon type="copyright" />
      {{ copyright }}
    </div>
  </footer>
</template>

<script>
import VueTypes from 'vue-types'
import { Icon } from 'ant-design-vue'

export const GlobalFooterProps = {
  links: VueTypes.arrayOf(VueTypes.shape({
    key: String,
    title: String,
    href: String,
    blankTarget: VueTypes.bool.def(true)
  })),
  copyright: VueTypes.string.def('2020 复星凯特医药科技有限公司')
}

export default {
  name: 'GlobalFooter',
  data () {
    return {}
  },
  components: { Icon },
  props: {
    ...GlobalFooterProps
  }
}
</script>

<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

@pro-global-footer-prefix-cls: ~'@{ant-prefix}-pro-global-footer';

.@{pro-global-footer-prefix-cls} {
  margin: 12px 0;
  padding: 0 16px;
  text-align: center;

  &-links {
    margin-bottom: 8px;

    a {
      color: @text-color-secondary;
      transition: all 0.3s;

      &:not(:last-child) {
        margin-right: 40px;
      }

      &:hover {
        color: @text-color;
      }
    }
  }

  &-copyright {
    color: @text-color-secondary;
    font-size: @font-size-base;
  }
}
</style>
