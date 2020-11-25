<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <span>{{ msgItem.title }}</span>
        </div>
        <div class="step-wrapper">
          <div class="keyboard">
            <div class="invite-code" v-if="mobile">{{ mobile }}</div>
            <div class="invite-code" v-else>{{ msgItem.errorMsg1 }}</div>
            <div class="item-list">
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('1')">1</a>
              </div>
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('2')">2</a>
              </div>
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('3')">3</a>
              </div>
              <div class="item">
                <a class="keyboard-btn" @click="handleDelete()">
                  <c-icon type="fv-qingchu" style="font-size:18px"></c-icon>
                </a>
              </div>
            </div>
            <div class="item-list">
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('4')">4</a>
              </div>
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('5')">5</a>
              </div>
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('6')">6</a>
              </div>
              <div class="item">
                <a class="keyboard-btn" @click="handleEmpty()">
                  <c-icon type="fv-shanchu"></c-icon>
                </a>
              </div>
            </div>
            <div class="item-list">
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('7')">7</a>
              </div>
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('8')">8</a>
              </div>
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('9')">9</a>
              </div>
              <div class="item">
                <a class="keyboard-btn" @click="handleAdd('0')">0</a>
              </div>
            </div>
            <div class="item-list">
              <div class="btn btn-primary btn-fill" @click="handleSubmit">{{ msgItem.btn }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import adapter from 'webrtc-adapter'
import { BrowserMultiFormatReader } from '@zxing/library'

import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import * as OrderService from '@/service/data/OrderService'

const MsgCN = {
  title: '历史访客',
  btn: '确定',
  tip: '或',
  errorMsg1: '请输入您的手机号',
  errorMsg2: '无历史记录'
}

const MsgEN = {
  title: 'Visitors to the history',
  btn: 'Confirm',
  tip: 'Or',
  errorMsg1: 'Please input you phone number',
  errorMsg2: 'No history record'
}

export default {
  components: {
  },
  data () {
    return {
      form: {},
      mobile: '',
      codeReader: new BrowserMultiFormatReader(),
      order: {}
    }
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    msgItem () {
      if (this.language === 'EN') {
        return MsgEN
      }
      return MsgCN
    }
  },
  async created () {
  },
  methods: {
    async handleSubmit () {
      if (this.mobile === '' || this.mobile === undefined) {
        this.$message.error(this.msgItem.errorMsg1)
        return false
      }
      this.order = {}
      const order = await OrderService.queryHistory({
        mobile: this.mobile
      }, { showLoading: false })
      this.order = order

      if (!order) {
        this.$message.error(this.msgItem.errorMsg2)
      } else {
        this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH, query: { orderId: this.order.id } })
      }
    },
    handleAdd (param) {
      if (this.mobile.length < 11) {
        this.mobile = this.mobile + param
      }
    },
    handleDelete () {
      if (this.mobile.length > 0) {
        this.mobile = this.mobile.substring(0, this.mobile.length - 1)
      }
    },
    handleEmpty () {
      this.mobile = ''
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  position: relative;
  padding-bottom: 150px;
}

.wrapper {
  background: url(../../assets/background.png);
  padding: 10px;
  height: 100%;
  box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
  background-size: cover;
}

.form {
  padding: 50px;
  height: 100%;
  overflow: auto;
  background: #eef3f9;
  position: relative;
  padding-top: 80px;

  .steps {
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

  .step-wrapper {
    background: #ffffff;
    height: 100%;
    padding: 24px;
    padding-top: 24px;
  }
}

.keyboard {
  width: 400px;
  margin: auto auto;
  vertical-align: middle;

  .invite-code {
    color: #013b84;
    border: 2px solid #013b84;
    padding: 5px 24px;
    height: 50px;
    line-height: 40px;
    border-radius: 8px;
    margin-bottom: 34px;
  }

  .item-list {
    margin-bottom: 34px;
  }

  .item {
    width: 25%;
    display: inline-block;
    padding: 0 5px;

    .keyboard-btn {
      height: 50px;
      line-height: 50px;
      border: 2px solid #013b84;
      display: block;
      text-align: center;
      border-radius: 8px;
      color: #013b84;
    }
  }
}
</style>
