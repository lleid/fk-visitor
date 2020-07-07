<template>
  <c-modal
    title="编辑用户"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
      ref="operatorUpdate"
      :model="form"
      :rules="rules"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="用户名" prop="username">
        <a-input v-model="form.username" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="用户角色" prop="roles">
        <a-select mode="multiple" allowClear v-model="form.roles" placeholder="请选择">
          <a-select-option v-for="role in roles" :key="role.id" :value="role.id">{{ role.name }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="用户组" prop="operatorGroupId">
        <a-tree-select
          v-model="form.operatorGroupId"
          :treeData="operatorGroupData"
          :allowClear="true"
          :defaultValue="operatorGroupIdDefault"
          placeholder="请选择"
          treeDefaultExpandAll
        ></a-tree-select>
      </a-form-model-item>
      <a-form-model-item label="姓名" prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="手机号" prop="mobile">
        <a-input v-model="form.mobile" :max-length="11" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="密码" prop="password">
        <a-input v-model="form.password" :max-length="16" type="password" placeholder="若无修改可不填" />
      </a-form-model-item>
      <a-form-model-item label="确认密码" prop="plainPassword">
        <a-input
          v-model="form.plainPassword"
          :max-length="16"
          type="password"
          placeholder="若无修改可不填"
        />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import OperatorRuleBuiler from './OperatorRule'

import * as OperatorService from '@/service/system/OperatorService'
import * as OperatorRoleService from '@/service/system/OperatorRoleService'
import * as OperatorGroupService from '@/service/system/OperatorGroupService'

export default {
  data () {
    return {
      ...FormConfig,
      labelCol: FormConfig.labelCol,
      wrapperCol: FormConfig.wrapperCol,
      validateMessage: FormConfig.validateMessages,
      visible: false,
      loading: false,
      confirmLoading: false,
      form: {
        username: '',
        name: '',
        password: '',
        plainPassword: '',
        mobile: '',
        roles: [],
        operatorGroupId: undefined,
        operatorGroup: {}
      },
      rules: {},
      roles: [],
      operatorGroupData: [],
      operatorGroupIdDefault: undefined
    }
  },
  async created () {},
  watch: {
    'form.operatorGroupId' (val) {
      if (val !== undefined) {
        this.form.operatorGroup = {}
        this.form.operatorGroup.id = val
      }
    }
  },
  methods: {
    async edit (record) {
      this.visible = true
      this.loading = true
      try {
        await OperatorRoleService.all({
          showLoading: false
        }).then(res => {
          this.roles = res
        })
        const operator = await OperatorService.get(record.id, { showLoading: false })
        const roleIds = []
        const len = operator.roles.length
        for (let i = 0; i < len; i++) {
          roleIds.push(operator.roles[i].id)
        }
        operator.roles = roleIds

        this.form = operator
        if (this.form.operatorGroup) {
          this.operatorGroupIdDefault = this.form.operatorGroup.id
        }

        this.rules = OperatorRuleBuiler.build(this.form)

        const operatorGroups = await OperatorGroupService.groups({
          showLoading: false
        })
        this.operatorGroupData = this.genTree(operatorGroups)
      } catch (error) {}
      this.loading = false
    },
    genTree (data) {
      return data.map(item => {
        const operatorGroupData = {
          value: item.id,
          key: item.id,
          title: item.name
        }
        if (item.subGroups && item.subGroups.length > 0) {
          operatorGroupData.children = this.genTree(item.subGroups)
        }
        return operatorGroupData
      })
    },
    handleSubmit () {
      this.$refs['operatorUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          const requestModel = {
            username: this.form.username,
            name: this.form.name,
            password: this.form.password,
            mobile: this.form.mobile,
            roles: this.form.roles,
            operatorGroup: this.form.operatorGroup
          }
          await OperatorService.patch(this.form.id, requestModel, { showLoading: false })
          this.confirmLoading = false
          this.handleClose()
          this.$emit('ok')
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs['operatorUpdate'] !== undefined) {
        this.$refs['operatorUpdate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
