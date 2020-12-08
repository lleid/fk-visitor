<template>
  <c-modal
    title="签出"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="signOut"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="签出原因" prop="reasonId">
        <a-select mode="single" allowClear v-model="form.reasonId" placeholder="请选择">
          <a-select-option
            v-for="reason in reasons"
            :key="reason.id"
            :value="reason.id"
          >{{ reason.name }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="备注" prop="remark">
        <a-input v-model="form.remark" :max-length="64" placeholder="请输入" />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import OrderSignOutRuleBuilder from './OrderSignOutRule'

import * as OrderService from '@/service/system/OrderService'
import * as SignOutReasonService from '@/service/system/SignOutReasonService'

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
      reasons: [],
      id: null,
      form: {
        remark: ''
      },
      rules: OrderSignOutRuleBuilder.build()
    }
  },
  methods: {
    async edit (record) {
      this.visible = true
      this.id = record.id

      const reasons = await SignOutReasonService.queryAll({
        showLoading: false
      })
      this.reasons = reasons
    },
    handleSubmit () {
      this.$refs['signOut'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          try {
            await OrderService.singOut(this.form)
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
      if (this.$refs['signOut'] !== undefined) {
        this.$refs['signOut'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
