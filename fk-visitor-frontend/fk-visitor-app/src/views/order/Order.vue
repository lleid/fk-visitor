<template>
  <div class="container">
    <div class="form">
      <div class="steps">
        <c-icon type="fv-tag-multiple"></c-icon>
        <template v-if="currentIndex===0">
          <span>请填写访客信息</span>
        </template>
        <template v-if="currentIndex===1">
          <span>请拍照</span>
        </template>
        <template v-if="currentIndex===2">
          <span>请阅读协议</span>
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
    <div class="operate">
      <a-row>
        <a-col :span="4">
          <a class="btn blue rounded" @click="handlePrevious">上一步</a>
        </a-col>
        <a-col :span="16"></a-col>
        <a-col :span="4" style="text-align:right">
          <a class="btn blue rounded" @click="handleNext">下一步</a>
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

<style scoped lang="less">
.container {
  height: 100%;
  padding-top: 50px;
  position: relative;
  padding-bottom: 60px;
}

.form {
  .steps {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 50px;
    line-height: 50px;
    font-size: 16px;
    border-bottom: 1px solid #0565aa;
    background: #fff;
    padding: 0 24px;

    i {
      margin-right: 12px;
      color: #003b86;
    }

    span {
    }
  }

  padding: 24px;
  height: 100%;
  overflow: auto;
  background: #fff;
}

.operate {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}

.operate .btn {
  width: 120px;
  text-align: center;
}
</style>
