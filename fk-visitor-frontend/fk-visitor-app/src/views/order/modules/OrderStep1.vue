<template>
  <a-form-model
    :label-col="labelCol"
    :wrapper-col="wrapperCol"
    ref="orderCreate"
    :model="form"
    :rules="rules"
    :validate-messages="validateMessages"
  >
    <a-row>
      <a-col :md="24" :lg="12">
        <a-form-model-item label="姓名" prop="name">
          <a-input v-model="form.name" :max-length="16" placeholder="请输入" />
        </a-form-model-item>
      </a-col>
      <a-col :md="24" :lg="12">
        <a-form-model-item label="手机号" prop="mobile">
          <a-input v-model="form.mobile" :max-length="11" placeholder="请输入" />
        </a-form-model-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :md="24" :lg="12">
        <a-form-model-item label="证件号" prop="idCard">
          <a-input v-model="form.idCard" :max-length="18" placeholder="请输入" />
        </a-form-model-item>
      </a-col>
      <a-col :md="24" :lg="12">
        <a-form-model-item label="公司" prop="company">
          <a-input v-model="form.company" :max-length="16" placeholder="请确认" />
        </a-form-model-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :md="24" :lg="12">
        <a-form-model-item label="职务" prop="job">
          <a-input v-model="form.job" :max-length="16" placeholder="请确认" />
        </a-form-model-item>
      </a-col>
      <a-col :md="24" :lg="12">
        <a-form-model-item label="拜访事由" prop="purpose">
          <a-select mode="single" allowClear v-model="form.purpose" placeholder="请选择">
            <a-select-option
              v-for="purpose in purposes"
              :key="purpose.id"
              :value="purpose.name"
            >{{ purpose.name }}</a-select-option>
          </a-select>
        </a-form-model-item>
      </a-col>
    </a-row>
  </a-form-model>
</template>

<script>
import FormConfig from '@/config/form.config'
import OrderRuleBuiler from './OrderRule'

import * as PurposeService from '@/service/system/PurposeService'

export default {
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      ...FormConfig,
      currentIndex: 0,
      labelCol: FormConfig.labelCol,
      wrapperCol: FormConfig.wrapperCol,
      validateMessage: FormConfig.validateMessages,
      loading: false,
      visible: false,
      confirmLoading: false,
      purposes: [],
      rules: {}
    }
  },
  watch: {
    'form.purposeId' (val) {
      if (val !== undefined) {
        this.form.purpose = {}
        this.form.purpose.id = val
      }
    }
  },
  async created () {
    const purposes = await PurposeService.queryAll({
      showLoading: false
    })
    this.purposes = purposes

    this.rules = OrderRuleBuiler.build(this.form)
  },
  methods: {
    handleSubmit () {
      return new Promise((resolve, reject) => {
        this.$refs['orderCreate'].validate(valid => {
          if (valid) {
            resolve(true)
          }
        })
      })
    }
  }
}
</script>

<style scoped>
</style>
