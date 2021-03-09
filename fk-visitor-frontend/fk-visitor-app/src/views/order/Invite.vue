<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <span>{{ msgItem.title }}</span>
        </div>
        <div class="step-wrapper">
          <keyboard
            :inputValue="inviteCode"
            :placeholder="msgItem.errorMsg1"
            :submitMsg="msgItem.btn"
            @add="handleAdd"
            @delete="handleDelete"
            @empty="handleEmpty"
            @submit="handleSubmit"
          ></keyboard>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Keyboard from '@/components/Keyboard'

import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import * as AppointmentService from '@/service/data/AppointmentService'

const MsgCN = {
  title: '受邀访客',
  btn: '确定',
  errorMsg1: '请输入您的邀请码',
  errorMsg2: '无该邀请码信息，请联系邀请人进行确认'
}

const MsgEN = {
  title: 'Invited Visitor',
  btn: 'Confirm',
  errorMsg1: 'Please input you invitation code',
  errorMsg2: 'No invitation code information, please contact the inviter for confirmation'
}

export default {
  components: {
    Keyboard
  },
  data () {
    return {
      inviteCode: '',
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
    }
  },
  async created () {
  },
  methods: {
    async handleSubmit () {
      if (this.inviteCode === '' || this.inviteCode === undefined) {
        this.$message.error(this.msgItem.errorMsg1)
        return false
      }
      const appointment = await AppointmentService.queryInviteCode({ inviteCode: this.inviteCode }, { showLoading: false })
      this.appointment = appointment
      if (!appointment) {
        this.$message.error(this.msgItem.errorMsg2)
      } else {
        if (appointment.isTeam) {
          this.$router.push({ path: ROUTE_PATH.APP_PATH.TEAM_PATH, query: { teamId: this.appointment.teamId } })
        } else {
          this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH, query: { appointmentId: this.appointment.id } })
        }
      }
    },
    handleAdd (param) {
      if (this.inviteCode.length < 6) {
        this.inviteCode = this.inviteCode + param
      }
    },
    handleDelete () {
      if (this.inviteCode.length > 0) {
        this.inviteCode = this.inviteCode.substring(0, this.inviteCode.length - 1)
      }
    },
    handleEmpty () {
      this.inviteCode = ''
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
    padding-top: 24px;
  }
}
</style>
