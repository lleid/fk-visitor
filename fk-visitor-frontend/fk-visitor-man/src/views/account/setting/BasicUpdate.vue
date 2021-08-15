<template>
  <a-form-model ref="accountBasicUpdate" :colon="false" :model="form" :rules="rules" hideRequiredMark label-align="left" :label-col="labelCol" :wrapper-col="wrapperCol" :validate-messages="validateMessages">
    <a-form-model-item label="账号" prop="username">{{ form.username }}</a-form-model-item>
    <a-form-model-item label="姓名" prop="name">
      <a-input v-model="form.name" :max-length="32" />
    </a-form-model-item>
    <a-form-model-item label="手机" prop="mobile">
      <a-input v-model="form.mobile" :max-length="11" />
    </a-form-model-item>
    <a-form-model-item label="邮箱" prop="email">
      <a-input v-model="form.email" :max-length="64" />
    </a-form-model-item>
    <a-divider />
    <a-form-model-item :wrapper-col="{ sm: { offset: 0 }, md: { offset: 4 } }" class="setting-submit">
      <a-button type="primary" :loading="confirmLoading" @click="handleSubmit">更新基本信息</a-button>
    </a-form-model-item>
  </a-form-model>
</template>

<script>
import { mapActions } from 'vuex'

import moment from 'moment'
import omit from 'lodash.omit'

import FormConfig from '@/config/form.config'
import * as PersonalService from '@/service/system/PersonalService'

import BasicUpdateRule from './BasicUpdateRule'

export default {
  data () {
    const operator = omit({ ...this.$store.state.operator.operator }, ['id', 'gender', 'avatar', 'roles', 'lastLoginAt', 'lastLoginAtByMs'])
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
      form: operator,
      birthday: undefined,
      gender: undefined,
      rules: BasicUpdateRule,
      confirmLoading: false
    }
  },
  watch: {
    birthday (val) {
      if (val !== undefined && val !== null) {
        this.form.birthday = val.format('YYYY-MM-DD')
      } else {
        this.form.birthday = undefined
      }
    }
  },
  created () {
    if (this.form.birthday !== undefined) {
      this.birthday = moment(this.form.birthday)
    }
  },
  methods: {
    ...mapActions(['reloadOperator']),
    handleSubmit () {
      this.$refs['accountBasicUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          await PersonalService.updateMyInfo(omit(this.form, ['username']), { showLoading: false })
          await this.reloadOperator()
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
