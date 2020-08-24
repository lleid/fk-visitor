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
      <order-step1 :form="form" v-if="currentIndex===0" ref="step1"></order-step1>
      <order-step2 v-if="currentIndex===1" ref="step2" :file="file" @change="file=$event"></order-step2>
      <order-step3
        v-if="currentIndex===2"
        ref="step3"
        :isChecked="isChecked"
        @change="isChecked=$event"
      ></order-step3>
    </div>
    <div class="operate">
      <a-row>
        <a-col :span="4">
          <a-button class="previous" @click="handlePrevious">上一步</a-button>
        </a-col>
        <a-col :span="16"></a-col>
        <a-col :span="4" style="text-align:right">
          <a-button class="next" @click="handleNext">下一步</a-button>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import ROUTE_PATH from '@/router/route-paths'

import * as OrderService from '@/service/data/OrderService'

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
      isChecked: false,
      form: {
        name: '',
        mobile: '',
        company: '',
        job: '',
        purpose: '',
        idCard: ''
      },
      file: undefined
    }
  },
  created () {
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
          this.$message.error('请同意以上协议')
        } else {
          const formData = new FormData()

          if (this.file) {
            formData.append('file', this.file)
          }

          formData.append('name', this.form.name)
          formData.append('mobile', this.form.mobile)
          formData.append('company', this.form.company)
          formData.append('job', this.form.job)
          formData.append('purposeId', this.form.purposeId)
          formData.append('idCard', this.form.idCard)

          const res = await OrderService.create(formData, {
            headers: {
              'content-type': 'multipart/form-data'
            }
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

<style scoped>
.wrapper {
  height: 100%;
}
.form {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 80px;
  background: #fff;
  padding: 24px;
  padding-top: 0;
}

.form .form-steps {
  padding: 24px 0px;
}

.operate {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}

.next {
  position: relative;
  padding: 20px 40px;
  font-size: 1.4rem;
  background-color: #00b3b4;
  background-size: 46px 26px;
  border: 1px solid #555;
  color: white;
  transition: all ease 0.3s;
}

.next::after {
  position: absolute;
  top: 50%;
  right: 0.6em;
  transform: translateY(-50%);
  content: '»';
  font-size: 1.2em;
  transition: all ease 0.3s;
}

.previous {
  position: relative;
  padding: 20px 40px;
  font-size: 1.4rem;
  background-color: #00b3b4;
  background-size: 46px 26px;
  border: 1px solid #555;
  color: white;
  transition: all ease 0.3s;
}

.previous::after {
  position: absolute;
  top: 50%;
  left: 0.6em;
  transform: translateY(-50%);
  content: '«';
  font-size: 1.2em;
  transition: all ease 0.3s;
}
</style>
