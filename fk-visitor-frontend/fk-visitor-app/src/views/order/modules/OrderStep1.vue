<template>
  <div class="step1-wrapper">
    <a-form-model
      ref="orderCreate"
      :model="form"
      :rules="rules"
      layout="vertical"
      :validate-messages="validateMessages"
    >
      <a-row>
        <a-col :md="24" :lg="12">
          <a-form-model-item label="您的姓名" prop="name">
            <a-input v-model="form.name" :max-length="16" placeholder="请输入" width />
          </a-form-model-item>
        </a-col>
        <a-col :md="24" :lg="12">
          <a-form-model-item label="联系方式" prop="mobile">
            <a-input v-model="form.mobile" :max-length="11" placeholder="请输入" />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="24" :lg="12">
          <a-form-model-item label="您的证件" prop="idCard">
            <a-input v-model="form.idCard" :max-length="18" placeholder="请输入" />
          </a-form-model-item>
        </a-col>
        <a-col :md="24" :lg="12">
          <a-form-model-item label="您的公司" prop="company">
            <a-input v-model="form.company" :max-length="16" placeholder="请输入" />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="24" :lg="12">
          <a-form-model-item label="您的职务" prop="job">
            <a-input v-model="form.job" :max-length="16" placeholder="请输入" />
          </a-form-model-item>
        </a-col>
        <a-col :md="24" :lg="12">
          <a-form-model-item label="受访人" prop="interviewer">
            <a-input v-model="form.interviewer" :max-length="16" placeholder="请输入" />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="24" :lg="12">
          <a-form-model-item label="拜访事由" prop="purposeId">
            <a-select mode="single" allowClear v-model="form.purposeId" placeholder="请选择">
              <a-select-option
                v-for="purpose in purposes"
                :key="purpose.id"
                :value="purpose.id"
              >{{ purpose.cnName }}</a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
        <a-col :md="24" :lg="12">
          <a-form-model-item label="拜访区域" prop="visitAreaId">
            <a-select mode="single" allowClear v-model="form.visitAreaId" placeholder="请选择">
              <a-select-option
                v-for="area in visitAreas"
                :key="area.id"
                :value="area.id"
              >{{ area.cnName }}</a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
      </a-row>
    </a-form-model>
  </div>
</template>

<script>
import FormConfig from '@/config/form.config'
import OrderRuleBuiler from './OrderRule'

import * as PurposeService from '@/service/system/PurposeService'
import * as VisitAreaService from '@/service/system/VisitAreaService'

export default {
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  watch: {
    'form.purposeId' (val) {
      if (val !== undefined) {
        this.form.purpose.id = val
      }
    },
    'form.visitAreaId' (val) {
      console.log(val)
      if (val !== undefined) {
        this.form.visitArea.id = val
      }
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
      visitAreas: [],
      rules: {}
    }
  },
  async created () {
    const purposes = await PurposeService.queryAll({
      showLoading: false
    })
    this.purposes = purposes

    const visitAreas = await VisitAreaService.queryAll({
      showLoading: false
    })
    this.visitAreas = visitAreas

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

<style scoped lang="less">
.ant-input {
  border-top: 0;
  border-left: 0;
  border-right: 0;
  width: 90%;
}

.ant-select {
  width: 90%;
}

.ant-select-selection {
  border-top: 0;
  border-left: 0;
  border-right: 0;
}
</style>
