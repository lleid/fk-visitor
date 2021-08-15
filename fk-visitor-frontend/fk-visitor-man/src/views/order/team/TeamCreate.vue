<template>
  <c-modal title="添加预约" centered :destroyOnClose="true" :visible="visible" :loading="loading" :confirmLoading="confirmLoading" @ok="handleSubmit" @cancel="handleClose">
    <a-form-model :label-col="labelCol" :wrapper-col="wrapperCol" ref="teamCreate" :model="form" :rules="rules" :validate-messages="validateMessages">
      <a-form-model-item label="团队名称" prop="company">
        <a-input v-model="form.company" :max-length="32" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="预约时间" prop="orderAt">
        <a-date-picker v-model="form.orderAt" />
      </a-form-model-item>
      <a-form-model-item label="拜访事由" prop="purposeId">
        <a-select mode="single" allowClear v-model="form.purposeId" placeholder="请选择">
          <a-select-option v-for="purpose in purposes" :key="purpose.id" :value="purpose.id">{{ purpose.cnName }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="参观区域" prop="visitAreaId">
        <a-select mode="single" allowClear v-model="form.visitAreaId" placeholder="请选择">
          <a-select-option v-for="area in visitAreas" :key="area.id" :value="area.id">{{ area.cnName }}</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="受访人" prop="interviewer">
        <a-input v-model="form.interviewer" placeholder="请输入" />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import TeamRuleBuiler from './TeamRule'
import moment from 'moment'

import * as TeamService from '@/service/order/TeamService'
import * as PurposeService from '@/service/mdata/PurposeService'
import * as VisitAreaService from '@/service/mdata/VisitAreaService'

export default {
  data () {
    return {
      ...FormConfig,
      labelCol: FormConfig.labelCol,
      wrapperCol: FormConfig.wrapperCol,
      validateMessage: FormConfig.validateMessages,
      loading: false,
      visible: false,
      confirmLoading: false,
      purposes: [],
      visitAreas: [],
      form: {
        name: '',
        purpose: {},
        visitArea: {},
        interviewer: '',
        purposeId: undefined,
        visitAreaId: undefined,
        orderAt: undefined
      },
      rules: {}
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
    }
  },
  methods: {
    moment,
    async add () {
      this.loading = true
      this.visible = true

      const purposes = await PurposeService.query({ isDeleted: false }, {
        showLoading: false
      })
      this.purposes = purposes

      const visitAreas = await VisitAreaService.query({ isDeleted: false }, {
        showLoading: false
      })
      this.visitAreas = visitAreas

      this.rules = TeamRuleBuiler.build(this.form)
      this.loading = false
    },
    handleSubmit () {
      this.$refs['teamCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          this.form.orderAt = this.form.orderAt.format('YYYY-MM-DD')
          try {
            await TeamService.create(this.form)
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
      if (this.$refs['teamCreate'] !== undefined) {
        this.$refs['teamCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
