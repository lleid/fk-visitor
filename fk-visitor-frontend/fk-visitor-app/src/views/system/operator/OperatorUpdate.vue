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
import * as StationService from '@/service/system/StationService'

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
        stationId: undefined
      },
      rules: {},
      roles: [],
      stations: []
    }
  },
  async created () { },
  watch: {
    'form.stationId' (val) {
      if (val !== undefined) {
        this.form.station = {}
        this.form.station.id = val
      }
    }
  },
  methods: {
    async edit (record) {
      this.visible = true
      this.loading = true
      try {
        const operatorRoles = await OperatorRoleService.queryAll({
          showLoading: false
        })
        const stations = await StationService.queryAll({
          showLoading: false
        })
        this.roles = operatorRoles

        this.stations = stations

        const operator = await OperatorService.get(record.id, { showLoading: false })
        const roleIds = []
        const len = operator.roles.length
        for (let i = 0; i < len; i++) {
          roleIds.push(operator.roles[i].id)
        }
        operator.roles = roleIds

        if (operator.station) {
          const sId = operator.station.id
          operator['stationId'] = sId
        }

        this.form = operator
        this.rules = OperatorRuleBuiler.build(this.form)
      } catch (error) { }
      this.loading = false
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
            station: this.form.station
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
