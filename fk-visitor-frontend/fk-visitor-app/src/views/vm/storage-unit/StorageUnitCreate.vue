<template>
  <a-modal
    title="添加云存储"
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
      <a-form-model-item label="描述" prop="description" >
        <a-input v-model="form.description" />
      </a-form-model-item>
      <a-form-model-item label="硬盘大小" prop="size" >
        <a-input-number :min="1" :max="20" v-model="form.size" />
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
import * as StorageUnitGeneralRules from './StorageUnitGeneralRules'
import * as StorageUnitService from '@/service/vm/StorageUnitService'
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
        description: '',
        bootable: false,
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
      this.rules = StorageUnitGeneralRules.build(this.form)
      this.visible = true
      this.$emit('stop-timer')
    },
    handleSubmit () {
      this.confirmLoading = true
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          StorageUnitService.create(this.form).then(res => {
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
