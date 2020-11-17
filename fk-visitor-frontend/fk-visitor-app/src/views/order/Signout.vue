<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <c-icon type="fv-yuan"></c-icon>
          <span>{{ msgItem.title }}</span>
        </div>
        <div class="video">
          <video ref="video" id="video" width="400"></video>
          <div class="wrapper"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import adapter from 'webrtc-adapter'
import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'

import { BrowserMultiFormatReader } from '@zxing/library'
import * as OrderService from '@/service/data/OrderService'

const MsgCN = {
  title: '签出',
  btn: '确定',
  confirmMsg: '确定签出当前访客信息吗？',
  content: '请输入手机号'
}

const MsgEN = {
  title: 'Sign out',
  btn: 'Confirm',
  confirmMsg: 'Confirmation message',
  content: 'Are you sure to check out the current visitor information?'
}

const FormCN = {
  item1: '您的姓名',
  item2: '联系方式',
  item3: '您的证件',
  item4: '您的公司',
  item5: '您的职务',
  item6: '受访人姓名',
  item7: '拜访事由',
  item8: '拜访区域'
}

const FormEN = {
  item1: 'Name',
  item2: 'Contact',
  item3: 'ID Card',
  item4: 'Company',
  item5: 'Post',
  item6: 'Interviewer',
  item7: 'Visit purpose',
  item8: 'Content'
}

export default {
  data () {
    return {
      codeReader: new BrowserMultiFormatReader(),
      textContent: '',
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
    },
    formItem () {
      if (this.language === 'EN') {
        return FormEN
      }
      return FormCN
    }
  },
  async created () {
    this.codeReader.getVideoInputDevices()
      .then((videoInputDevices) => {
        this.videoList = videoInputDevices
        const selectedDeviceId = videoInputDevices[0].deviceId
        this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
          console.log(result)
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
        content: '确定签出当前访客信息吗？',
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

.form .video {
  width: 400px;
  height: 225px;
  position: fixed;
  left: 25%;
  top: 50%;
  margin-left: -150px;
  margin-top: -112px;
}

div.wrapper {
  width: 400px;
  height: 320px;
  position: absolute;
  top: 0;
  background: linear-gradient(#1a98ca, #1a98ca), linear-gradient(90deg, #ffffff33 1px, transparent 0, transparent 19px),
    linear-gradient(#ffffff33 1px, transparent 0, transparent 19px), linear-gradient(transparent, #1a98ca);
  background-size: 100% 1.5%, 10% 100%, 100% 8%, 100% 100%;
  background-repeat: no-repeat, repeat, repeat, no-repeat;
  background-position: 0% 0%, 0 0, 0 0, 0 0;
  /* 初始位置 */
  clip-path: polygon(0% 0%, 100% 0%, 100% 1.5%, 0% 1.5%);
  /* 添加动画效果 */
  animation: move 2s infinite linear;
}

@keyframes move {
  to {
    background-position: 0 100%, 0 0, 0 0, 0 0;
    /* 终止位置 */
    clip-path: polygon(0% 0%, 100% 0%, 100% 100%, 0% 100%);
  }
}
</style>
