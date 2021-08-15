<template>
  <c-modal title="添加签出原因" centered :destroyOnClose="true" :visible="visible" :loading="loading" :confirmLoading="confirmLoading" @ok="handleSubmit" @cancel="handleClose">
    <a-form-model ref="signOutReasonCreate" :model="form" :rules="rules" :labelCol="labelCol" :wrapperCol="wrapperCol" :validate-messages="validateMessages">
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name" :max-length="64" placeholder="请输入" />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import SignOutReasonRuleBuilder from './SignOutReasonRule'

import * as SignOutReasonService from '@/service/mdata/SignOutReasonService'

export default {
  components: {
  },
  data () {
    return {
      ...FormConfig,
      labelCol: FormConfig.labelCol,
      wrapperCol: FormConfig.wrapperCol,
      validateMessage: FormConfig.validateMessages,
      loading: false,
      visible: false,
      confirmLoading: false,
      form: {
        type: '',
        description: ''
      },
      rules: SignOutReasonRuleBuilder.build()
    }
  },
  methods: {
    async add () {
      this.visible = true
    },
    handleSubmit () {
      this.$refs['signOutReasonCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          try {
            await SignOutReasonService.create(this.form)
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
      if (this.$refs['signOutReasonCreate'] !== undefined) {
        this.$refs['signOutReasonCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
