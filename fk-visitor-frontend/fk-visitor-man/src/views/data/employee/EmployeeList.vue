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
      <c-table
        ref="employeeList"
        size="default"
        :rowSelection="null"
        :rowKey="record => record.id"
        :columns="columns"
        :data-loader="query"
      >
        <div slot="toolbar">
          <a-input-search v-model="queryValue" allowClear @search="onSearch">
            <a-select v-model="querySelect" slot="addonBefore">
              <a-select-option value="name">姓名</a-select-option>
              <a-select-option value="department">职务</a-select-option>
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

import * as EmployeeService from '@/service/system/EmployeeService'

export default {
  components: {
    EmployeeCreate,
    EmployeeUpdate
  },
  data () {
    return {
      queryParam: {},
      querySelect: 'name',
      queryValue: '',
      columns: [
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title: '职务',
          dataIndex: 'department'
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
  created () { },
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
      this.queryParam = {}
      this.queryParam[this.querySelect] = this.queryValue
      this.$refs.employeeList.refresh()
    },
    handleOk () {
      this.$refs.employeeList.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleDel (record) {
      console.log(record)
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
