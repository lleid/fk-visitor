<template>
  <a-modal
    title="添加机型"
    :destroyOnClose="true"
    :maskClosable="false"
    width="40%"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-form-model
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
      ref="ruleForm"
      :model="form"
      :rules="rules">
      <a-form-model-item label="名称" prop="name" >
        <a-input v-model="form.name" />
      </a-form-model-item>
      <a-form-model-item label="vCPU数量" prop="vCPUs" >
        <a-radio-group v-model="form.vCPUs">
          <a-radio :value="1">1</a-radio>
          <a-radio :value="2">2</a-radio>
          <a-radio :value="4">4</a-radio>
          <a-radio :value="8">8</a-radio>
        </a-radio-group>
      </a-form-model-item>
      <a-form-model-item label="内存大小(MB)" prop="ram" >
        <a-input-number :min="1" :max="16000" v-model="form.ram" />
      </a-form-model-item>
      <a-form-model-item label="硬盘大小(GB)" prop="size" >
        <a-input-number :min="1" :max="100" v-model="form.size" />
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
import * as TemplateGeneralRules from './TemplateGeneralRules'
import * as TemplateService from '@/service/vm/TemplateService'
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      visible: false,
      confirmLoading: false,
      form: {
        name: '',
        vCPUs: 1,
        ram: 64,
        size: 1
      },
      rules: {}
    }
  },
  computed: {},
  async created () {
  },
  methods: {
    add () {
      this.rules = TemplateGeneralRules.build(this.form)
      this.visible = true
      this.$emit('stop-timer')
    },
    handleSubmit () {
      this.confirmLoading = true
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          TemplateService.create(this.form).then(res => {
            this.confirmLoading = false
            this.$refs.ruleForm.resetFields()
            this.visible = false
            this.$emit('ok')
            this.$emit('start-timer')
          })
        } else {
          this.confirmLoading = false
          return false
        }
      })
    },
    handleCancel () {
      this.$refs.ruleForm.resetFields()
      this.visible = false
      this.$emit('start-timer')
    }
  }
}
</script>
