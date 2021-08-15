<template>
  <div id="register-panel">
    <a-form-model ref="registerForm" :model="form" :rules="rules" hideRequiredMark :validate-messages="validateMessages" @submit="handleSubmit" @submit.native.prevent>
      <a-form-model-item prop="username">
        <a-input v-model="form.username" :max-length="32" placeholder="用户名">
          <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25); fontSize: 18px" />
        </a-input>
      </a-form-model-item>
      <a-form-model-item prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="姓名">
          <a-icon slot="prefix" type="profile" style="color:rgba(0,0,0,.25); fontSize: 18px" />
        </a-input>
      </a-form-model-item>
      <a-form-model-item prop="mobile">
        <a-input v-model="form.mobile" :max-length="11" placeholder="手机号">
          <a-icon slot="prefix" type="mobile" style="color:rgba(0,0,0,.25); fontSize: 18px" />
        </a-input>
      </a-form-model-item>
      <a-form-model-item prop="password">
        <a-input v-model="form.password" :max-length="16" type="password" placeholder="密码">
          <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25); fontSize: 18px" />
        </a-input>
      </a-form-model-item>
      <a-form-model-item prop="plainPassword">
        <a-input v-model="form.plainPassword" :max-length="16" type="password" placeholder="确认密码">
          <a-icon slot="prefix" type="audit" style="color:rgba(0,0,0,.25); fontSize: 18px" />
        </a-input>
      </a-form-model-item>
      <a-form-model-item style="padding-top: 10px;">
        <a-button size="large" type="primary" htmlType="submit" class="register-button">注册</a-button>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>

<script>
import * as OperatorService from '@/service/system/OperatorService'

import FormConfig from '@/config/form.config'
import OperatorRuleBuiler from './RegisterRule'

export default {
  data () {
    return {
      ...FormConfig,
      validateMessages: FormConfig.validateMessages,
      form: {
        username: '',
        name: '',
        password: '',
        plainPassword: '',
        mobile: ''
      },
      rules: {}
    }
  },
  created () {
    this.rules = OperatorRuleBuiler.build(this.form)
  },
  methods: {
    handleSubmit (e) {
      this.$refs.registerForm.validate(async valid => {
        if (valid) {
          try {
            await OperatorService.register(this.form)
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
