/* eslint-disable vue/require-v-for-key */
<template>
  <div class="wrapper">
    <div class="form">
      <div class="form-steps">
        <a-steps :current="currentIndex" size="small">
          <a-step title="请填写访客信息" />
          <a-step title="请拍照" />
          <a-step title="请阅读协议" />
        </a-steps>
      </div>
      <div class="form-content">
        <order-step1 :form="form" v-if="currentIndex===0" ref="step1"></order-step1>
        <order-step2 v-if="currentIndex===1" ref="step2"></order-step2>
        <order-step3 v-if="currentIndex===2" ref="step3"></order-step3>
      </div>
    </div>
    <div class="operate">
      <a-row>
        <a-col :span="4">
          <a-button
            type="primary"
            size="large"
            shape="round"
            v-if="currentIndex !==0"
            @click="handlePrevious"
          >
            上一步
            <a-icon type="logout" />
          </a-button>
        </a-col>
        <a-col :span="16"></a-col>
        <a-col :span="4" style="text-align:right">
          <a-button type="primary" size="large" shape="round" @click="handleNext">
            下一步
            <a-icon type="right-circle" />
          </a-button>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import OrderStep1 from './modules/OrderStep1'
import OrderStep2 from './modules/OrderStep2'
import OrderStep3 from './modules/OrderStep3'

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
        job: '',
        purposeId: undefined,
        idCard: ''
      }
    }
  },
  created () {
  },
  methods: {
    handlePrevious () {
      this.currentIndex--
    },
    handleNext () {
      if (this.currentIndex === 0) {
        this.$refs.step1.handleSubmit().then(e => {
          if (e) {
            this.currentIndex++
          }
        })
      } else {
        this.currentIndex++
      }
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100%;
}
.form {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 100px;
  background: #fff;
  padding: 24px;
  padding-top: 0;
}

.form .form-steps {
  padding: 24px 0px;
  border-bottom: 1px solid #eeeeee;
}
.form .form-content {
  margin-top: 24px;
}
.operate {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>
