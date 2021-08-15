<template>
  <c-modal title="添加协议" centered size="fullscreen" :destroyOnClose="true" :visible="visible" :loading="loading" :confirmLoading="confirmLoading" @ok="handleSubmit" @cancel="handleClose">
    <a-form-model ref="protocolCreate" :model="form" :rules="rules" :labelCol="labelCol" :wrapperCol="wrapperCol" :validate-messages="validateMessages">
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name" :max-length="64" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="类型" prop="type">
        <a-select mode="single" allowClear v-model="form.type" placeholder="请选择">
          <a-select-option value="CN">CN</a-select-option>
          <a-select-option value="EN">EN</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="描述" prop="description">
        <wang-editor @change="description=$event" :description="form.description"></wang-editor>
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import ProtocolRuleBuilder from './ProtocolRule'
import WangEditor from '@/components/WangEditor'

import * as ProtocolService from '@/service/mdata/ProtocolService'

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
      loading: false,
      visible: false,
      confirmLoading: false,
      form: {
        type: '',
        description: ''
      },
      rules: ProtocolRuleBuilder.build()
    }
  },
  methods: {
    async add () {
      this.visible = true
    },
    handleSubmit () {
      this.$refs['protocolCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          try {
            await ProtocolService.create(this.form)
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
      if (this.$refs['protocolCreate'] !== undefined) {
        this.$refs['protocolCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
