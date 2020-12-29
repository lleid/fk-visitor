<template>
  <c-modal
    title="编辑事由"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="purposeUpdate"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="中文" prop="cnName">
        <a-input v-model="form.cnName" :max-length="64" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="英文" prop="enName">
        <a-input v-model="form.enName" :max-length="64" placeholder="请输入" />
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
      visible: false,
      loading: false,
      confirmLoading: false,
      form: {
        cnName: '',
        enName: ''
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
        const result = await PurposeService.get(record.id, { showLoading: false })

        this.form = result
        this.rules = PurposeRuleBuilder.build(this.form)
      } catch (error) {

      }
      this.loading = false
    },
    handleSubmit () {
      this.$refs['purposeUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          try {
            const param = {}
            param.cnName = this.form.cnName
            param.enName = this.form.enName

            await PurposeService.update(this.form.id, param)
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
      if (this.$refs['purposeUpdate'] !== undefined) {
        this.$refs['purposeUpdate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
