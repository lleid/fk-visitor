<template>
  <c-modal
    title="添加用户"
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
      ref="operatorCreate"
      :model="form"
      :rules="rules"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="用户名" prop="username">
        <a-input v-model="form.username" :max-length="32" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="用户角色" prop="roles">
        <a-select mode="multiple" allowClear v-model="form.roles" placeholder="请选择">
          <a-select-option v-for="role in roles" :key="role.id" :value="role.id">{{ role.name }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="站点" prop="stationId">
        <a-select mode="single" allowClear v-model="form.stationId" placeholder="请选择">
          <a-select-option
            v-for="station in stations"
            :key="station.id"
            :value="station.id"
          >{{ station.name }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="姓名" prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="手机号" prop="mobile">
        <a-input v-model="form.mobile" :max-length="11" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="密码" prop="password">
        <a-input v-model="form.password" :max-length="16" type="password" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="确认密码" prop="plainPassword">
        <a-input v-model="form.plainPassword" :max-length="16" type="password" placeholder="请确认" />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import OperatorRuleBuiler from './OperatorRule'

import * as OperatorService from '@/service/system/OperatorService'
import * as OperatorRoleService from '@/service/system/OperatorRoleService'
import * as StationService from '@/service/system/StationService'

export default {
  data () {
    return {
      ...FormConfig,
      labelCol: FormConfig.labelCol,
      wrapperCol: FormConfig.wrapperCol,
      validateMessage: FormConfig.validateMessages,
      loading: false,
      visible: false,
      confirmLoading: false,
      roles: [],
      stations: [],
      form: {
        username: '',
        name: '',
        password: '',
        plainPassword: '',
        mobile: '',
        roles: [],
        stationId: ''
      },
      rules: {}
    }
  },
  watch: {
    'form.stationId' (val) {
      if (val !== undefined) {
        this.form.station = {}
        this.form.station.id = val
      }
    }
  },
  methods: {
    async add () {
      this.loading = true
      this.visible = true
      try {
        const operatorRoles = await OperatorRoleService.queryAll({
          showLoading: false
        })
        const stations = await StationService.queryAll({
          showLoading: false
        })
        this.roles = operatorRoles
        this.stations = stations
      } catch (error) {

      }
      this.rules = OperatorRuleBuiler.build(this.form)
      this.loading = false
    },
    handleSubmit () {
      this.$refs['operatorCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          await OperatorService.create(this.form)
          this.confirmLoading = false
          this.handleClose()
          this.$emit('ok')
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs['operatorCreate'] !== undefined) {
        this.$refs['operatorCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
