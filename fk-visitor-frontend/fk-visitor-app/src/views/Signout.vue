<template>
  <div class="singout-container">
    <div class="signout-wrapper">
      <div class="signout-form">
        <div class="form-header">
          <span>{{ msg.title }}</span>
        </div>
        <div class="form-content">
          <div class="scanner-wrapper">
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

const log = require('electron-log')

const mes = {
  cn: {
    title: '签出',
    btn: '确定',
    tip: '或',
    error1: '请输入您的手机号',
    error2: '无拜访记录'
  },
  en: {
    title: 'Sign out',
    btn: 'Confirm',
    tip: 'Or',
    error1: 'Please input you phone number',
    error2: 'No visit record'
  }
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
    msg () {
      return this.language === 'EN' ? mes.en : mes.cn
    }
  },
  async created () {
  },
  mounted () {
    this.codeReader.getVideoInputDevices().then((videoInputDevices) => {
      this.videoList = videoInputDevices
      const selectedDeviceId = videoInputDevices[0].deviceId
      this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
        if (result && this.orderId !== result.text) {
          this.orderId = result.text
          this.findOrder(result.text)
        }
      })
    }).catch((err) => {
      log.error(err)
    })
  },
  destroyed () {
    this.codeReader.stopContinuousDecode()
  },
  methods: {
    async findOrder (id) {
      const that = this
      const order = await OrderService.get(id, { showLoading: false, showSuccess: false })
      if (order && order.id !== null) {
        const orderId = order.id
        that.$confirm({
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
        this.$message.error(this.msg.error2)
      }
    }
  }
}
</script>

<style scoped lang="less">
.singout-container {
  height: 100%;
  position: relative;
  padding-bottom: 100px;

  .signout-wrapper {
    background: url(../assets/background.png);
    padding: 10px;
    height: 100%;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
    background-size: cover;

    .signout-form {
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

      .form-content {
        background: #ffffff;
        height: 100%;
        padding: 24px;
        padding-top: 52px;
      }
    }
  }
}

.scanner-wrapper {
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
