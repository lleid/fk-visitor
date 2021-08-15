/* eslint-disable vue/no-unused-vars */
<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table :rowSelection="null" :showRefresh="false" :showFullScreen="false" ref="teamList" size="default" :rowKey="record => record.id" :columns="columns" :data-loader="query">
        <template slot="toolbar">
          <div class="table-query-block">
            <a-input style="width: 200px" v-model="queryParam.company" placeholder="团队名称" />
          </div>
          <div class="table-query-block">
            <a-date-picker class="start-at-from" style="width: 200px" v-model="queryParam.from" placeholder="预约日期从..." :allowClear="false" />
            <a-date-picker class="start-at-to" style="width: 200px" v-model="queryParam.to" placeholder="到..." :allowClear="false" />
          </div>
          <div class="table-query-block">
            <a-button type="primary" class="operate-btn" @click="onSearch">搜索</a-button>
            <a-button @click="resetSearch" class="operate-btn"> 重置 </a-button>
          </div>
        </template>
        <span slot="action" slot-scope="text, record" v-if="!record.isDeleted">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleStaff(record)">成员</a>
          <a-divider type="vertical" />
          <a @click="handleDel(record)">删除</a>
        </span>
      </c-table>
      <team-create ref="createModal" @ok="handleOk" />
      <team-update ref="updateModal" @ok="handleOk" />

    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import TeamCreate from './TeamCreate'
import TeamUpdate from './TeamUpdate'

import * as TeamService from '@/service/order/TeamService'

export default {
  components: {
    TeamCreate,
    TeamUpdate
  },
  data () {
    return {
      queryParam: {},
      columns: [
        {
          title: '团队名称',
          dataIndex: 'company'
        },
        {
          title: '预约日期',
          dataIndex: 'orderAt'
        },
        {
          title: '拜访事由',
          dataIndex: 'purpose',
          customRender: (text) => text ? text.cnName : ''
        },
        {
          title: '参观区域',
          dataIndex: 'visitArea',
          customRender: (text) => text ? text.cnName : ''
        },
        {
          title: '受访人',
          dataIndex: 'interviewer'
        },
        {
          title: '是否到达',
          dataIndex: 'isCame',
          customRender: (text) => text ? '是' : '否'
        },
        {
          title: '是否删除',
          dataIndex: 'isDeleted',
          customRender: (text) => text ? '是' : '否'
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
          const p = {}
          if (this.queryParam.company !== '') {
            p['company'] = this.queryParam.company
          }
          if (this.queryParam.from != null) {
            p['from'] = this.queryParam.from.format('yyyy-MM-DD')
          }
          if (this.queryParam.to != null) {
            p['to'] = this.queryParam.to.format('yyyy-MM-DD')
          }
          const result = await TeamService.queryPage(Object.assign(param, p), {
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
  methods: {
    onSearch () {
      this.$refs.teamList.refresh(true)
    },
    resetSearch () {
      this.queryParam = {}
      this.$refs.teamList.refresh(true)
    },
    handleOk () {
      this.$refs.teamList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleStaff (record) {
      this.$router.push({
        path: '/order/staff',
        query: { teamId: record.id, teamName: record.name }
      })
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前团队预约吗？',
        onOk () {
          TeamService.del(record.id).then(res => {
            that.$refs.teamList.refresh()
          })
        },
        onCancel () { }
      })
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
