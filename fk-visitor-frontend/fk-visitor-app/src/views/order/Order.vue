<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <c-icon type="fv-yuan"></c-icon>
          <template v-if="currentIndex===0">
            <span>{{ tipName.tip1 }}</span>
          </template>
          <template v-if="currentIndex===1">
            <span>{{ tipName.tip2 }}</span>
          </template>
          <template v-if="currentIndex===2">
            <span>{{ tipName.tip3 }}</span>
          </template>
        </div>

        <order-step1 :form="form" v-if="currentIndex===0" ref="step1"></order-step1>
        <order-step2 v-if="currentIndex===1" ref="step2" :file="file" @change="file=$event"></order-step2>
        <order-step3
          v-if="currentIndex===2"
          ref="step3"
          :isChecked="isChecked"
          @change="isChecked=$event"
        ></order-step3>
      </div>
    </div>
    <div class="operate">
      <a-row>
        <a-col :span="4">
          <a
            class="button button-primary button-pill button2"
            @click="handlePrevious"
            v-if="currentIndex>0"
          >{{ btnName.btn1 }}</a>
        </a-col>
        <a-col :span="16"></a-col>
        <a-col :span="4" style="text-align:right">
          <a class="button button-primary button-pill button2" @click="handleNext">{{ btnName.btn2 }}</a>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'

import * as OrderService from '@/service/data/OrderService'
import * as AppointmentService from '@/service/data/AppointmentService'

import OrderStep1 from './modules/OrderStep1'
import OrderStep2 from './modules/OrderStep2'
import OrderStep3 from './modules/OrderStep3'

const TipCN = {
  tip1: '请填写访客信息',
  tip2: '请拍照',
  tip3: '请阅读保密协议',
  tip4: '请同意以上协议'
}

const TipEN = {
  tip1: 'Please input you information',
  tip2: 'Please take photo',
  tip3: 'Confideniality agreement',
  tip4: 'Please agree to the above agreement'
}

const BtnCN = {
  btn1: '上一步',
  btn2: '下一步'
}

const BtnEN = {
  btn1: 'Previous',
  btn2: 'Next'
}

export default {
  components: {
    OrderStep1,
    OrderStep2,
    OrderStep3
  },
  data () {
    return {
      currentIndex: 0,
      isChecked: false,
      form: {
        name: '',
        mobile: '',
        company: '',
        job: '',
        purpose: {},
        visitArea: {},
        idCard: '',
        interviewer: '',
        purposeId: undefined,
        visitAreaId: undefined
      },
      file: undefined
    }
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    tipName () {
      if (this.language === 'EN') {
        return TipEN
      }
      return TipCN
    },
    btnName () {
      if (this.language === 'EN') {
        return BtnEN
      }
      return BtnCN
    }
  },
  async created () {
    const appointmentId = this.$route.query.appointmentId
    const orderId = this.$route.query.orderId
    if (appointmentId) {
      const appointment = await AppointmentService.get(appointmentId)
      if (appointment) {
        this.form.name = appointment.name
        this.form.mobile = appointment.mobile
        this.form.company = appointment.company
        this.form.job = appointment.job
        this.form.idCard = appointment.idCard
        this.form.interviewer = appointment.interviewer
        this.form.appointmentId = appointment.id

        if (appointment.purpose) {
          this.form.purposeId = appointment.purpose.id
        }
        if (appointment.visitArea) {
          this.form.visitAreaId = appointment.visitArea.id
        }
      }
    }

    if (orderId) {
      const order = await OrderService.get(orderId)
      if (order) {
        this.form.name = order.name
        this.form.mobile = order.mobile
        this.form.company = order.company
        this.form.job = order.job
        this.form.idCard = order.idCard
        this.form.interviewer = order.interviewer

        if (order.purpose) {
          this.form.purposeId = order.purpose.id
        }
        if (order.visitArea) {
          this.form.visitAreaId = order.visitArea.id
        }
      }
    }
  },
  methods: {
    handlePrevious () {
      this.currentIndex--
    },
    async handleNext () {
      if (this.currentIndex === 0) {
        this.$refs.step1.handleSubmit().then(e => {
          if (e) {
            this.currentIndex++
          }
        })
      } else if (this.currentIndex === 2) {
        console.log(this.file)

        if (!this.isChecked) {
          this.$message.error(this.tipName.tip4)
        } else {
          const formData = new FormData()

          if (this.file) {
            formData.append('file', this.file)
          }

          formData.append('name', this.form.name)
          formData.append('mobile', this.form.mobile)
          formData.append('company', this.form.company)
          formData.append('job', this.form.job)
          formData.append('interviewer', this.form.interviewer)
          if (this.form.purposeId) {
            formData.append('purposeId', this.form.purposeId)
          }
          if (this.form.visitAreaId) {
            formData.append('visitAreaId', this.form.visitAreaId)
          }
          formData.append('idCard', this.form.idCard)
          if (this.form.appointmentId) {
            formData.append('appointmentId', this.form.appointmentId)
          }

          const res = await OrderService.create(formData, {
            headers: {
              'content-type': 'multipart/form-data'
            },
            showSuccess: false
          })
          console.log(res)
          this.$router.push({ path: ROUTE_PATH.APP_PATH.SUCCESS_PATH, query: { orderId: res.values.id } })
        }
      } else {
        this.currentIndex++
      }
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  position: relative;
  padding-bottom: 80px;
}

.wrapper {
  background: url(../../assets/background.png);
  padding: 10px;
  height: 100%;
  box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
  background-size: cover;
}

.form {
  padding: 24px;
  height: 100%;
  overflow: auto;
  background: #fff;
  position: relative;
  padding-top: 80px;

  .steps {
    height: 60px;
    line-height: 60px;
    font-size: 18px;
    font-weight: bold;
    border-bottom: 1px solid #0565aa;
    background: #fff;
    color: #003b86;
    position: absolute;
    top: 0;
    left: 24px;
    right: 24px;

    i {
      margin-right: 12px;
    }
  }
}

.operate {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>
