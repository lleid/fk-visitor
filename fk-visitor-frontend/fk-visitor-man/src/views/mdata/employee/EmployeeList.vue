<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建</a-button>
      <a-upload name="file" :multiple="true" :showUploadList="false" :customRequest="upload">
        <a-button class="operate-button">
          <a-icon type="upload" />导入
        </a-button>
      </a-upload>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table ref="employeeList" size="default" :rowSelection="null" :showRefresh="false" :showFullScreen="false" :rowKey="record => record.id" :columns="columns" :data-loader="query" :scroll="{ x: true }">
        <div slot="toolbar">
          <div class="table-query-block">
            <a-input v-model="queryParam.name" placeholder="姓名" />
          </div>
          <div class="table-query-block">
            <a-input v-model="queryParam.enName" placeholder="英文名" />
          </div>
          <div class="table-query-block">
            <a-input v-model="queryParam.department" placeholder="部门" />
          </div>
          <div class="table-query-block">
            <a-button type="primary" class="operate-btn" @click="onSearch">搜索</a-button>
            <a-button @click="resetSearch" class="operate-btn"> 重置 </a-button>
          </div>
        </div>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
        </span>
      </c-table>
      <employee-create ref="createModal" @ok="handleOk" />
      <employee-update ref="updateModal" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import EmployeeCreate from './EmployeeCreate'
import EmployeeUpdate from './EmployeeUpdate'

import * as EmployeeService from '@/service/mdata/EmployeeService'

export default {
  components: {
    EmployeeCreate,
    EmployeeUpdate
  },
  data () {
    return {
      queryParam: {},
      columns: [
        {
          title: '姓名',
          dataIndex: 'name'
        },
        {
          title: '英文名',
          dataIndex: 'enName'
        },
        {
          title: '部门',
          dataIndex: 'department'
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
          const result = await EmployeeService.queryPage(Object.assign(param, this.queryParam), {
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
    upload (data) {
      const formData = new FormData()
      formData.append('file', data.file)
      const config = {
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      EmployeeService.upload(formData, config)

      setTimeout(() => {
        this.$refs.employeeList.refresh()
      }, 3000)
    },
    onSearch () {
      this.$refs.employeeList.refresh(true)
    },
    resetSearch () {
      this.queryParam = {}
      this.$refs.employeeList.refresh(true)
    },
    handleOk () {
      this.$refs.employeeList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        department: '确认信息',
        content: '确定删除当前员工信息吗？',
        onOk () {
          EmployeeService.del(record.id).then(res => {
            that.$refs.employeeList.refresh()
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
