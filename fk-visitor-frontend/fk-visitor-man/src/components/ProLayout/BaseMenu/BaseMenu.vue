<script>
import Vue from 'vue'

import omit from 'lodash.omit'
import VueTypes from 'vue-types'

import { Menu, Icon } from 'ant-design-vue'
import { menuProps as MenuProps } from 'ant-design-vue/es/menu'

import { SettingProps } from '../ProLayoutSetting'
import { ProLayoutStateProps } from '../ProLayoutProps'

import * as MenuRenderUtils from './utils'

import { isUrl } from '../utils/utils'

export const BaseMenuDataProps = {
  menuData: VueTypes.arrayOf(VueTypes.shape({
    authority: VueTypes.arrayOf(String),
    children: VueTypes.arrayOf(Object),
    hideChildrenInMenu: VueTypes.bool,
    hideInMenu: VueTypes.bool,
    icon: VueTypes.string,
    name: VueTypes.string.isRequired,
    localeName: VueTypes.object,
    key: VueTypes.string,
    path: VueTypes.string,
    relatedPath: VueTypes.arrayOf(String),
    target: VueTypes.oneOf(['_blank', '_self'])
  }).loose).def([])
}

export const BaseMenuProps = {
  ...SettingProps,
  ...ProLayoutStateProps,
  ...MenuProps,
  ...BaseMenuDataProps,
  locale: VueTypes.string
}

export default {
  name: 'BaseMenu',
  components: {
    Icon
  },
  props: {
    ...BaseMenuProps
  },
  data () {
    return {
      iconfontComponent: this.iconfontUrl !== undefined && this.iconfontUrl !== '' ? Icon.createFromIconfontCN({ scriptUrl: this.iconfontUrl }) : undefined,
      renderMenuData: MenuRenderUtils.filterMenuData(this.menuData),
      openKeysData: this.collapsed ? [] : this.openKeys,
      selectedKeysData: this.selectedKeys,
      cachedOpenKeysData: this.openKeys,
      cachedImageIcons: {}
    }
  },
  mounted () {
    this.updateMenuState()
  },
  watch: {
    openKeys (val) {
      if (this.collapsed) {
        this.cachedOpenKeysData = val
      } else {
        this.openKeysData = val
      }
    },
    selectedKeys (val) {
      this.selectedKeysData = val
    },
    collapsed (val) {
      if (val) {
        this.cachedOpenKeysData = this.openKeysData !== undefined ? this.openKeysData.concat() : []
        this.openKeysData = []
      } else {
        this.openKeysData = this.cachedOpenKeysData
      }
    },
    menuData (val) {
      this.renderMenuData = MenuRenderUtils.filterMenuData(val)
    },
    iconfontUrl (val) {
      if (val !== undefined && val !== '') {
        this.iconfontComponent = Icon.createFromIconfontCN({ scriptUrl: val })
      } else {
        this.iconfontComponent = undefined
      }
    },
    $route: function () {
      this.updateMenuState()
    }
  },
  methods: {
    onOpenChange (openKeysData) {
      this.openKeysData = openKeysData
      if (this.mode === 'inline' && !this.collapsed) {
        this.$emit('open', openKeysData)
      }
    },
    onMenuSelect (e) {
      this.selectedKeysData = e.selectedKeys
      this.$emit('select', e)
    },
    updateMenuState () {
      if (this.renderMenuData === undefined) {
        return
      }
      let currentOpenKeys
      if (this.mode === 'inline') {
        let pathOpenKeys = MenuRenderUtils.findOpenKeysByPath(this.renderMenuData, this.$route.fullPath)
        if (pathOpenKeys === undefined || pathOpenKeys.length === 0) {
          pathOpenKeys = MenuRenderUtils.findOpenKeysByPath(this.renderMenuData, this.$route.path)
        }
        if (this.collapsed) {
          if (this.cachedOpenKeysData === undefined) {
            this.cachedOpenKeysData = pathOpenKeys
          } else {
            pathOpenKeys.forEach((key) => {
              this.cachedOpenKeysData.indexOf(key) === -1 && this.cachedOpenKeysData.push(key)
            })
          }
          currentOpenKeys = this.cachedOpenKeysData
        } else {
          if (this.openKeysData === undefined) {
            this.openKeysData = pathOpenKeys
          } else {
            pathOpenKeys.forEach((key) => {
              this.openKeysData.indexOf(key) === -1 && this.openKeysData.push(key)
            })
          }
          currentOpenKeys = this.openKeysData
        }
      }
      let pathSelectedKeys = MenuRenderUtils.findSelectedKeysByPath(this.renderMenuData, this.$route.fullPath)
      if (pathSelectedKeys === undefined || pathSelectedKeys.length === 0) {
        pathSelectedKeys = MenuRenderUtils.findSelectedKeysByPath(this.renderMenuData, this.$route.path)
      }
      this.selectedKeysData = pathSelectedKeys

      this.$emit('update', { openKeys: currentOpenKeys, selectedKeys: this.selectedKeysData })
    },
    // render
    renderSubMenuOrItem (h, menuItem) {
      return menuItem.children ? this.renderSubMenu(h, menuItem) : this.renderMenuItem(h, menuItem)
    },
    renderMenuItem (h, menuItem) {
      const that = this
      const isHttpUrl = isUrl(menuItem.path)
      const linkTag = isHttpUrl ? 'a' : 'router-link'
      let props = {}
      let attrs = {}
      if (menuItem.target !== undefined) {
        attrs = { ...attrs, target: menuItem.target }
      }
      if (isHttpUrl) {
        attrs = { ...attrs, href: menuItem.path, target: menuItem.target === undefined ? '_blank' : menuItem.target }
      } else {
        props = { to: { path: menuItem.path } }
        attrs = { target: menuItem.target }
      }
      return h(Menu.Item, { key: menuItem.key || menuItem.path }, [
        h(linkTag, { ...{ props, attrs } }, [
          this.renderIcon(h, menuItem),
          h('span', that.getMenuLocalName(menuItem))
        ])
      ])
    },
    getMenuLocalName (menuItem) {
      if (this.menu !== undefined && this.menu.locale === true && this.locale !== undefined && this.locale.trim() !== '' && menuItem.localeName !== undefined) {
        return menuItem.localeName[this.locale] === undefined ? menuItem.name : menuItem.localeName[this.locale]
      } else {
        return menuItem.name
      }
    },
    renderSubMenu (h, menuItem) {
      const that = this
      const subMenuItems = []
      menuItem.children.forEach(item => subMenuItems.push(this.renderSubMenuOrItem(h, item)))
      return h(Menu.SubMenu, { key: menuItem.key || menuItem.path }, [
        h('span', { slot: 'title' }, [
          this.renderIcon(h, menuItem),
          h('span', that.getMenuLocalName(menuItem))
        ])
      ].concat(subMenuItems))
    },
    // 采用缓存避免因动态编译造成的图标闪烁问题
    getImageIcon (menuItem) {
      if (this.cachedImageIcons[menuItem.key] === undefined) {
        const _icon = Vue.component('icon-' + menuItem.key.toLowerCase(), {
          template: `<img src="${menuItem.icon}" alt="icon" class="ant-pro-sider-menu-icon" />`
        })
        this.cachedImageIcons[menuItem.key] = _icon
      }
      return this.cachedImageIcons[menuItem.key]
    },
    renderIcon (h, menuItem) {
      if (menuItem.icon === undefined || menuItem.icon === '') {
        return null
      }
      if (isUrl(menuItem.icon)) {
        return h('Icon', {
          props: {
            component: this.getImageIcon(menuItem)
          }
        })
      }
      if (menuItem.icon.startsWith(this.iconfontPrefix) && this.iconfontComponent !== undefined) {
        return h(this.iconfontComponent, { props: { type: menuItem.icon } })
      }
      return h(Icon, { props: { type: menuItem.icon } })
    }
  },
  render (h) {
    if (this.renderMenuData === undefined) {
      return
    }
    const dynamicProps = {
      props: {
        ...(this.mode !== 'inline' ? omit(this.$props, ['inlineCollapsed']) : this.$props),
        ...(this.mode === 'inline' && this.collapsed ? { inlineCollapsed: true } : {}),
        theme: this.navTheme,
        openKeys: this.openKeysData,
        selectedKeys: this.selectedKeysData
      },
      on: {
        openChange: this.onOpenChange,
        select: this.onMenuSelect
      }
    }
    const menuDom = this.renderMenuData.map(item => {
      return this.renderSubMenuOrItem(h, item)
    })
    return h(Menu, dynamicProps, menuDom)
  }

}
</script>
