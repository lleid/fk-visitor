<template>
  <c-modal title="添加角色" centered :use-tabs="true" :destroyOnClose="true" :visible="visible" :loading="loading" :confirmLoading="confirmLoading" @ok="handleSubmit" @cancel="handleClose">
    <a-form-model ref="roleForm" :colon="false" :model="form" :rules="rules" :labelCol="labelCol" :wrapperCol="wrapperCol" :validate-messages="validateMessages">
      <a-tabs v-model="activeTab" tab-position="left">
        <a-tab-pane key="basic" tab="基本信息">
          <a-form-model-item label="角色名称" prop="name">
            <a-input v-model="form.name" placeholder="请输入角色名称" :maxLength="8" />
          </a-form-model-item>
          <a-form-model-item label="角色别名" prop="keyCode">
            <a-input v-model="form.keyCode" placeholder="请输入角色别名" :maxLength="16" />
          </a-form-model-item>
          <a-form-model-item label="超级管理员?">
            <a-switch v-model="form.isSupervisor" />
          </a-form-model-item>
        </a-tab-pane>
        <a-tab-pane key="menu" tab="菜单权限">
          <a-form-model-item label="菜单权限">
            <div class="menu-select">
              <multi-level-menu :initialData="menuData" @send-out="getMenuIds"></multi-level-menu>
            </div>
          </a-form-model-item>
        </a-tab-pane>
      </a-tabs>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import * as OperatorRoleRule from './OperatorRoleRule'

import * as OperatorRoleService from '@/service/system/OperatorRoleService'
import * as SysMenuService from '@/service/system/SysMenuService'
import * as SysApiService from '@/service/system/SysApiService'

import MultiLevelMenu from './components/MultiLevelMenu'

export default {
  components: {
    MultiLevelMenu
  },
  data () {
    return {
      ...FormConfig,
      visible: false,
      loading: false,
      confirmLoading: false,
      form: {
        name: '',
        keyCode: '',
        isSupervisor: false,
        menuIds: {},
        permissionUrls: undefined
      },
      rules: {},
      activeTab: 'basic',
      menuData: [],
      apiData: [],
      rolePermissions: [],
      axiosConfig: {
        showLoading: false,
        showSuccess: false
      }
    }
  },
  watch: {
    rolePermissions: function (newVal, oldVal) {
      this.form.permissionUrls = newVal.join(',')
    }
  },
  methods: {
    async add () {
      this.visible = true
      this.loading = true
      try {
        const menuResult = await SysMenuService.all(this.axiosConfig)
        this.menuData = [...menuResult]
        const apiResult = await SysApiService.all(this.axiosConfig)
        const apiData = []
        for (let i = 0; i < apiResult.length; i++) {
          const transferItem = {
            key: apiResult[i],
            title: apiResult[i]
          }
          apiData.push(transferItem)
        }
        this.apiData = apiData
      } catch (e) {
      }
      this.loading = false
      this.rules = OperatorRoleRule.OperatorRoleRuleBuilder.build(this.form)
    },
    handleSubmit () {
      this.$refs.roleForm.validate(async (valid, e) => {
        if (valid) {
          this.confirmLoading = true

          try {
            await OperatorRoleService.create(this.form)
            this.rolePermissions = []
            this.$refs.roleForm.resetFields()
            this.handleClose()
            this.$emit('ok')
          } catch (e) { }

          this.confirmLoading = false
        } else {
          if (e.name !== undefined || e.keyCode !== undefined) {
            this.activeTab = 'basic'
          }
          this.confirmLoading = false
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs.roleForm !== undefined) {
        this.$refs.roleForm.resetFields()
      }
      this.visible = false
    },
    onApiChange (targetKeys, direction, moveKeys) {
      this.rolePermissions = targetKeys
    },
    filterApiOption (inputValue, option) {
      return option.title.indexOf(inputValue) > -1
    },
    getMenuIds (val) {
      this.form.menuIds = val
    }
  }
}
</script>
<style lang="less" scoped>
.menu-select {
  height: 300px;
  overflow-y: auto;
  border: 1px solid #e8e8e8;
}
.api-select {
  .ant-transfer {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
