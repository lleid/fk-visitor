<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table ref="purposeList" size="default" :rowSelection="null" :showRefresh="false" :showFullScreen="false" :rowKey="record => record.id" :columns="columns" :data-loader="query" :scroll="{ x: true }">
        <div slot="toolbar">
          <div class="table-query-block">
            <a-input v-model="queryParam.cnName" placeholder="中文名称" />
          </div>
          <div class="table-query-block">
            <a-input v-model="queryParam.enName" placeholder="英文名称" />
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
      <purpose-create ref="createModal" @ok="handleOk" />
      <purpose-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import PurposeCreate from './PurposeCreate'
import PurposeUpdate from './PurposeUpdate'

import * as PurposeService from '@/service/system/PurposeService'

export default {
  components: {
    PurposeCreate,
    PurposeUpdate
  },
  data () {
    return {
      queryParam: {},
      columns: [
        {
          title: '中文名称',
          dataIndex: 'cnName'
        },
        {
          title: '英文名称',
          dataIndex: 'enName'
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
          const result = await PurposeService.queryPage(Object.assign(param, this.queryParam), {
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
      this.$refs.purposeList.refresh()
    },
    resetSearch () {
      this.queryParam = {}
      this.$refs.purposeList.refresh(true)
    },
    handleOk () {
      this.$refs.purposeList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前拜访事由吗？',
        onOk () {
          PurposeService.del(record.id).then(res => {
            that.$refs.purposeList.refresh()
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
