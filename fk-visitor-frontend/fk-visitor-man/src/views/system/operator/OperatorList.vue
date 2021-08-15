<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
      <a-button class="operate-button" icon="delete" @click="batchDelete">批量删除</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table ref="operatorList" size="default" :rowSelection="null" :showRefresh="false" :showFullScreen="false" :rowKey="record => record.id" :columns="columns" :data-loader="query" :scroll="{ x: true }">
        <div slot="toolbar">
          <div class="table-query-block">
            <a-input v-model="queryParam.username" placeholder="用户名" />
          </div>
          <div class="table-query-block">
            <a-input v-model="queryParam.name" placeholder="姓名" />
          </div>
          <div class="table-query-block">
            <a-button type="primary" class="operate-btn" @click="onSearch">搜索</a-button>
            <a-button @click="resetSearch" class="operate-btn"> 重置 </a-button>
          </div>
        </div>
        <span slot="tag" slot-scope="tags">
          <a-tag v-for="tag in tags" :key="tag.id" color="green">{{ tag.name }}</a-tag>
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDel(record)">删除</a>
        </span>
      </c-table>
      <operator-create ref="createModal" @ok="handleOk" />
      <operator-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import OperatorCreate from './OperatorCreate'
import OperatorUpdate from './OperatorUpdate'

import * as OperatorService from '@/service/system/OperatorService'

export default {
  components: {
    OperatorCreate,
    OperatorUpdate
  },
  data () {
    return {
      queryParam: {},
      columns: [
        {
          title: '用户名',
          dataIndex: 'username'
        },
        {
          title: '姓名',
          dataIndex: 'name'
        },
        {
          title: '电话',
          dataIndex: 'mobile'
        },
        {
          title: '角色',
          dataIndex: 'roles',
          scopedSlots: { customRender: 'tag' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      query: async param => {
        try {
          const result = await OperatorService.queryPage(Object.assign(param, this.queryParam), {
            showLoading: false
          })
          return result
        } catch (e) { }
      }
    }
  },
  computed: {
    ...mapState({
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config
    })
  },
  created () { },
  methods: {
    onSearch () {
      this.$refs.operatorList.refresh()
    },
    resetSearch () {
      this.queryParam = {}
      this.$refs.operatorList.refresh(true)
    },
    handleOk () {
      this.$refs.operatorList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前用户信息吗？',
        onOk () {
          OperatorService.del(record.id).then(res => {
            that.$refs.operatorList.refresh()
          })
        },
        onCancel () { }
      })
    },
    batchDelete () {
      const selectedRowKeys = this.$refs.operatorList.selectedRowKeys
      const that = this
      if (selectedRowKeys !== undefined && selectedRowKeys.length > 0) {
        this.$confirm({
          title: '批量删除用户',
          content: '是否确定批量删除用户?',
          okText: '是',
          okType: 'danger',
          cancelText: '否',
          onOk () {
            OperatorService.batchDel({ ids: selectedRowKeys.join(',') }, { showLoading: false }).then(res => {
              that.$message.info(res.message)
              that.$refs.operatorList.refresh()
              that.$refs.operatorList.selectedRowKeys = []
            })
          },
          onCancel () { }
        })
      } else {
        this.$message.warning('请至少选择一条数据')
      }
    }
  }
}
</script>

<style>
.operate-button {
  margin-left: 8px;
}
.search-form {
  padding: 14px;
}
</style>
