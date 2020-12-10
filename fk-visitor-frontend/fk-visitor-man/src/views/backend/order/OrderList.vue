<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table
        ref="orderList"
        size="default"
        :rowKey="record => record.id"
        :columns="columns"
        :data-loader="query"
      >
        <template slot="toolbar">
          <div class="table-query-block">
            <a-input style="width: 200px" v-model="queryParam.name" placeholder="姓名" />
          </div>
          <div class="table-query-block">
            <a-input style="width: 200px" v-model="queryParam.mobile" placeholder="电话" />
          </div>
          <div class="table-query-block">
            <a-button type="link" @click="doQuery" style="padding: 0 4px"><a-icon type="search" />查询</a-button>
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
          dataIndex: 'name',
          media: 'md'
        },
        {
          title: '电话',
          dataIndex: 'mobile'
        },
        {
          title: '证件号',
          dataIndex: 'idCard'
        },
        {
          title: '公司',
          dataIndex: 'company'
        },
        {
          title: '职务',
          dataIndex: 'department'
        },
        {
          title: '拜访事由',
          dataIndex: 'purpose',
          customRender: (text) => text ? text.cnName : ''
        },
        {
          title: '拜访日期',
          dataIndex: 'visitAt'
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
          console.log('in===')
          console.log(param)
          console.log(this.queryParam)
          const result = await OrderService.queryPage(Object.assign(param, this.queryParam), {
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
</style>
