<template>
  <div class="invite-container">
    <div class="invite-wrapper">
      <div class="invite-form">
        <div class="form-header">
          <span>{{ msg.title }}</span>
        </div>
        <div class="form-content">
          <keyboard
            :inputValue="inviteCode"
            :placeholder="msg.error1"
            :submitMsg="msg.btn"
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

const mes = {
  cn: {
    title: '受邀访客',
    btn: '确定',
    error1: '请输入您的邀请码',
    error2: '无该邀请码信息，请联系邀请人进行确认'
  },
  en: {
    title: 'Invited Visitor',
    btn: 'Confirm',
    error1: 'Please input you invitation code',
    error2: 'No invitation code information, please contact the inviter for confirmation'
  }
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
    msg () {
     return this.language === 'EN' ? mes.en : mes.cn
    }
  },
  methods: {
    async handleSubmit () {
      if (this.inviteCode === '' || this.inviteCode === undefined) {
        this.$message.error(this.msg.error1)
        return false
      }
      const appointment = await AppointmentService.queryInviteCode({ inviteCode: this.inviteCode }, { showLoading: false })
      this.appointment = appointment
      if (!appointment) {
        this.$message.error(this.msg.error2)
      } else {
        if (appointment.isTeam) {
          this.$router.push({ path: ROUTE_PATH.APP_PATH.TEAM_PATH, query: { teamId: this.appointment.teamId } })
        } else {
          this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH, query: { appointmentId: this.appointment.id } })
        }
      }
    },
    handleAdd (param) {
      if (this.inviteCode.length < 6) this.inviteCode = this.inviteCode + param
    },
    handleDelete () {
      if (this.inviteCode.length > 0) this.inviteCode = this.inviteCode.substring(0, this.inviteCode.length - 1)
    },
    handleEmpty () {
      this.inviteCode = ''
    }
  }
}
</script>

<style scoped lang="less">
.invite-container {
  height: 100%;
  position: relative;
  padding-bottom: 100px;

  .invite-wrapper {
    background: url(../assets/background.png);
    padding: 10px;
    height: 100%;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
    background-size: cover;
  }

  .invite-form {
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
      padding-top: 24px;
    }
  }
}
</style>
