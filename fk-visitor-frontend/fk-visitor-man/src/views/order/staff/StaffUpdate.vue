<template>
  <c-modal title="编辑成员" centered :destroyOnClose="true" :visible="visible" :loading="loading" :confirmLoading="confirmLoading" @ok="handleSubmit" @cancel="handleClose">
    <a-form-model ref="staffUpdate" :model="form" :rules="rules" :labelCol="labelCol" :wrapperCol="wrapperCol" :validate-messages="validateMessages">
      <a-form-model-item label="姓名" prop="name">
        <a-input v-model="form.name" :max-length="32" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="职务" prop="title">
        <a-input v-model="form.title" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="联系方式" prop="mobile">
        <a-input v-model="form.mobile" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="证件号" prop="idCard">
        <a-input v-model="form.idCard" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="接收验证码" prop="isMessage">
        <a-select mode="single" allowClear v-model="form.isMessage">
          <a-select-option value="true"> 是 </a-select-option>
          <a-select-option value="false"> 否 </a-select-option>
        </a-select>
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'

import StaffRuleBuilder from './StaffRule'

import * as AppointmentService from '@/service/order/AppointmentService'

export default {
  components: {
  },
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
        name: '',
        idCard: '',
        email: '',
        mobile: '',
        title: '',
        isMessage: 'true'
      },
      rules: {}
    }
  },
  created () {
  },
  methods: {
    async edit (record) {
      this.visible = true
      this.loading = true
      try {
        const result = await AppointmentService.get(record.id, { showLoading: false })
        this.form = result
        this.form.isMessage = this.form.isMessage + ''
        this.rules = StaffRuleBuilder.build(this.form)
      } catch (error) {

      }
      this.loading = false
    },
    handleSubmit () {
      this.$refs['staffUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          try {
            const requestModel = {
              name: this.form.name,
              mobile: this.form.mobile,
              idCard: this.form.idCard,
              company: this.form.company,
              title: this.form.title,
              email: this.form.email,
              isMessage: this.form.isMessage
            }

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
      if (this.$refs['staffUpdate'] !== undefined) {
        this.$refs['staffUpdate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
