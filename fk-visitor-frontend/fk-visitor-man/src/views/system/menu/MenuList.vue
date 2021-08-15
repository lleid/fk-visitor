<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table :key="tableKey" ref="menuList" rowKey="id" :rowSelection="null" :columns="columns" childrenColumnName="subMenus" :pagination="false" :dataSource="dataSource" :defaultExpandAllRows="true" @refreshClick="refreshClick">
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDel(record)">删除</a>
        </span>
      </c-table>
      <menu-create ref="createModal" @ok="handleOk" />
      <menu-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import MenuCreate from './MenuCreate'
import MenuUpdate from './MenuUpdate'

import * as SysMenuService from '@/service/system/SysMenuService'

export default {
  components: {
    MenuCreate,
    MenuUpdate
  },
  data () {
    return {
      tableKey: 0,
      columns: [
        {
          title: '名称',
          dataIndex: 'name',
          customRender: (value, row, index) => {
            if (row.icon) {
              return (
                <span>
                  <c-icon type={row.icon} class='menu-icon' />
                  <span>{value}</span>
                </span>
              )
            } else {
              return (
                <span>{value}</span>
              )
            }
          }
        },
        {
          title: '别名',
          dataIndex: 'alias',
          media: 'md'
        },
        {
          title: '路径',
          dataIndex: 'url',
          media: 'xl'
        },
        {
          title: '排序',
          dataIndex: 'code',
          media: 'xxl'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      dataSource: []
    }
  },
  computed: {
    ...mapState({
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config
    })
  },
  methods: {
    refreshClick () {
      this.getMenuData()
    },
    handleOk () {
      this.getMenuData()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前菜单吗？',
        onOk () {
          SysMenuService.del(record.id).then(res => {
            that.$message.info(res.message)
            that.getMenuData()
          })
        },
        onCancel () { }
      })
    },
    async getMenuData () {
      try {
        this.dataSource = await SysMenuService.all()
        this.tableKey++
      } catch (e) {
      }
    }
  },
  created () {
    this.getMenuData()
  }
}
</script>
<style scoped>
.menu-icon {
  margin-right: 8px;
}
</style>
