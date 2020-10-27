<template>
  <div class="container">
    <div class="form">
      <a-input-search :placeholder="msgItem.placeholder" enter-button @search="onSearch" />
      <div v-if="appointment.id" style="margin-top:20px">
        <a-row>
          <a-col span="12">
            <div class="user-info">
              <span class="label">{{ formItem.item1 }}</span>
              <span class="info">{{ appointment.name }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">{{ formItem.item2 }}</span>
              <span class="info">{{ appointment.mobile }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">{{ formItem.item3 }}</span>
              <span class="info">{{ appointment.idCard }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">{{ formItem.item4 }}</span>
              <span class="info">{{ appointment.company }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">{{ formItem.item5 }}</span>
              <span class="info">{{ appointment.job }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info" v-if="appointment.operator">
              <span class="label">{{ formItem.item6 }}</span>
              <span class="info">{{ appointment.operator.name }}</span>
            </div>
          </a-col>
          <a-col span="12" v-if="appointment.purpose">
            <div class="user-info">
              <span class="label">{{ formItem.item7 }}</span>
              <span class="info" v-if="language === 'CN'">{{ appointment.purpose.cnName }}</span>
              <span class="info" v-else>{{ appointment.purpose.enName }}</span>
            </div>
          </a-col>
          <a-col span="12" v-if="appointment.visitArea">
            <div class="user-info">
              <span class="label">{{ formItem.item8 }}</span>
              <span class="info" v-if="language === 'CN'">{{ appointment.visitArea.cnName }}</span>
              <span class="info" v-else>{{ appointment.visitArea.enName }}</span>
            </div>
          </a-col>
        </a-row>
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
import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import * as AppointmentService from '@/service/data/AppointmentService'
import FormConfig from '@/config/form.config'

const MsgCN = {
  btn: '确定',
  placeholder: '请输入您的邀请码',
  errorMsg1: '请输入您的邀请码',
  errorMsg2: '无该邀请码信息，请联系邀请人进行确认'
}

const MsgEN = {
  btn: 'Confirm',
  placeholder: 'Please input you invitation code',
  errorMsg1: 'Please input you invitation code',
  errorMsg2: 'No invitation code information, please contact the inviter for confirmation'
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
  item6: 'Interviewee name',
  item7: 'Visit purpose',
  item8: 'Content'
}

export default {
  components: {
  },
  data () {
    return {
      ...FormConfig,
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
  methods: {
    async onSearch (value) {
      if (value) {
        const appointment = await AppointmentService.queryInviteCode({
          inviteCode: value
        }, { showLoading: false })
        this.appointment = appointment
        if (!appointment) {
          this.$message.error(this.msgItem.errorMsg2)
        }
      } else {
        this.$message.error(this.msgItem.errorMsg1)
      }
    },
    async handleConfirm () {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.appointment_PATH, query: { appointmentId: this.appointment.id } })
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

.form {
  padding: 24px;
  height: 100%;
  overflow: auto;
  background: #fff;
}

.user-info {
  height: 45px;
  line-height: 45px;

  span {
    display: inline-block;

    &.label {
      font-weight: bold;
      width: 100px;
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
</style>
