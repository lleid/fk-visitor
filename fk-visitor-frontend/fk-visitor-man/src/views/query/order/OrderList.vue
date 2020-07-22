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
        <div slot="toolbar">
          <a-input-search v-model="queryValue" allowClear @search="onSearch">
            <a-select v-model="querySelect" slot="addonBefore">
              <a-select-option value="name">姓名</a-select-option>
              <a-select-option value="mobile">电话</a-select-option>
            </a-select>
            <a-button slot="enterButton">
              <a-icon type="search" />
            </a-button>
          </a-input-search>
        </div>
        <span slot="action" slot-scope="text, record">
          <a @click="handleSignOut(record)" v-if="!record.isSignOut">签出</a>
        </span>
      </c-table>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import * as OrderService from '@/service/data/OrderService'

export default {
  components: {
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
          dataIndex: 'job'
        },
        {
          title: '拜访事由',
          dataIndex: 'purpose'
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
    onSearch () {
      this.queryParam = {}
      this.queryParam[this.querySelect] = this.queryValue
      this.$refs.orderList.refresh()
    },
    handleOk () {
      this.$refs.orderList.refresh()
    },
    handleSignOut (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定签出当前访客信息吗？',
        onOk () {
          OrderService.singOut(record.id).then(res => {
            that.$refs.orderList.refresh()
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
