<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table
        ref="customerList"
        size="default"
        :rowKey="record => record.id"
        :columns="columns"
        :data-loader="query"
      >
        <div slot="toolbar">
          <a-input-search v-model="queryValue" allowClear @search="onSearch">
            <a-select v-model="querySelect" slot="addonBefore">
              <a-select-option value="username">用户名</a-select-option>
              <a-select-option value="name">姓名</a-select-option>
              <a-select-option value="mobile">电话</a-select-option>
            </a-select>
            <a-button slot="enterButton">
              <a-icon type="search" />
            </a-button>
          </a-input-search>
        </div>
        <span slot="tag" slot-scope="tags">
          <a-tag v-for="tag in tags" :key="tag.id" color="green">{{ tag.name }}</a-tag>
        </span>
      </c-table>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import * as CustomerService from '@/service/data/CustomerService'

export default {
  components: {
  },
  data () {
    return {
      queryParam: {},
      querySelect: 'username',
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
        }
      ],
      query: async param => {
        try {
          const result = await CustomerService.queryPage(Object.assign(param, this.queryParam), {
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
      this.$refs.customerList.refresh()
    },
    handleOk () {
      this.$refs.customerList.refresh()
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
