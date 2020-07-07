<template>
  <a-modal
    title="扩容"
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

      <a-form-model-item label="设定新容量" prop="form">
        <a-input-number :min="form.blockStorageUnit.size" :max="20" v-model="form.newSize"/>
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
      instances: [],
      form: {
        size: 0,
        blockStorageUnit: { id: '', size: 0 }
      },
      rules: {}
    }
  },
  computed: {},
  async created () {
  },
  methods: {
    extend (record) {
      this.form = { ...record }
      this.rules = StorageUnitGeneralRules.build(this.form)
      this.visible = true
      this.$emit('stop-timer')
    },
    handleSubmit () {
      this.confirmLoading = true
      this.$refs.ruleForm.validate(async valid => {
        if (valid) {
          // console.log(this.form)
          StorageUnitService.extend(this.form).then(res => {
            this.confirmLoading = false
            this.$refs.ruleForm.resetFields()
            this.visible = false
            this.$emit('ok')
            this.$emit('start-timer')
          }).catch(err => {
            this.confirmLoading = false
            console.log(err)
          })
        } else {
          this.confirmLoading = false
          return false
        }
      })
    },
    handleCancel () {
      this.form.instances = undefined
      this.$refs.ruleForm.resetFields()
      this.visible = false
      this.$emit('start-timer')
    }
  }
}
</script>
