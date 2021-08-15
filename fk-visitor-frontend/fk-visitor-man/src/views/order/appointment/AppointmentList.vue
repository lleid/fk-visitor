<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table ref="appointmentList" size="default" :rowSelection="null" :showRefresh="false" :showFullScreen="false" :rowKey="record => record.id" :columns="columns" :data-loader="query" :scroll="{ x: true }">
        <template slot="toolbar">
          <div class="table-query-block">
            <a-input style="width: 200px" v-model="queryParam.name" placeholder="姓名" />
          </div>
          <div class="table-query-block">
            <a-date-picker class="start-at-from" style="width: 200px" v-model="queryParam.from" placeholder="预约日期从..." :allowClear="false" />
            <a-date-picker class="start-at-to" style="width: 200px" v-model="queryParam.to" placeholder="到..." :allowClear="false" />
          </div>
          <div class="table-query-block operate-btn">
            <a-button type="primary" class="operate-btn" @click="onSearch">搜索</a-button>
            <a-button @click="resetSearch" class="operate-btn"> 重置 </a-button>
          </div>
        </template>
        <span slot="action" slot-scope="text, record" v-if="!record.isDeleted && !record.isCame">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDel(record)">作废</a>
        </span>
      </c-table>
      <appointment-create ref="createModal" @ok="handleOk" />
      <appointment-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import AppointmentCreate from './AppointmentCreate'
import AppointmentUpdate from './AppointmentUpdate'

import * as AppointmentService from '@/service/order/AppointmentService'

export default {
  components: {
    AppointmentCreate,
    AppointmentUpdate
  },
  data () {
    return {
      queryParam: {},
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
          title: '公司',
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
          if (this.queryParam.name !== '') {
            p['name'] = this.queryParam.name
          }
          if (this.queryParam.from != null) {
            p['from'] = this.queryParam.from.format('yyyy-MM-DD')
          }
          if (this.queryParam.to != null) {
            p['to'] = this.queryParam.to.format('yyyy-MM-DD')
          }
          p.isTeam = false
          const result = await AppointmentService.queryPage(Object.assign(param, p), {
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
      this.$refs.appointmentList.refresh(true)
    },
    resetSearch () {
      this.queryParam = {}
      this.$refs.appointmentList.refresh(true)
    },
    handleOk () {
      this.$refs.appointmentList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定作废当前个人预约吗？',
        onOk () {
          AppointmentService.del(record.id).then(res => {
            that.$refs.appointmentList.refresh()
          })
        },
        onCancel () { }
      })
    }
  }
}
</script>

<style>
</style>
