<template>
  <div id="login-panel">
    <a-form-model
      ref="loginForm"
      :model="loginData"
      :rules="loginRules"
      :colon="false"
      hideRequiredMark
      :validate-messages="validateMessages"
      @submit="handleSubmit"
      @submit.native.prevent
    >
      <a-form-model-item prop="username">
        <a-input v-model="loginData.username" placeholder="账号/手机/邮箱" size="large" allowClear>
          <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25); fontSize: 18px" />
        </a-input>
      </a-form-model-item>
      <a-form-model-item prop="password">
        <a-input-password
          v-model="loginData.password"
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
        <a-button size="large" type="primary" htmlType="submit" class="login-button">确定</a-button>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

import FormConfig from '@/config/form.config'
import LoginRules from './LoginRule'

export default {
  data () {
    return {
      validateMessages: FormConfig.validateMessages,
      loginData: {
        username: '',
        password: ''
      },
      loginRules: LoginRules
    }
  },
  created () {
  },
  methods: {
    ...mapActions(['login']),
    // handler
    handleSubmit (e) {
      this.$refs.loginForm.validate(async valid => {
        if (valid) {
          try {
            await this.login(this.loginData)
            this.$router.push({ path: '/' })
          } catch (e) {
          }
        }
        return valid
      })
      // const {
      //   // form: { validateFields },
      //   state
      //   // Login
      // } = this

      // state.loginBtn = true

      // const validateFieldsKey = customActiveKey === 'tab1' ? ['username', 'password'] : ['mobile', 'captcha']

      // validateFields(validateFieldsKey, { force: true }, (err, values) => {
      //   if (!err) {
      //     console.log('login form', values)
      //     const loginParams = { ...values }
      //     delete loginParams.username
      //     loginParams[!state.loginType ? 'email' : 'username'] = values.username
      //     Login(loginParams)
      //       .then((res) => {
      //         this.loginSuccess(res)
      //       })
      //       .catch(err => this.requestFailed(err))
      //       .finally(() => {
      //         state.loginBtn = false
      //       })
      //   } else {
      //     setTimeout(() => {
      //       state.loginBtn = false
      //     }, 600)
      //   }
      // })
    },
    loginSuccess (res) {
      this.$router.push({ path: '/' })
      // // 延迟 1 秒显示欢迎信息
      // setTimeout(() => {
      //   this.$notification.success({
      //     message: '欢迎',
      //     description: `${timeFix()}，欢迎回来`
      //   })
      // }, 1000)
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
#login-panel {
  min-width: 260px;
  width: 368px;
  margin: 0 auto;
}

button.login-button {
  padding: 0 15px;
  font-size: 16px;
  height: 40px;
  width: 100%;
}
</style>
