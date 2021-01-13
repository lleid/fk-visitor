<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table
        ref="orderList"
        size="default"
        :rowSelection="null"
        :rowKey="record => record.id"
        :columns="columns"
        :data-loader="query"
      >
        <template slot="toolbar">
          <div class="table-query-block">
            <a-input style="width: 200px" v-model="queryParam.name" placeholder="姓名" />
          </div>
          <div class="table-query-block">
            <a-date-picker
              class="start-at-from"
              v-model="queryParam.from"
              placeholder="拜访日期从..."
              :allowClear="false"
            />
            <a-date-picker
              class="start-at-to"
              v-model="queryParam.to"
              placeholder="到..."
              :allowClear="false"
            />
          </div>
          <div class="table-query-block">
            <a-button type="link" @click="doQuery" style="padding: 0 4px">
              <a-icon type="search" />查询
            </a-button>
            <a-button type="link" @click="resetQuery" style="padding: 0 4px">
              <a-icon type="close" />重置
            </a-button>
          </div>
        </template>
        <span slot="action" slot-scope="text, record">
          <a @click="handleSignOut(record)" v-if="!record.isSignOut">签出</a>
        </span>
      </c-table>
      <order-sign-out ref="singOutModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'
import OrderSignOut from './OrderSignOut'

import * as OrderService from '@/service/data/OrderService'

export default {
  components: {
    OrderSignOut
  },
  data () {
    return {
      queryParam: {},
      querySelect: 'name',
      queryValue: '',
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
          title: '拜访日期',
          dataIndex: 'visitAt'
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
          title: '签出日期',
          dataIndex: 'signOutAt'
        },
        {
          title: '操作',
          dataIndex: 'action',
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
          const result = await OrderService.queryPage(Object.assign(param, p), {
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
    doQuery () {
      this.$refs.orderList.refresh()
    },
    resetQuery () {
      this.queryParam = {}
      this.$refs.orderList.refresh()
    },
    handleOk () {
      this.$refs.orderList.refresh()
    },
    handleSignOut (record) {
      this.$refs.singOutModal.edit(record)
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

.start-at-from,
.start-at-to {
  width: 160px;
}
</style>
