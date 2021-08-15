/* eslint-disable vue/no-unused-vars */
<template>
  <page-header-wrapper :menu-data="menuData" :breadcrumb-list="breadcrumbs" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add(teamId)">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table :rowSelection="null" :showRefresh="false" :showFullScreen="false" ref="staffList" size="default" :rowKey="record => record.id" :columns="columns" :data-loader="query">
        <template slot="toolbar">
          <div class="table-query-block">
            <a-input style="width: 200px" v-model="queryParam.name" placeholder="姓名" />
          </div>
          <div class="table-query-block">
            <a-button type="primary" class="operate-btn" @click="onSearch">搜索</a-button>
            <a-button @click="resetSearch" class="operate-btn"> 重置 </a-button>
          </div>
        </template>
        <span slot="action" slot-scope="text, record" v-if="!record.isDeleted">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDel(record)">删除</a>
        </span>
      </c-table>
      <staff-create ref="createModal" @ok="handleOk" />
      <staff-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import StaffCreate from './StaffCreate'
import StaffUpdate from './StaffUpdate'

import * as AppointmentService from '@/service/order/AppointmentService'

export default {
  components: {
    StaffCreate,
    StaffUpdate
  },
  data () {
    return {
      queryParam: {},
      teamId: undefined,
      teamName: undefined,
      breadcrumbs: [
        {
          title: '预约访客',
          href: '/order'
        },
        {
          title: '团队预约',
          href: '/order/team'
        }
      ],
      columns: [
        {
          title: '姓名',
          dataIndex: 'name'
        },
        {
          title: '联系方式',
          dataIndex: 'mobile'
        },
        {
          title: '职务',
          dataIndex: 'title'
        },
        {
          title: '证件号',
          dataIndex: 'idCard'
        },
        {
          title: '接收验证码',
          dataIndex: 'isMessage',
          customRender: (text) => text ? '是' : '否'
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
          this.queryParam.teamId = this.teamId
          const result = await AppointmentService.queryPage(Object.assign(param, this.queryParam), {
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
  async created () {
    if (this.$route.query.teamId) this.teamId = this.$route.query.teamId
  },
  methods: {
    onSearch () {
      this.$refs.staffList.refresh(true)
    },
    resetSearch () {
      this.queryParam = {}
      this.$refs.staffList.refresh(true)
    },
    handleOk () {
      this.$refs.staffList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前成员吗？',
        onOk () {
          AppointmentService.del(record.id).then(res => {
            that.$refs.staffList.refresh()
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
