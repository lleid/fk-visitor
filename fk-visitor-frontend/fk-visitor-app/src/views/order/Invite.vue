<template>
  <div class="wrapper">
    <div class="form">
      <a-form-model
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        ref="inviteCreate"
        :model="form"
        :rules="rules"
        :validate-messages="validateMessages"
      >
        <a-row>
          <a-col span="24">请输入邀请码</a-col>
        </a-row>
        <a-row>
          <a-col span="24">
            <a-form-model-item label="手机号" prop="mobile">
              <a-input v-model="form.mobile" :max-length="11" placeholder="请输入" />
            </a-form-model-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="24">
            <a-form-model-item label="邀请码" prop="idCard">
              <a-input v-model="form.idCard" :max-length="18" placeholder="请输入" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </div>
    <div class="operate">
      <a-row>
        <a-col :span="20"></a-col>
        <a-col :span="4" style="text-align:right">
          <a-button type="primary" size="large" shape="round" @click="handleConfirm">
            确定
            <a-icon type="right-circle" />
          </a-button>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import ROUTE_PATH from '@/router/route-paths'

import * as AppointmentService from '@/service/data/AppointmentService'
import FormConfig from '@/config/form.config'
import InviteRuleBuiler from './InviteRule'

export default {
  components: {
  },
  data () {
    return {
      ...FormConfig,
      labelCol: FormConfig.labelCol,
      wrapperCol: FormConfig.wrapperCol,
      validateMessage: FormConfig.validateMessages,
      form: {
        inviteCode: '',
        mobile: ''
      },
      rules: {}
    }
  },
  created () {
    this.rules = InviteRuleBuiler.build(this.form)
  },
  methods: {
    async  handleConfirm () {
      const appointment = await AppointmentService.query({
        mobile: this.form.mobile,
        inviteCode: this.form.inviteCode
      })

      if (appointment.lenght > 0) {
        this.$router.push({ path: ROUTE_PATH.HOME_PATH })
      }
    }
  }
}
</script>

<style scoped>
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

.operate {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>
