<template>
  <div class="order-container">
    <div class="order-wrapper">
      <div class="order-form">
        <div class="form-header">
          <template v-if="currentIndex===0">
            <span>{{ msg.tips.tip1 }}</span>
          </template>
          <template v-if="currentIndex===1">
            <span>{{ msg.tips.tip2 }}</span>
          </template>
          <template v-if="currentIndex===2">
            <span>{{ msg.tips.tip3 }}</span>
          </template>
        </div>
        <order-step1 :form="form" v-if="currentIndex===0" ref="step1"></order-step1>
        <order-step2 :form="form" v-if="currentIndex===1" ref="step2"></order-step2>
        <order-step3 :form="form" v-if="currentIndex===2" ref="step3"></order-step3>
      </div>
    </div>
    <div class="order-operate-wrapper">
      <a-row>
        <a-col :span="4">
          <span class="btn" @click="handlePrevious" v-if="currentIndex>0">{{ msg.btns.btn1 }}</span>
        </a-col>
        <a-col :span="16"></a-col>
        <a-col :span="4" style="text-align:right">
          <span class="btn" @click="handleNext">{{ msg.btns.btn2 }}</span>
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

import OrderStep1 from './order/modules/OrderStep1'
import OrderStep2 from './order/modules/OrderStep2'
import OrderStep3 from './order/modules/OrderStep3'

const lang = {
  cn: {
    tips: {
      tip1: '请填写访客信息',
      tip2: '请阅读保密协议',
      tip3: '请拍照'
    },
    btns: {
      btn1: '上一步',
      btn2: '下一步'
    }
  },
  en: {
    tips: {
      tip1: 'Please fill in the visitor information',
      tip2: 'Please read the confidentiality agreement',
      tip3: 'Please take pictures'
    },
    btns: {
      btn1: 'Previous',
      btn2: 'Next'
    }
  }
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
      form: {
        name: '',
        mobile: '',
        company: '',
        title: '',
        purpose: {},
        visitArea: {},
        idCard: '',
        interviewer: '',
        purposeId: undefined,
        visitAreaId: undefined,
        avatar: '',
        isChecked: false
      }
    }
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    msg () {
      return this.language === 'EN' ? lang.en : lang.cn
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
        this.form.title = appointment.title
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
        this.form.title = order.title
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
      } else if (this.currentIndex === 1) {
        if (!this.form.isChecked) {
          this.$message.error(this.msg.tips.tip2)
          return false
        }
        this.currentIndex++
      } else if (this.currentIndex === 2) {
        if (this.form.avatar === '') {
          this.$message.error(this.msg.tips.tip3)
          return false
        }

        const res = await OrderService.create(this.form, {
          showSuccess: false
        })
        this.$router.push({ path: ROUTE_PATH.APP_PATH.SUCCESS_PATH, query: { orderId: res.values.id } })
      }
    }
  }
}
</script>

<style scoped lang="less">
.order-container {
  height: 100%;
  position: relative;
  padding-bottom: 100px;

  .order-wrapper {
    background: url(../assets/background.png);
    padding: 10px;
    height: 100%;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
    background-size: cover;

    .order-form {
      padding: 50px;
      height: 100%;
      overflow: auto;
      background: #eef3f9;
      position: relative;
      padding-top: 80px;

      .form-header {
        height: 80px;
        line-height: 80px;
        font-size: 24px;
        font-weight: bold;
        color: #003b86;
        position: absolute;
        top: 0;
        left: 50px;
        right: 50px;
      }
    }
  }

  .order-operate-wrapper {
    position: absolute;
    bottom: 20px;
    left: 0;
    right: 0;
  }
}
</style>
