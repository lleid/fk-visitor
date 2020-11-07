<template>
  <div class="container">
    <div class="form">
      <div class="steps">
        <c-icon type="fv-yuan"></c-icon>
        <span>{{ msgItem.title }}</span>
      </div>
      <div class="video">
        <video ref="video" id="video" width="400"></video>
      </div>
      <div class="take">
        <c-icon type="fv-erweima"></c-icon>
      </div>
      <div class="order">
        <div class="order-info" v-if="order.id">
          <div class="item">
            <span class="label">{{ formItem.item1 }}</span>
            <span class="value">{{ order.name }}</span>
          </div>
          <div class="item">
            <span class="label">{{ formItem.item2 }}</span>
            <span class="value">{{ order.mobile }}</span>
          </div>
          <div class="item">
            <span class="label">{{ formItem.item3 }}</span>
            <span class="value">{{ order.idCard }}</span>
          </div>
          <div class="item">
            <span class="label">{{ formItem.item4 }}</span>
            <span class="value">{{ order.company }}</span>
          </div>
          <div class="item">
            <span class="label">{{ formItem.item5 }}</span>
            <span class="value">{{ order.job }}</span>
          </div>
          <div class="item">
            <span class="label">{{ formItem.item6 }}</span>
            <span class="value">{{ order.interviewer }}</span>
          </div>
          <div class="item" v-if="order.purpose">
            <span class="label">{{ formItem.item7 }}</span>
            <span class="value" v-if="language === 'CN'">{{ order.purpose.cnName }}</span>
            <span class="value" v-else>{{ order.purpose.enName }}</span>
          </div>
          <div class="item" v-if="order.visitArea">
            <span class="label">{{ formItem.item8 }}</span>
            <span class="value" v-if="language === 'CN'">{{ order.visitArea.cnName }}</span>
            <span class="value" v-else>{{ order.visitArea.enName }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="operate" v-if="order.id">
      <a-col :span="20"></a-col>
      <a-col :span="4" style="text-align:right">
        <a
          class="button button-3d button-primary button-pill"
          @click="handleSignOut"
        >{{ msgItem.btn }}</a>
      </a-col>
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
  padding-top: 80px;
  position: relative;
  padding-bottom: 80px;
}

.form {
  .steps {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 80px;
    line-height: 80px;
    font-size: 24px;
    font-weight: bold;
    border-bottom: 1px solid #0565aa;
    background: #fff;
    padding: 0 24px;
    color: #003b86;

    i {
      margin-right: 12px;
    }

    span {
    }
  }

  padding: 8px 24px;
  height: 100%;
  overflow: auto;
  background: #fff;
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

.form .take {
  height: 100px;
  width: 100px;
  line-height: 100px;
  position: fixed;
  left: 50%;
  top: 50%;
  margin-top: -50px;
  margin-left: -50px;
  text-align: center;
  border: 1px solid #1090dd;
  border-radius: 50%;
  font-size: 60px;
  color: #ffffff;
  background: #1090dd;
}

.form .order {
  width: 400px;
  height: 400px;
  position: fixed;
  right: 25%;
  top: 50%;
  margin-right: -200px;
  margin-top: -200px;
  border: 1px solid #1090dd;
  padding: 24px;
}

.order .item {
  height: 50px;
  line-height: 50px;
}

.order .item span {
  display: inline-block;
}

.order .item .label {
  font-weight: bold;
  width: 120px;
  font-size: 16px;
}

.operate {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>
