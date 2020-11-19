<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <span>{{ msgItem.title }}</span>
        </div>
        <div class="step-wrapper">
          <div class="keyboard">
            <div class="invite-code">{{ visitCode }}</div>
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
                <a class="keyboard-btn" @click="handleDelete()">删除</a>
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
                <a class="keyboard-btn" @click="handleEmpty()">清空</a>
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
              <a class="btn btn-primary btn-fill" @click="handleSubmit">确定</a>
            </div>
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
    <div class="operate" v-if="appointment.id">
      <a-row>
        <a-col :span="20"></a-col>
        <a-col :span="4" style="text-align:right">
          <a-button
            type="primary"
            size="large"
            shape="round"
            @click="handleConfirm"
          >{{ msgItem.btn }}</a-button>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import adapter from 'webrtc-adapter'
import { BrowserMultiFormatReader } from '@zxing/library'

import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import * as AppointmentService from '@/service/data/AppointmentService'
import FormConfig from '@/config/form.config'

const MsgCN = {
  title: '请输入手机号查询',
  btn: '确定',
  search: '搜索',
  placeholder1: '请输入您的手机号',
  placeholder2: '请输入您的邀请码',
  errorMsg1: '请输入您的手机号',
  errorMsg2: '请输入您的邀请码',
  errorMsg3: '无该邀请码信息，请联系邀请人进行确认'
}

const MsgEN = {
  title: 'Please input you invitation code',
  btn: 'Confirm',
  search: 'Search',
  placeholder1: 'Please input you phone',
  placeholder2: 'Please input you invitation code',
  errorMsg1: 'Please input you phone',
  errorMsg2: 'Please input you invitation code',
  errorMsg3: 'No invitation code information, please contact the inviter for confirmation'
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
      form: {},
      visitCode: '',
      codeReader: new BrowserMultiFormatReader(),
      appointment: {}
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
    async handleSubmit () {
      if (this.inviteCode === '' || this.inviteCode === undefined) {
        this.$message.error(this.msgItem.errorMsg2)
        return false
      }
      const appointment = await AppointmentService.queryInviteCode({ inviteCode: this.inviteCode }, { showLoading: false })
      this.appointment = appointment
      if (!appointment) {
        this.$message.error(this.msgItem.errorMsg3)
      }
    },
    async handleConfirm () {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH, query: { appointmentId: this.appointment.id } })
    },
    handleAdd (param) {
      if (this.visitCode.length < 6) {
        this.visitCode = this.visitCode + param
      }
    },
    handleDelete () {
      if (this.visitCode.length > 0) {
        this.visitCode = this.visitCode.substring(0, this.visitCode.length - 1)
      }
    },
    handleEmpty () {
      this.visitCode = ''
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  position: relative;
  padding-bottom: 120px;
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

.operate {
  position: absolute;
  bottom: 40px;
  left: 0;
  right: 0;
}

.keyboard {
  width: 400px;
  vertical-align: middle;
  height: 340px;
  position: absolute;
  top: 50%;
  margin-top: -170px;

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
      height: 35px;
      line-height: 35px;
      border: 2px solid #013b84;
      display: block;
      text-align: center;
      border-radius: 8px;
      color: #013b84;
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
