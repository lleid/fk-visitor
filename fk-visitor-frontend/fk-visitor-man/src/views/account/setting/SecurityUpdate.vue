<template>
  <a-form-model ref="accountSecurityUpdate" :colon="false" :model="form" :rules="rules" hideRequiredMark label-align="left" :label-col="labelCol" :wrapper-col="wrapperCol" :validate-messages="validateMessages">
    <a-form-model-item label="原密码" prop="oldPassword">
      <a-input-password v-model="form.oldPassword" :max-length="32" type="password" allowClear visibilityToggle />
    </a-form-model-item>
    <a-form-model-item label="新密码" prop="newPassword">
      <a-input-password v-model="form.newPassword" :max-length="32" type="password" allowClear visibilityToggle />
    </a-form-model-item>
    <a-form-model-item label="确认密码" prop="repeatPassword">
      <a-input-password v-model="form.repeatPassword" :max-length="32" type="password" allowClear visibilityToggle />
    </a-form-model-item>
    <a-form-model-item :wrapper-col="{ sm: { offset: 0 }, md: { offset: 4 } }" class="setting-submit">
      <a-button type="primary" :loading="confirmLoading" @click="handleSubmit">更新账号密码</a-button>
    </a-form-model-item>
  </a-form-model>
</template>

<script>
import FormConfig from '@/config/form.config'

import SecurityUpdateRuleBuiler from './SecurityUpdateRule'

import * as PersonalService from '@/service/system/PersonalService'

export default {
  data () {
    return {
      labelCol: {
        sm: { span: 24 },
        md: { span: 4 }
      },
      wrapperCol: {
        sm: { span: 24 },
        md: { span: 20 }
      },
      validateMessages: FormConfig.validateMessages,
      form: {
        oldPassword: undefined,
        newPassword: undefined,
        repeatPassword: undefined
      },
      rules: undefined,
      confirmLoading: false
    }
  },
  created () {
    this.rules = SecurityUpdateRuleBuiler.build(this.form)
  },
  methods: {
    handleSubmit () {
      this.$refs['accountSecurityUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          try {
            await PersonalService.updateMyPassword(this.form, { showLoading: false })
            this.$refs['accountSecurityUpdate'].resetFields()
          } catch (_) {
          }
          this.confirmLoading = false
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
</style>
