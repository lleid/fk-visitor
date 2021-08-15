<template>
  <c-modal title="添加参观区域" centered :destroyOnClose="true" :visible="visible" :loading="loading" :confirmLoading="confirmLoading" @ok="handleSubmit" @cancel="handleClose">
    <a-form-model ref="visitAreaCreate" :model="form" :rules="rules" :labelCol="labelCol" :wrapperCol="wrapperCol" :validate-messages="validateMessages">
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
import VisitAreaRuleBuilder from './VisitAreaRule'

import * as VisitAreaService from '@/service/mdata/VisitAreaService'

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
      rules: VisitAreaRuleBuilder.build()
    }
  },
  methods: {
    async add () {
      this.visible = true
    },
    handleSubmit () {
      this.$refs['visitAreaCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          try {
            await VisitAreaService.create(this.form)
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
      if (this.$refs['visitAreaCreate'] !== undefined) {
        this.$refs['visitAreaCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
