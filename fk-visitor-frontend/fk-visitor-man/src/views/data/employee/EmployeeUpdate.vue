<template>
  <c-modal
    title="编辑员工"
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
      ref="employeeUpdate"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="姓名" prop="name">
        <a-input v-model="form.name" :max-length="32" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="部门" prop="department">
        <a-input v-model="form.name" :max-length="32" placeholder="请输入" />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'

import EmployeeRuleBuilder from './EmployeeRule'

import * as EmployeeService from '@/service/system/EmployeeService'

export default {
  components: {
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
        name: '',
        department: ''
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
        const result = await EmployeeService.get(record.id, { showLoading: false })

        this.form = result
        this.rules = EmployeeRuleBuilder.build(this.form)
      } catch (error) {

      }
      this.loading = false
    },
    handleSubmit () {
      this.$refs['employeeUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          try {
            await EmployeeService.update(this.form.id, this.form)
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
      if (this.$refs['employeeUpdate'] !== undefined) {
        this.$refs['employeeUpdate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
