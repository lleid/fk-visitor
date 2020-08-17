<template>
  <c-modal
    title="添加预约"
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
      ref="appointmentCreate"
      :model="form"
      :rules="rules"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="姓名" prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="手机号" prop="mobile">
        <a-input v-model="form.mobile" :max-length="11" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="预约时间" prop="orderAt">
        <a-date-picker v-model="form.orderAt" />
      </a-form-model-item>
      <a-form-model-item label="证件号" prop="idCard">
        <a-input v-model="form.idCard" :max-length="18" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="公司" prop="company">
        <a-input v-model="form.company" :max-length="16" placeholder="请确认" />
      </a-form-model-item>
      <a-form-model-item label="职务" prop="job">
        <a-input v-model="form.job" :max-length="16" placeholder="请确认" />
      </a-form-model-item>
      <a-form-model-item label="拜访事由" prop="purposeId">
        <a-select mode="single" allowClear v-model="form.purposeId" placeholder="请选择">
          <a-select-option
            v-for="purpose in purposes"
            :key="purpose.id"
            :value="purpose.id"
          >{{ purpose.cnName }}</a-select-option>
        </a-select>
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import AppointmentRuleBuiler from './AppointmentRule'
import moment from 'moment'

import * as AppointmentService from '@/service/data/AppointmentService'
import * as PurposeService from '@/service/system/PurposeService'

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
      purposes: [],
      form: {
        name: '',
        mobile: '',
        company: '',
        job: '',
        purpose: {},
        idCard: '',
        purposeId: undefined,
        orderAt: undefined
      },
      rules: {}
    }
  },
  watch: {
    'form.purposeId' (val) {
      if (val !== undefined) {
        this.form.purpose.id = val
      }
    }
  },
  methods: {
    moment,
    async add () {
      this.loading = true
      this.visible = true

      const purposes = await PurposeService.queryAll({
        showLoading: false
      })
      this.purposes = purposes

      this.rules = AppointmentRuleBuiler.build(this.form)
      this.loading = false
    },
    handleSubmit () {
      this.$refs['appointmentCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          this.form.orderAt = this.form.orderAt.format('YYYY-MM-DD')

          await AppointmentService.create(this.form)
          this.confirmLoading = false
          this.handleClose()
          this.$emit('ok')
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs['appointmentCreate'] !== undefined) {
        this.$refs['appointmentCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
