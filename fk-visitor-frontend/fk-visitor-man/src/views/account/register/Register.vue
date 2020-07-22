<template>
  <div id="register-panel">
    <a-form-model
      ref="registerForm"
      :model="registerData"
      :rules="registerRules"
      :colon="false"
      hideRequiredMark
      :validate-messages="validateMessages"
      @submit="handleSubmit"
      @submit.native.prevent
    >
      <a-form-model-item prop="username">
        <a-input v-model="registerData.username" placeholder="账号" size="large" allowClear>
          <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25); fontSize: 18px" />
        </a-input>
      </a-form-model-item>
      <a-form-model-item prop="password">
        <a-input-password
          v-model="registerData.password"
          type="password"
          placeholder="密码"
          size="large"
          allowClear
          visibilityToggle
        >
          <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
        </a-input-password>
      </a-form-model-item>
      <a-form-model-item style="padding-top: 10px;">
        <a-button size="large" type="primary" htmlType="submit" class="register-button">确定</a-button>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>

<script>
import * as OperatorService from '@/service/system/OperatorService'

import FormConfig from '@/config/form.config'
import RegisterRules from './RegisterRule'

export default {
  data () {
    return {
      validateMessages: FormConfig.validateMessages,
      registerData: {
        username: '',
        password: ''
      },
      registerRules: RegisterRules
    }
  },
  created () {
  },
  methods: {
    handleSubmit (e) {
      this.$refs.registerForm.validate(async valid => {
        if (valid) {
          try {
            await OperatorService.register(this.registerData)
            this.$router.push({ path: '/account/login' })
          } catch (e) {
          }
        }
        return valid
      })
    },
    registerSuccess (res) {
      this.$router.push({ path: '/' })
    },
    requestFailed (err) {
      this.$notification['error']({
        message: '错误',
        description: ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
    }
  }
}
</script>

<style lang="less" scoped>
#register-panel {
  min-width: 260px;
  width: 368px;
  margin: 0 auto;
}

button.register-button {
  padding: 0 15px;
  font-size: 16px;
  height: 40px;
  width: 100%;
}
</style>
