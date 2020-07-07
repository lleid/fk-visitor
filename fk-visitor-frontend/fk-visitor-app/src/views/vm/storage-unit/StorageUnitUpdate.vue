<template>
  <a-modal
    title="云存储修改"
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
      :rules="rules"
    >
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name" />
      </a-form-model-item>
      <a-form-model-item label="描述" prop="description">
        <a-input v-model="form.description" />
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
import * as StorageUnitService from '@/service/vm/StorageUnitService'
import * as StorageUnitGeneralRules from './StorageUnitGeneralRules'

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
        bootable: false
      },
      rules: {}
    }
  },
  computed: {},
  async created () {
    await StorageUnitService.queryAll({
      showLoading: false,
      showSuccess: false,
      showError: false
    })
  },
  methods: {
    update (record) {
      this.form = { ...record }
      this.rules = StorageUnitGeneralRules.build(this.form)
      this.visible = true
      this.$emit('stop-timer')
    },
    handleSubmit () {
      this.$refs.ruleForm.validate(async valid => {
        this.confirmLoading = true
        if (valid) {
          try {
            await StorageUnitService.update(this.form.id, this.form)
            this.$refs.ruleForm.resetFields()
            this.visible = false
            this.$emit('ok')
            this.$emit('start-timer')
          } catch (error) {}
        } else {
          return false
        }
        this.confirmLoading = false
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
