<template>
  <div class="step1-wrapper">
    <a-form-model
      ref="orderCreate"
      :model="form"
      :rules="rules"
      layout="vertical"
      :validate-messages="this.language!=='EN'?validateMessage:null"
    >
      <a-row>
        <a-col :md="24" :lg="12">
          <a-form-model-item :label="formItem.item1.label" prop="name">
            <a-input
              v-model="form.name"
              :max-length="16"
              :placeholder="formItem.item1.placeholder"
              width
            />
          </a-form-model-item>
        </a-col>
        <a-col :md="24" :lg="12">
          <a-form-model-item :label="formItem.item2.label" prop="mobile">
            <a-input
              v-model="form.mobile"
              :max-length="11"
              :placeholder="formItem.item2.placeholder"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="24" :lg="12">
          <a-form-model-item :label="formItem.item4.label" prop="company">
            <a-input v-model="form.company" :placeholder="formItem.item4.placeholder" />
          </a-form-model-item>
        </a-col>
        <a-col :md="24" :lg="12">
          <a-form-model-item :label="formItem.item5.label" prop="title">
            <a-input v-model="form.title" :placeholder="formItem.item5.placeholder" />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="24" :lg="12">
          <a-form-model-item :label="formItem.item6.label" prop="interviewer">
            <a-input v-model="form.interviewer" :placeholder="formItem.item6.placeholder" />
          </a-form-model-item>
        </a-col>
        <a-col :md="24" :lg="12">
          <a-form-model-item :label="formItem.item7.label" prop="purposeId">
            <a-select
              mode="single"
              allowClear
              v-model="form.purposeId"
              :placeholder="formItem.item7.placeholder"
            >
              <a-select-option v-for="purpose in purposes" :key="purpose.id" :value="purpose.id">
                <template v-if="language === 'CN'">{{ purpose.cnName }}</template>
                <template v-else>{{ purpose.enName }}</template>
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="24" :lg="12">
          <a-form-model-item :label="formItem.item8.label" prop="visitAreaId">
            <a-select
              mode="single"
              allowClear
              v-model="form.visitAreaId"
              :placeholder="formItem.item8.placeholder"
            >
              <a-select-option v-for="area in visitAreas" :key="area.id" :value="area.id">
                <template v-if="language === 'CN'">{{ area.cnName }}</template>
                <template v-else>{{ area.enName }}</template>
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
        <a-col :md="24" :lg="12">
          <a-form-model-item :label="formItem.item3.label" prop="idCard">
            <a-input
              v-model="form.idCard"
              :max-length="18"
              :placeholder="formItem.item3.placeholder"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
    </a-form-model>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import FormConfig from '@/config/form.config'
import OrderRuleBuiler from './OrderRule'

import * as PurposeService from '@/service/system/PurposeService'
import * as VisitAreaService from '@/service/system/VisitAreaService'

const FormCN = {
  item1: {
    label: '您的姓名',
    placeholder: '请输入您的姓名'
  },
  item2: {
    label: '联系方式',
    placeholder: '请输入您的联系方式'
  },
  item3: {
    label: '您的证件',
    placeholder: '请输入您的证件'
  },
  item4: {
    label: '您的公司',
    placeholder: '请输入您的公司'
  },
  item5: {
    label: '您的职务',
    placeholder: '请输入您的职务'
  },
  item6: {
    label: '受访人姓名',
    placeholder: '请输入受访人姓名'
  },
  item7: {
    label: '拜访事由',
    placeholder: '请选择拜访事由'
  },
  item8: {
    label: '参观区域',
    placeholder: '请选择参观区域'
  }
}

const FormEN = {
  item1: {
    label: 'Name',
    placeholder: 'Please input you name'
  },
  item2: {
    label: 'Contact',
    placeholder: 'Please input you contact'
  },
  item3: {
    label: 'ID Card',
    placeholder: 'Please input you ID card'
  },
  item4: {
    label: 'Company',
    placeholder: 'Please input you company'
  },
  item5: {
    label: 'Post',
    placeholder: 'Please input you post'
  },
  item6: {
    label: 'People visited',
    placeholder: 'Please input you people visited'
  },
  item7: {
    label: 'Visit purpose',
    placeholder: 'Please input you visit purpose'
  },
  item8: {
    label: 'Visit area',
    placeholder: 'Please input you Visit area'
  }
}

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
      if (val !== undefined) {
        this.form.visitArea.id = val
      }
    },
    language (val) {
      if (val === 'EN') {
        this.rules = OrderRuleBuiler.build_EN(this.form)
      } else {
        this.rules = OrderRuleBuiler.build(this.form)
      }
    }
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    formItem () {
      if (this.language === 'EN') {
        return FormEN
      }
      return FormCN
    }
  },
  data () {
    return {
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

    if (this.language === 'EN') {
      this.rules = OrderRuleBuiler.build_EN(this.form)
    } else {
      this.rules = OrderRuleBuiler.build(this.form)
    }
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
.step1-wrapper {
  background: #ffffff;
  height: 100%;
  padding: 24px;
  padding-top: 52px;
}

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
