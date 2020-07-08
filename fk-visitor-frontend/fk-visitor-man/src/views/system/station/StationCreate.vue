<template>
  <c-modal
    title="添加站点"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="stationCreate"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="站点名称" prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="站点地址" prop="address">
        <a-input v-model="form.address" :max-length="64" placeholder="请输入" />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import StationRuleBuilder from './StationRule'

import * as StationService from '@/service/system/StationService'

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
        name: '',
        address: ''
      },
      rules: StationRuleBuilder.build()
    }
  },
  methods: {
    async add () {
      this.visible = true
    },
    handleSubmit () {
      this.$refs['stationCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          await StationService.create(this.form)
          this.confirmLoading = false
          this.handleClose()
          this.$emit('ok')
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs['stationCreate'] !== undefined) {
        this.$refs['stationCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
