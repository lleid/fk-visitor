<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table
        ref="protocolList"
        size="default"
        :rowKey="record => record.id"
        :columns="columns"
        :data-loader="query"
      >
        <div slot="toolbar">
          <a-input-search v-model="queryValue" allowClear @search="onSearch">
            <a-select v-model="querySelect" slot="addonBefore">
              <a-select-option value="type">类型</a-select-option>
            </a-select>
            <a-button slot="enterButton">
              <a-icon type="search" />
            </a-button>
          </a-input-search>
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
      <protocol-create ref="createModal" @ok="handleOk" />
      <protocol-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import ProtocolCreate from './ProtocolCreate'
import ProtocolUpdate from './ProtocolUpdate'

import * as ProtocolService from '@/service/system/ProtocolService'

export default {
  components: {
    ProtocolCreate,
    ProtocolUpdate
  },
  data () {
    return {
      queryParam: {},
      querySelect: 'type',
      queryValue: '',
      columns: [
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title: '类型',
          dataIndex: 'type'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      query: async param => {
        try {
          const result = await ProtocolService.queryPage(Object.assign(param, this.queryParam), {
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
      this.$refs.protocolList.refresh()
    },
    handleOk () {
      this.$refs.protocolList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前用户角色信息吗？',
        onOk () {
          ProtocolService.del(record.id).then(res => {
            that.$refs.protocolList.refresh()
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
