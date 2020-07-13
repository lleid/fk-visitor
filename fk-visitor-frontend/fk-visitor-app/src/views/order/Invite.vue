<template>
  <div class="container">
    <a-row :gutter="16">
      <a-col :span="12">
        <a-card title="扫一扫" :bordered="false">
          <video ref="video" id="video" width="410" height="400" style="border-radius:20px"></video>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card title="访客信息" :bordered="false">
          <div class="order-wrapper">
            <div class="order-info" v-if="order">
              <div class="item">
                <span class="label">姓名</span>
                <span class="value">{{ order.name }}</span>
              </div>
              <div class="item">
                <span class="label">手机号</span>
                <span class="value">{{ order.mobile }}</span>
              </div>
              <div class="item">
                <span class="label">证件号</span>
                <span class="value">{{ order.idCard }}</span>
              </div>
              <div class="item">
                <span class="label">公司</span>
                <span class="value">{{ order.company }}</span>
              </div>
              <div class="item">
                <span class="label">职务</span>
                <span class="value">{{ order.job }}</span>
              </div>
              <div class="item">
                <span class="label">拜访事由</span>
                <span class="value">{{ order.purpose }}</span>
              </div>
              <div class="operate">
                <a-button type="primary" style="float:right" @click="handleSignOut">登出</a-button>
              </div>
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import adapter from 'webrtc-adapter'

import ROUTE_PATH from '@/router/route-paths'

import { BrowserMultiFormatReader } from '@zxing/library'
import * as OrderService from '@/service/data/OrderService'

export default {
  data () {
    return {
      codeReader: new BrowserMultiFormatReader(),
      textContent: '',
      order: null
    }
  },
  async created () {
    this.codeReader.getVideoInputDevices()
      .then((videoInputDevices) => {
        this.videoList = videoInputDevices
        const selectedDeviceId = videoInputDevices[0].deviceId
        this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
          if (result) {
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
  methods: {
    async findOrder (id) {
      const order = await OrderService.get(id, {
        showLoading: false
      })
      this.order = order
    },
    handleSignOut () {
      const order = this.order
      const that = this
      this.$confirm({
        title: '确认信息',
        content: '确定登出当前访客信息吗？',
        onOk () {
          OrderService.singOut(order.id).then(res => {
            that.$router.push({ path: ROUTE_PATH.HOME_PATH })
          })
        },
        onCancel () { }
      })
    }

  }
}
</script>

<style scoped>
.container {
  position: relative;
  height: 100%;
}

.order-wrapper {
  height: 405px;
}

.order-wrapper .item {
  height: 50px;
  line-height: 50px;
}

.order-wrapper .item span {
  display: inline-block;
}

.order-wrapper .item .label {
  font-weight: bold;
  width: 100px;
}

.order-wrapper .operate {
  position: absolute;
  bottom: 20px;
  left: 20px;
  right: 20px;
}
</style>
