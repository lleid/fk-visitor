<template>
  <c-modal
    title="添加员工"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="employeeCreate"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="姓名" prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="部门" prop="department">
        <a-input v-model="form.department" :max-length="16" placeholder="请输入" />
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
      loading: false,
      visible: false,
      confirmLoading: false,
      form: {
        name: '',
        department: ''
      },
      rules: EmployeeRuleBuilder.build()
    }
  },
  methods: {
    async add () {
      this.visible = true
    },
    handleSubmit () {
      this.$refs['employeeCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          try {
            await EmployeeService.create(this.form)
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
      if (this.$refs['employeeCreate'] !== undefined) {
        this.$refs['employeeCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
