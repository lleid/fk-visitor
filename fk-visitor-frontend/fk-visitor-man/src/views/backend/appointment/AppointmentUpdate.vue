<template>
  <c-modal
    title="编辑预约"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="appointmentUpdate"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="姓名" prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="手机号" prop="mobile">
        <a-input v-model="form.mobile" :max-length="11" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="预约时间" prop="orderAt">
        <a-date-picker v-model="orderAt" />
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
        <a-select mode="single" allowClear v-model="purposeId" placeholder="请选择">
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
import moment from 'moment'

import AppointmentRuleBuilder from './AppointmentRule'
import * as AppointmentService from '@/service/data/AppointmentService'
import * as PurposeService from '@/service/system/PurposeService'
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
      purposes: [],
      orderAt: undefined,
      purposeId: undefined,
      form: {
        name: '',
        mobile: '',
        company: '',
        job: '',
        purpose: {},
        idCard: '',
        orderAt: undefined
      },
      rules: {}
    }
  },
  created () {
  },
  watch: {
    'purposeId' (val) {
      if (val !== undefined) {
        this.form.purpose.id = val
      }
    },
    'orderAt' (val) {
      if (val !== undefined && typeof val === 'object') {
        this.form.orderAt = val.format('YYYY-MM-DD')
      }
    }
  },
  methods: {
    moment,
    async edit (record) {
      this.visible = true
      this.loading = true
      try {
        const purposes = await PurposeService.queryAll({
          showLoading: false
        })
        this.purposes = purposes

        const result = await AppointmentService.get(record.id, { showLoading: false })
        if (result.purpose) {
          this.purposeId = result.purpose.id
        }
        if (result.orderAt) {
          this.orderAt = moment(result.orderAt).format('YYYY-MM-DD')
        }
        this.form = result
        this.rules = AppointmentRuleBuilder.build(this.form)
      } catch (error) {
      }
      this.loading = false
    },
    handleSubmit () {
      this.$refs['appointmentUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          const requestModel = {
            name: this.form.name,
            orderAt: this.form.orderAt,
            mobile: this.form.mobile,
            idCard: this.form.idCard,
            company: this.form.company,
            job: this.form.job,
            purpose: this.form.purpose
          }
          await AppointmentService.update(this.form.id, requestModel)
          this.confirmLoading = false
          this.handleClose()
          this.$emit('ok')
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs['appointmentUpdate'] !== undefined) {
        this.$refs['appointmentUpdate'].resetFields()
      }
      this.visible = false
    },
    onChange (date, dateString) {
      console.log(date, dateString)
    }
  }
}
</script>
