<template>
  <c-modal
    title="角色修改"
    centered
    :with-tabs="true"
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="roleForm"
      :colon="false"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-tabs default-active-key="basic" tab-position="left">
        <a-tab-pane key="basic" tab="基本信息">
          <a-form-model-item label="角色名称" prop="name">
            <a-input v-model="form.name" placeholder="请输入角色名称" :maxLength=8 />
          </a-form-model-item>
          <a-form-model-item label="角色别名" prop="keyCode">
            <a-input v-model="form.keyCode" placeholder="请输入角色别名" :maxLength=16 />
          </a-form-model-item>
          <a-form-model-item label="超级管理员?">
            <a-switch v-model="form.isSupervisor" />
          </a-form-model-item>
        </a-tab-pane>
        <a-tab-pane key="menu" tab="菜单权限">
          <a-form-model-item label="菜单权限">
            <div class="menu-select">
              <multi-level-menu
                :initialData="menuData"
                :initialChecked="roleMenus"
                v-on:send-out="getMenuIds"
              >
              </multi-level-menu>
            </div>
          </a-form-model-item>
        </a-tab-pane>
        <a-tab-pane key="api" tab="接口权限">
          <a-form-model-item
            :wrapperCol="wrapperCol"
          >
            <div class="api-select">
              <a-transfer
                show-search
                :data-source="apiData"
                :filter-option="filterOption"
                :target-keys="rolePermissions"
                :render="item => item.title"
                :list-style="{
                  width: '180px',
                  height: '300px'
                }"
                @change="onTransferChange"
              />
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
import * as SysRoleService from '@/service/system/SysRoleService'

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
        menuIds: undefined,
        permissionUrls: undefined
      },
      rules: {},
      menuData: [],
      apiData: [],
      rolePermissions: [],
      roleMenus: [],
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
    async edit (record) {
      this.visible = true
      this.loading = true
      this.form = record
      try {
        const recSysRole = await SysRoleService.get(record.keyCode, this.axiosConfig)
        this.roleMenus = recSysRole.menus.map(item => item.id)
        this.rolePermissions = recSysRole.permissions.map(item => item.url)
        const menuResult = await SysMenuService.all(this.axiosConfig)
        this.menuData = [ ...menuResult ]
        const apiResult = await SysApiService.all(this.axiosConfig)
        for (let i = 0; i < apiResult.length; i++) {
          const transferItem = {
            key: apiResult[i],
            title: apiResult[i]
          }
          this.apiData.push(transferItem)
        }
      } catch (e) {
      }
      this.loading = false
      this.rules = OperatorRoleRule.OperatorRoleRuleBuilder.build(this.form)
    },
    handleSubmit () {
      this.$refs.roleForm.validate(valid => {
        if (valid) {
          this.confirmLoading = true
          OperatorRoleService.update(this.form.id, this.form).then(res => {
            this.roleMenus = []
            this.rolePermissions = []
            this.confirmLoading = false
            this.$refs.roleForm.resetFields()
            this.visible = false
            this.handleClose()
            this.$emit('ok')
          })
        } else {
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
    onTransferChange (targetKeys, direction, moveKeys) {
      this.rolePermissions = targetKeys
    },
    filterOption (inputValue, option) {
      return option.title.indexOf(inputValue) > -1
    },
    getMenuIds (val) {
      this.form.menuIds = val
    }
  }
}
</script>
<style scoped>
.menu-select {
  max-height: 500px;
  overflow-y: auto;
  border: 1px solid #e8e8e8;
}
</style>
