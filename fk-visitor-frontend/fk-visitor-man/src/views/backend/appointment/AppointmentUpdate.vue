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
      <a-form-model-item label="联系方式" prop="mobile">
        <a-input v-model="form.mobile" :max-length="11" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="证件号" prop="idCard">
        <a-input v-model="form.idCard" :max-length="18" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="预约时间" prop="orderAt">
        <a-date-picker v-model="form.orderAt" />
      </a-form-model-item>
      <a-form-model-item label="公司" prop="company">
        <a-input v-model="form.company" :max-length="16" placeholder="请确认" />
      </a-form-model-item>
      <a-form-model-item label="职务" prop="title">
        <a-input v-model="form.title" :max-length="16" placeholder="请确认" />
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
      <a-form-model-item label="参观区域" prop="visitAreaId">
        <a-select mode="single" allowClear v-model="form.visitAreaId" placeholder="请选择">
          <a-select-option
            v-for="area in visitAreas"
            :key="area.id"
            :value="area.id"
          >{{ area.cnName }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="受访人" prop="interviewer">
        <a-input v-model="form.interviewer" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="接收验证码" prop="isMessage">
        <a-select mode="single" allowClear v-model="form.isMessage">
          <a-select-option value="true">是</a-select-option>
          <a-select-option value="false">否</a-select-option>
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
import * as VisitAreaService from '@/service/system/VisitAreaService'

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
      visitAreas: [],
      form: {
        name: '',
        mobile: '',
        company: '',
        title: '',
        purpose: {},
        visitArea: {},
        idCard: '',
        orderAt: undefined,
        purposeId: undefined,
        visitAreaId: undefined,
        isMessage: 'true'
      },
      rules: {}
    }
  },
  methods: {
    moment,
    async edit (record) {
      this.visible = true
      this.loading = true
      try {
        const purposes = await PurposeService.query({ isDeleted: false }, {
          showLoading: false
        })
        this.purposes = purposes

        const visitAreas = await VisitAreaService.query({ isDeleted: false }, {
          showLoading: false
        })
        this.visitAreas = visitAreas

        const result = await AppointmentService.get(record.id, { showLoading: false })
        if (result.purpose) {
          result.purposeId = result.purpose.id
        }
        if (result.visitArea) {
          result.visitAreaId = result.visitArea.id
        }
        if (result.orderAt) {
          result.orderAt = moment(result.orderAt, 'YYYY-MM-DD')
        }
        this.form = result
        this.form.isMessage = this.form.isMessage + ''
        this.rules = AppointmentRuleBuilder.build(this.form)
      } catch (error) {
      }
      this.loading = false
    },
    handleSubmit () {
      this.$refs['appointmentUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          this.form.orderAt = this.form.orderAt.format('YYYY-MM-DD')

          const requestModel = {
            name: this.form.name,
            orderAt: this.form.orderAt,
            mobile: this.form.mobile,
            idCard: this.form.idCard,
            company: this.form.company,
            title: this.form.title,
            purpose: {
              id: this.form.purposeId
            },
            visitArea: {
              id: this.form.visitAreaId
            },
            isMessage: this.form.isMessage
          }

          try {
            await AppointmentService.update(this.form.id, requestModel)
            this.handleClose()
            this.$emit('ok')
          } catch (e) { }
          this.confirmLoading = false
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
