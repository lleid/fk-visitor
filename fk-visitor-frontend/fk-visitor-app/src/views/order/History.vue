<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <c-icon type="fv-yuan"></c-icon>
          <span>{{ msgItem.title }}</span>
        </div>
        <div class="keyboard">
          <div class="invite-code">123</div>
          <div class="item-list">
            <div class="item">
              <a class="button button-raised button-pill">1</a>
            </div>
            <div class="item">
              <a class="button button-raised button-pill">2</a>
            </div>
            <div class="item">
              <a class="button button-raised button-pill">3</a>
            </div>
            <div class="item">
              <a class="button button-raised button-pill">删除</a>
            </div>
          </div>
          <div class="item-list">
            <div class="item">
              <a class="button button-raised button-pill">4</a>
            </div>
            <div class="item">
              <a class="button button-raised button-pill">5</a>
            </div>
            <div class="item">
              <a class="button button-raised button-pill">5</a>
            </div>
            <div class="item">
              <a class="button button-raised button-pill">清空</a>
            </div>
          </div>
          <div class="item-list">
            <div class="item">
              <a class="button button-raised button-pill">7</a>
            </div>
            <div class="item">
              <a class="button button-raised button-pill">8</a>
            </div>
            <div class="item">
              <a class="button button-raised button-pill">9</a>
            </div>
            <div class="item">
              <a class="button button-raised button-pill">0</a>
            </div>
          </div>
          <div class="item-list">
            <a class="button button-primary button-pill button2 button-block button-large">确定</a>
          </div>
        </div>
        <div class="userinfo">
          <div class="qr-scanner">
            <div class="box">
              <div class="line"></div>
              <div class="angle"></div>
            </div>
          </div>
          <video ref="video" class="video" id="video" width="300"></video>
        </div>
        <div class="other">或</div>
      </div>
    </div>
    <div class="operate" v-if="order.id">
      <a-row>
        <a-col :span="20"></a-col>
        <a-col :span="4" style="text-align:right">
          <a class="button button-primary button-pill" @click="handleConfirm">{{ msgItem.btn }}</a>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
// eslint-disable-next-line no-unused-vars
import adapter from 'webrtc-adapter'
import { BrowserMultiFormatReader } from '@zxing/library'

import ROUTE_PATH from '@/router/route-paths'
import * as OrderService from '@/service/data/OrderService'
import FormConfig from '@/config/form.config'

const MsgCN = {
  title: '历史访客',
  btn: '确定',
  search: '搜索',
  placeholder: '请输入手机号',
  errorMsg1: '请输入手机号',
  errorMsg2: '无历史记录'
}

const MsgEN = {
  title: 'Visited',
  btn: 'Confirm',
  search: 'Search',
  placeholder: 'Please input you phone number',
  errorMsg1: 'Please input you phone number',
  errorMsg2: 'No history'
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
  components: {
  },
  data () {
    return {
      ...FormConfig,
      codeReader: new BrowserMultiFormatReader(),
      form: {},
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
    async onSearch () {
      if (this.form.mobile === '' || this.form.mobile === undefined) {
        this.$message.error(this.msgItem.errorMsg1)
        return
      }
      this.order = {}
      const order = await OrderService.queryHistory(this.form, { showLoading: false })
      this.order = order

      if (!order) {
        this.$message.error(this.msgItem.errorMsg2)
      }
    },
    async handleConfirm () {
      await OrderService.history(this.order.id, { showLoading: false, showSuccess: false })
      this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH, query: { orderId: this.order.id } })
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

.user-info {
  height: 45px;
  line-height: 45px;

  span {
    display: inline-block;

    &.label {
      font-weight: bold;
      width: 120px;
      font-size: 16px;
    }
  }
}

.operate {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}

.keyboard {
  width: 500px;
  /* height: 100%; */
  vertical-align: middle;
  height: 340px;
  position: absolute;
  top: 50%;
  margin-top: -170px;

  .invite-code {
    color: #0565aa;
    border: 1px solid #0565aa;
    padding: 5px 24px;
    height: 50px;
    line-height: 40px;
    border-radius: 8px;
    margin-bottom: 24px;
  }

  .item-list {
    margin-bottom: 24px;
  }

  .item {
    width: 25%;
    display: inline-block;
    padding: 0 5px;

    .button {
      width: 100%;
      font-weight: bold;
    }
  }
}

.userinfo {
  width: 300px;
  height: 225px;
  position: absolute;
  top: 50%;
  margin-top: -112px;
  right: 200px;

  .video {
  }

  .qr-scanner {
    width: 300px;
    height: 225px;
    position: absolute;
    top: 50%;
    margin-top: -112px;
  }
}

.other {
  position: absolute;
  font-weight: bold;
  top: 50%;
  left: 50%;
  margin-left: -12px;
  margin-top: -18px;
  font-size: 24px;
}
</style>
