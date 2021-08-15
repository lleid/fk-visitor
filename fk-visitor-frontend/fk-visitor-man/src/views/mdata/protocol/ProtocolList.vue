<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table ref="protocolList" size="default" :rowSelection="null" :showRefresh="false" :showFullScreen="false" :rowKey="record => record.id" :columns="columns" :data-loader="query" :scroll="{ x: true }">
        <div slot="toolbar">
          <div class="table-query-block">
            <a-input v-model="queryParam.name" placeholder="名称" />
          </div>
          <div class="table-query-block">
            <a-select mode="single" style="width:200px" allowClear v-model="queryParam.type" placeholder="请选择">
              <a-select-option value="CN">CN</a-select-option>
              <a-select-option value="EN">EN</a-select-option>
            </a-select>
          </div>
          <div class="table-query-block">
            <a-button type="primary" class="operate-btn" @click="onSearch">搜索</a-button>
            <a-button @click="resetSearch" class="operate-btn"> 重置 </a-button>
          </div>
        </div>
        <span slot="action" slot-scope="text, record" v-if="!record.isDeleted">
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

import * as ProtocolService from '@/service/mdata/ProtocolService'

export default {
  components: {
    ProtocolCreate,
    ProtocolUpdate
  },
  data () {
    return {
      queryParam: {},
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
          fixed: 'right',
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
  methods: {
    onSearch () {
      this.$refs.protocolList.refresh(true)
    },
    resetSearch () {
      this.queryParam = {}
      this.$refs.protocolList.refresh(true)
    },
    handleOk () {
      this.$refs.protocolList.refresh(true)
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前用户协议吗？',
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
