<template>
  <c-modal title="编辑签出原因" centered :destroyOnClose="true" :visible="visible" :loading="loading" :confirmLoading="confirmLoading" @ok="handleSubmit" @cancel="handleClose">
    <a-form-model ref="signOutReasonUpdate" :model="form" :rules="rules" :labelCol="labelCol" :wrapperCol="wrapperCol" :validate-messages="validateMessages">
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name" :max-length="64" placeholder="请输入" />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'

import SignOutReasonRuleBuilder from './SignOutReasonRule'

import * as SignOutReasonService from '@/service/system/SignOutReasonService'

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
        type: '',
        description: ''
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
        const result = await SignOutReasonService.get(record.id, { showLoading: false })

        this.form = result
        this.rules = SignOutReasonRuleBuilder.build(this.form)
      } catch (error) {

      }
      this.loading = false
    },
    handleSubmit () {
      this.$refs['signOutReasonUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          try {
            const param = {}
            param.name = this.form.name
            await SignOutReasonService.update(this.form.id, param)
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
      if (this.$refs['signOutReasonUpdate'] !== undefined) {
        this.$refs['signOutReasonUpdate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
