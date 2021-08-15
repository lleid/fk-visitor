<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table :rowSelection="null" :showRefresh="false" :showFullScreen="false" ref="stationList" size="default" :rowKey="record => record.id" :columns="columns" :data-loader="query" :scroll="{ x: true }">
        <div slot="toolbar">
          <div class="table-query-block">
            <a-input v-model="queryParam.name" placeholder="名称" />
          </div>
          <div class="table-query-block">
            <a-button type="primary" class="operate-btn" @click="onSearch">搜索</a-button>
            <a-button @click="resetSearch" class="operate-btn"> 重置 </a-button>
          </div>
        </div>
        <span slot="tag" slot-scope="tags">
          <a-tag v-for="tag in tags" :key="tag.id" color="green">{{ tag.name }}</a-tag>
        </span>
        <span slot="action" slot-scope="text, record" v-if="!record.isDeleted">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDel(record)">删除</a>
        </span>
      </c-table>
      <station-create ref="createModal" @ok="handleOk" />
      <station-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import StationCreate from './StationCreate'
import StationUpdate from './StationUpdate'

import * as StationService from '@/service/system/StationService'

export default {
  components: {
    StationCreate,
    StationUpdate
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
          title: '地址',
          dataIndex: 'address'
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
          const result = await StationService.queryPage(Object.assign(param, this.queryParam), {
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
      this.$refs.stationList.refresh()
    },
    resetSearch () {
      this.queryParam = {}
      this.$refs.stationList.refresh(true)
    },
    handleOk () {
      this.$refs.stationList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定删除当前站点信息吗？',
        onOk () {
          StationService.del(record.id).then(res => {
            that.$refs.stationList.refresh()
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
