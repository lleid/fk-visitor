<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table
        :key="tableKey"
        ref="operatorGroupList"
        rowKey="id"
        :columns="columns"
        :rowSelection="null"
        :pagination="false"
        childrenColumnName="subGroups"
        :dataSource="dataSource"
        :defaultExpandAllRows="true"
        @refreshClick="refreshClick"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDel(record)">删除</a>
        </span>
      </c-table>
      <operator-group-create ref="createModal" @ok="handleOk" />
      <operator-group-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { mapState } from 'vuex'

import OperatorGroupCreate from './OperatorGroupCreate'
import OperatorGroupUpdate from './OperatorGroupUpdate'

import * as OperatorGroupService from '@/service/system/OperatorGroupService'

export default {
  components: {
    OperatorGroupCreate,
    OperatorGroupUpdate
  },
  data () {
    return {
      tableKey: 0,
      columns: [
        {
          title: '用户组名',
          dataIndex: 'name'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '230px',
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
      this.genOperatorGroupData()
    },
    handleOk () {
      this.genOperatorGroupData()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前用户组吗？',
        async onOk () {
          try {
            await OperatorGroupService.del(record.id)
          } catch (error) {
          }
          that.genOperatorGroupData()
        },
        onCancel () { }
      })
    },
    async genOperatorGroupData () {
      try {
        this.dataSource = await OperatorGroupService.groups()
        this.tableKey++
      } catch (e) {
      }
    }
  },
  created () {
    this.genOperatorGroupData()
  }
}
</script>
