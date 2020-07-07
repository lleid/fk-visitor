<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table
        ref="operatorRoleList"
        :rowKey="record => record.id"
        :columns="columns"
        :data-loader="query"
        :rowSelection="null"
      >
        <div slot="toolbar">
          <a-input-search v-model="queryValue" allowClear @search="onSearch">
            <a-select v-model="querySelect" slot="addonBefore">
              <a-select-option value="name">
                名称
              </a-select-option>
              <a-select-option value="keyCode">
                别名
              </a-select-option>
            </a-select>
            <a-button slot="enterButton">
              <a-icon type="search" />
            </a-button>
          </a-input-search>
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
      queryParams: {},
      querySelect: 'name',
      queryValue: '',
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
          dataIndex: 'keyCode',
          media: 'md'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
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
  created () {

  },
  methods: {
    onSearch () {
      this.queryParam = {}
      this.queryParam[this.querySelect] = this.queryValue
      this.$refs.operatorRoleList.refresh()
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
