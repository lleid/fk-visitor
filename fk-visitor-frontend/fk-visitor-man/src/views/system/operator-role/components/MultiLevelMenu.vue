<template>
  <a-tree defaultExpandAll checkable checkStrictly v-model="itemList" :treeData="menuData" @check="onMenuCheck">
    <template slot-scope="menu" slot="title">
      <c-icon v-if="menu.icon !== undefined && menu.icon.trim() !== ''" :type="menu.icon" class="menu-selection-icon" />
      {{ menu.name + '[' + menu.key + ']' }}
    </template>
  </a-tree>
</template>

<script>
export default {
  name: 'MultiLevelMenu',
  props: {
    // 对象定义见genMenuData方法
    initialData: {
      type: Array,
      default: function () {
        return []
      }
    },
    initialChecked: {
      type: Array,
      default: function () {
        return []
      }
    }
  },
  data () {
    return {
      prevMenuSelected: [],
      itemList: [...this.initialChecked]
    }
  },
  watch: {
    itemList: function (newVal) {
      if (Array.isArray(newVal)) {
        this.$emit('sent-out', newVal.join(','))
      }
    }
  },
  computed: {
    menuData: function () {
      return this.genMenuData(this.initialData)
    }
  },
  methods: {
    genMenuData (data) {
      return data.map(item => {
        const menuDataNode = {
          value: item.code,
          key: item.id,
          name: item.name,
          icon: item.icon,
          scopedSlots: {
            title: 'title'
          }
        }
        if (item.subMenus && item.subMenus.length > 0) {
          menuDataNode.children = this.genMenuData(item.subMenus)
        }
        return menuDataNode
      })
    },
    onMenuCheck (menu, info) {
      const checkedMenus = [...menu.checked]
      let nodes = []
      if (checkedMenus.length < this.prevMenuSelected.length) {
        nodes = this.prevMenuSelected.filter(key => !checkedMenus.includes(key))
      } else {
        nodes = checkedMenus.filter(key => !this.prevMenuSelected.includes(key))
      }
      this.renewMenuList(checkedMenus, this.menuData, nodes)
      this.renewMenuList(checkedMenus, this.menuData)
      this.prevMenuSelected = [...checkedMenus]
      this.itemList = checkedMenus
      this.$emit('send-out', checkedMenus.join())
    },
    renewMenuList (keyList, menuData, nodes) {
      if (keyList === undefined || keyList.length === 0) {
        return
      }
      if (menuData === undefined) {
        return
      }
      let isSelected = false
      menuData.map(menu => {
        if (nodes !== undefined) {
          // 流程1 选择父节点 各层子节点全选/全不选
          if (Array.isArray(menu.children)) {
            if (nodes.includes(menu.key)) {
              menu.children.map(subMenu => {
                if (keyList.includes(menu.key) && !keyList.includes(subMenu.key)) {
                  keyList.push(subMenu.key)
                } else if (!keyList.includes(menu.key) && keyList.includes(subMenu.key)) {
                  keyList.splice(keyList.indexOf(subMenu.key), 1)
                }
                nodes.push(subMenu.key)
              })
            }
            this.renewMenuList(keyList, menu.children, nodes)
          }
        } else {
          // 流程2 反选子节点 判断各层父节点下是否存在已选子节点 无则反选父节点
          if (
            !Array.isArray(menu.children)
          ) {
            if (keyList.includes(menu.key)) {
              isSelected = true
            }
          } else {
            if (this.renewMenuList(keyList, menu.children)) {
              if (!keyList.includes(menu.key)) {
                keyList.push(menu.key)
              }
              isSelected = true
            } else {
              const idx = keyList.findIndex(key => key === menu.key)
              if (idx >= 0) {
                // 注意此处不能赋值keyList为新数组(filter/slice 无用) 必须使用修改原数组的splice
                // JS传值问题
                keyList.splice(idx, 1)
              }
            }
          }
        }
      })
      return isSelected
    }
  }
}
</script>
