<template>
  <c-modal
    title="添加事由"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="purposeCreate"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="中文" prop="cnName">
        <a-input v-model="form.cnName" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="英文" prop="enName">
        <a-input v-model="form.enName" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import PurposeRuleBuilder from './PurposeRule'

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
      form: {
        cnName: '',
        enName: ''
      },
      rules: PurposeRuleBuilder.build()
    }
  },
  methods: {
    async add () {
      this.visible = true
    },
    handleSubmit () {
      this.$refs['purposeCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          await PurposeService.create(this.form)
          this.confirmLoading = false
          this.handleClose()
          this.$emit('ok')
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs['purposeCreate'] !== undefined) {
        this.$refs['purposeCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
