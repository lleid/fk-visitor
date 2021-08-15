<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table ref="operatorRoleList" :rowKey="record => record.id" :columns="columns" :data-loader="query" :scroll="{ x: true }" :rowSelection="null" :showRefresh="false" :showFullScreen="false">
        <div slot="toolbar">
          <div class="table-query-block">
            <a-input v-model="queryParam.name" placeholder="名称" />
          </div>
          <div class="table-query-block">
            <a-button type="primary" class="operate-btn" @click="onSearch">搜索</a-button>
            <a-button @click="resetSearch" class="operate-btn"> 重置 </a-button>
          </div>
        </div>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDel(record)">删除</a>
        </span>
      </c-table>
      <operator-role-create ref="createModal" @ok="handleOk" />
      <operator-role-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import OperatorRoleCreate from './OperatorRoleCreate'
import OperatorRoleUpdate from './OperatorRoleUpdate'

import * as OperatorRoleService from '@/service/system/OperatorRoleService'

export default {
  components: {
    OperatorRoleCreate,
    OperatorRoleUpdate
  },
  data () {
    return {
      loading: false,
      queryParam: {},
      columns: [
        {
          title: '名称',
          dataIndex: 'name',
          customRender: (value, row, index) => {
            if (row.isSupervisor) {
              return (
                <span>
                  <span class="is-supervisor">{value}</span>
                  <a-tag color="blue">超级管理员</a-tag>
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
          dataIndex: 'keyCode'
        },
        {
          title: '操作',
          dataIndex: 'action',
          fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      query: async (param) => {
        try {
          const result = await OperatorRoleService.queryByPage(Object.assign(param, this.queryParam), {
            showLoading: false,
            showSuccess: false
          })
          return result
        } catch (e) {
        }
      }
    }
  },
  computed: {
    ...mapState({
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config
    })
  },
  methods: {
    onSearch () {
      this.$refs.operatorRoleList.refresh()
    },
    resetSearch () {
      this.queryParam = {}
      this.$refs.operatorRoleList.refresh(true)
    },
    handleOk () {
      this.$refs.operatorRoleList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前用户角色信息吗？',
        onOk () {
          OperatorRoleService.del(record.id).then(res => {
            that.$message.info(res.message)
            that.$refs.operatorRoleList.refresh()
          })
        },
        onCancel () { }
      })
    }
  }
}
</script>
<style scoped>
.is-supervisor {
  margin-right: 8px;
}
</style>
