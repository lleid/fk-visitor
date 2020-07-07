<template>
  <a-modal
    title="挂载云盘"
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

      <a-form-model-item label="挂载到" prop="instances">
        <a-select v-model="form.vmInstance.id">
          <a-select-option v-for="instance in instances" :key="instance.id" :value="instance.id">
            {{ instance.name }}
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="挂载点" prop="mountPoint">
        <a-input v-model="form.mountPoint"/>
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
import * as StorageUnitGeneralRules from './StorageUnitGeneralRules'
import * as StorageUnitService from '@/service/vm/StorageUnitService'
import * as InstanceService from '@/service/vm/InstanceService'
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
        vmInstance: {},
        mountPoint: '',
        blockStorageUnit: {}
      },
      rules: {}
    }
  },
  computed: {},
  async created () {
    await InstanceService.queryAll({
      showLoading: false,
      showSuccess: false,
      showError: false
    }).then(res => {
      this.instances = res
    })
  },
  methods: {
    attach (record) {
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
          StorageUnitService.attach(this.form).then(res => {
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
