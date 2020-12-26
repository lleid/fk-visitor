<template>
  <c-modal
    title="编辑协议"
    centered
    size="fullscreen"
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="protocolUpdate"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="类型" prop="type">
        <a-select mode="single" allowClear v-model="form.type" placeholder="请选择">
          <a-select-option value="CN">CN</a-select-option>
          <a-select-option value="EN">EN</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="描述" prop="description">
        <wang-editor @change="form.description=$event" :description="form.description"></wang-editor>
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import WangEditor from '@/components/WangEditor'

import ProtocolRuleBuilder from './ProtocolRule'

import * as ProtocolService from '@/service/system/ProtocolService'

export default {
  components: {
    WangEditor
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
        const result = await ProtocolService.get(record.id, { showLoading: false })

        this.form = result
        this.rules = ProtocolRuleBuilder.build(this.form)
      } catch (error) {

      }
      this.loading = false
    },
    handleSubmit () {
      this.$refs['protocolUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          try {
            const param = {}
            param.name = this.form.name
            param.type = this.form.type
            param.description = this.form.description

            await ProtocolService.update(this.form.id, param)
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
      if (this.$refs['protocolUpdate'] !== undefined) {
        this.$refs['protocolUpdate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
