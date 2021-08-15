<template>
  <c-modal title="编辑预约" centered :destroyOnClose="true" :visible="visible" :loading="loading" :confirmLoading="confirmLoading" @ok="handleSubmit" @cancel="handleClose">
    <a-form-model ref="teamUpdate" :model="form" :rules="rules" :labelCol="labelCol" :wrapperCol="wrapperCol" :validate-messages="validateMessages">
      <a-form-model-item label="团队名称" prop="company">
        <a-input v-model="form.company" :max-length="16" placeholder="请确认" />
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
import moment from 'moment'

import TeamRuleBuilder from './TeamRule'
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
      visible: false,
      loading: false,
      confirmLoading: false,
      purposes: [],
      visitAreas: [],
      form: {
        company: '',
        purpose: {},
        visitArea: {},
        interviewer: '',
        orderAt: undefined,
        purposeId: undefined,
        visitAreaId: undefined
      },
      rules: {}
    }
  },
  methods: {
    moment,
    async edit (record) {
      this.visible = true
      this.loading = true
      try {
        const purposes = await PurposeService.query({ isDeleted: false }, {
          showLoading: false
        })
        this.purposes = purposes

        const visitAreas = await VisitAreaService.query({ isDeleted: false }, {
          showLoading: false
        })
        this.visitAreas = visitAreas

        const result = await TeamService.get(record.id, { showLoading: false })
        if (result.purpose) {
          result.purposeId = result.purpose.id
        }
        if (result.visitArea) {
          result.visitAreaId = result.visitArea.id
        }
        if (result.orderAt) {
          result.orderAt = moment(result.orderAt, 'YYYY-MM-DD')
        }
        this.form = result
        this.rules = TeamRuleBuilder.build(this.form)
      } catch (error) {
      }
      this.loading = false
    },
    handleSubmit () {
      this.$refs['teamUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true

          this.form.orderAt = this.form.orderAt.format('YYYY-MM-DD')

          const requestModel = {
            orderAt: this.form.orderAt,
            company: this.form.company,
            interviewer: this.form.interviewer,
            purpose: {
              id: this.form.purposeId
            },
            visitArea: {
              id: this.form.visitAreaId
            }
          }

          try {
            await TeamService.update(this.form.id, requestModel)
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
      if (this.$refs['teamUpdate'] !== undefined) {
        this.$refs['teamUpdate'].resetFields()
      }
      this.visible = false
    },
    onChange (date, dateString) {
    }
  }
}
</script>
