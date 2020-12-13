<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <span>{{ msgItem.title }}</span>
        </div>
        <div class="step-wrapper">
          <div class="userinfo">
            <div class="qr-scanner">
              <div class="box">
                <div class="line"></div>
                <div class="angle"></div>
              </div>
            </div>
            <video ref="video" class="video" id="video" width="400"></video>
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
import { APP_MUTATIONS } from '@/store/modules/app-store'
import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import * as OrderService from '@/service/data/OrderService'

const MsgCN = {
  title: '签出',
  btn: '确定',
  tip: '或',
  errorMsg1: '请输入您的手机号',
  errorMsg2: '无拜访记录'
}

const MsgEN = {
  title: 'Sign out',
  btn: 'Confirm',
  tip: 'Or',
  errorMsg1: 'Please input you phone number',
  errorMsg2: 'No visit record'
}

export default {
  components: {
  },
  data () {
    return {
      orderId: '',
      codeReader: new BrowserMultiFormatReader()
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
  mounted () {
    this.codeReader.getVideoInputDevices()
      .then((videoInputDevices) => {
        this.videoList = videoInputDevices
        const selectedDeviceId = videoInputDevices[0].deviceId
        this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
          if (result && this.orderId !== result.text) {
            this.orderId = result.text
            this.findOrder(result.text)
          }
          if (err && !(err)) {
            console.error(err)
          }
        })
      })
      .catch((err) => {
        console.error(err)
      })
  },
  destroyed () {
    this.codeReader.stopContinuousDecode()
  },
  methods: {
    async findOrder (id) {
      const that = this
      const order = await OrderService.get(id, {
        showLoading: false
      })
      if (order && order.id !== null) {
        const orderId = order.id
        this.$confirm({
          title: '确认信息',
          content: '确定签出当前访客信息吗？',
          onOk () {
            OrderService.singOut(orderId).then(res => {
              that.$router.push({ path: ROUTE_PATH.HOME_PATH })
              that.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, true)
            })
          },
          onCancel () {
            that.orderId = ''
          }
        })
      } else {
        this.$message.error(this.msgItem.errorMsg2)
      }
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  position: relative;
  padding-bottom: 100px;
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
    padding-top: 52px;
  }
}

.userinfo {
  width: 400px;
  height: 300px;
  position: absolute;
  top: 50%;
  margin-top: -132px;
  right: 50%;
  margin-right: -175px;

  .qr-scanner {
    width: 400px;
    height: 300px;
    position: absolute;
    top: 50%;
    margin-top: -148px;
  }
}
</style>
