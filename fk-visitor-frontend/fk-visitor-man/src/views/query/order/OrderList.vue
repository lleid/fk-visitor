<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button class="operate-button" icon="delete" @click="handleExport">导出</a-button>
    </div>
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
            <a-select mode="single" style="width: 120px" allowClear v-model="queryParam.signOutType" placeholder="签出类型">
              <a-select-option value="10"> 自动 </a-select-option>
              <a-select-option value="20"> 手动 </a-select-option>
            </a-select>
          </div>
          <div class="table-query-block">
            <a-button type="link" @click="doQuery" style="padding: 0 4px"><a-icon type="search" />查询</a-button>
            <a-button type="link" @click="resetQuery" style="padding: 0 4px">
              <a-icon type="close" />重置
            </a-button>
          </div>
        </template>
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
          dataIndex: 'name'
        },
        {
          title: '电话',
          dataIndex: 'mobile'
        },
        {
          title: '公司',
          dataIndex: 'company'
        },
        {
          title: '拜访事由',
          dataIndex: 'purpose',
          customRender: (text) => text ? text.cnName : ''
        },
        {
          title: '拜访区域',
          dataIndex: 'visitArea',
          customRender: (text) => text ? text.cnName : ''
        },
         {
          title: '拜访时间',
          dataIndex: 'visitAt'
        },
        {
          title: '签出时间',
          dataIndex: 'signOutAt'
        },
        {
          title: '签出类型',
          dataIndex: 'signOutType',
          customRender: (text) => {
            console.log(text)
            return text !== undefined ? text === '10' ? '自动' : '手动' : ''
          }
        }
      ],
      query: async param => {
        try {
          const p = {}
          if (this.queryParam.name !== '') {
            p['name'] = this.queryParam.name
          }
           if (this.queryParam.signOutType !== '') {
            p['signOutType'] = this.queryParam.signOutType
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
    handleExport () {
      OrderService.exportOrder(this.queryParam, { responseType: 'blob', showLoading: false, showSuccess: false }).then(res => {
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
