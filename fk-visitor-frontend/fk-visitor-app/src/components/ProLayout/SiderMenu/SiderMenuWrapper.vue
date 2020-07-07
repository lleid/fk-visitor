<script>
import VueTypes from 'vue-types'

import { Drawer } from 'ant-design-vue'
import SiderMenu, { SiderMenuProps } from './SiderMenu'

export const SiderMenuWrapperProps = {
  ...SiderMenuProps,
  siderDrawerClass: VueTypes.object.def({}),
  siderDrawerStyle: VueTypes.object.def({})
}

export default {
  name: 'SiderMenuWrapper',
  components: {
    Drawer,
    SiderMenu
  },
  props: {
    ...SiderMenuWrapperProps
  },
  data () {
    return {
    }
  },
  watch: {
  },
  computed: {
    drawerVisible () {
      return !this.collapsed
    }
  },
  methods: {
    onDrawerClose: function () {
      this.$emit('close')
    }
  },
  render () {
    if (!this.siderMenuRender) {
      return
    }
    return (<Drawer
      {
      ...{
        props: {
          visible: this.drawerVisible,
          placement: 'left',
          class: {
            'ant-pro-drawer-sider-menu': true,
            ...this.siderDrawerClass
          },
          style: {
            padding: 0,
            height: '100vh',
            ...this.siderDrawerStyle
          },
          width: this.siderWidth,
          bodyStyle: {
            height: '100vh',
            padding: 0
          }
        }
      }
      }
      onClose={this.onDrawerClose}
    >(
        <SiderMenu
        {...{
          props: {
            ...this.$props,
            collapsed: false
          }
        }}
      >
        {Object.keys(this.$slots).map(name => (
          <template slot={name}>{this.$slots[name]}</template>
        ))}
      </SiderMenu>)
    </Drawer>)
  }
}
</script>
