<template>
  <div class="wrapper">
    <div class="form">
      <a-input-search placeholder="请输入邀请码" enter-button @search="onSearch" />
      <div v-if="appointment.id" style="margin-top:20px">
        <a-row>
          <a-col span="12">
            <div class="user-info">
              <span class="label">姓名</span>
              <span class="info">{{ appointment.name }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">手机号</span>
              <span class="info">{{ appointment.mobile }}</span>
            </div>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <div class="user-info">
              <span class="label">身份证</span>
              <span class="info">{{ appointment.idCard }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">公司</span>
              <span class="info">{{ appointment.comppany }}</span>
            </div>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <div class="user-info">
              <span class="label">职务</span>
              <span class="info">{{ appointment.job }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">拜访事由</span>
              <span class="info">{{ appointment.purpose.cnName }}</span>
            </div>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <div class="user-info" v-if="appointment.operator">
              <span class="label">拜访人</span>
              <span class="info">{{ appointment.operator.name }}</span>
            </div>
          </a-col>
          <a-col span="12"></a-col>
        </a-row>
      </div>
    </div>
    <div class="operate" v-if="appointment.id">
      <a-row>
        <a-col :span="20"></a-col>
        <a-col :span="4" style="text-align:right">
          <a-button type="primary" size="large" shape="round" @click="handleConfirm">确定</a-button>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import ROUTE_PATH from '@/router/route-paths'
import * as AppointmentService from '@/service/data/AppointmentService'
import FormConfig from '@/config/form.config'

export default {
  components: {
  },
  data () {
    return {
      ...FormConfig,
      appointment: {}
    }
  },
  created () {
  },
  methods: {
    async  onSearch (value) {
      const appointment = await AppointmentService.queryInviteCode({
        inviteCode: value
      })
      this.appointment = appointment
    },
    async handleConfirm () {
      await AppointmentService.visit(this.appointment.id)
      this.$router.push({ path: ROUTE_PATH.APP_PATH.SUCCESS_PATH })
    }
  }
}
</script>

<style scoped lang="less">
.wrapper {
  height: 100%;
}

.form {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 100px;
  background: #fff;
  padding: 24px;
}

.user-info {
  height: 45px;
  line-height: 45px;

  span {
    display: inline-block;

    &.label {
      font-weight: bold;
      width: 100px;
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
