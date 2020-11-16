<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button class="operate-button" icon="delete" @click="batchDelete">导出</a-button>
    </div>
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
    handleExport () {
      OrderService.exportOrder(this.queryParam).then(res => {
        var fileURL = window.URL.createObjectURL(new Blob([res]))
        var fileLink = document.createElement('a')
        fileLink.href = fileURL
        fileLink.setAttribute('download', '访客记录.xlsx')
        document.body.appendChild(fileLink)
        fileLink.click()
      })
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
