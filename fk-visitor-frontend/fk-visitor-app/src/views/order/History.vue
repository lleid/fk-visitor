<template>
  <div class="wrapper">
    <div class="form">
      <a-input-search placeholder="请输入手机号" enter-button @search="onSearch" />
      <div v-if="order.id" style="margin-top:20px">
        <a-row>
          <a-col span="12">
            <div class="user-info">
              <span class="label">姓名</span>
              <span class="info">{{ order.name }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">手机号</span>
              <span class="info">{{ order.mobile }}</span>
            </div>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <div class="user-info">
              <span class="label">身份证</span>
              <span class="info">{{ order.idCard }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">公司</span>
              <span class="info">{{ order.comppany }}</span>
            </div>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <div class="user-info">
              <span class="label">职务</span>
              <span class="info">{{ order.job }}</span>
            </div>
          </a-col>
          <a-col span="12">
            <div class="user-info">
              <span class="label">拜访事由</span>
              <span class="info">{{ order.purpose.cnName }}</span>
            </div>
          </a-col>
        </a-row>
        <a-row>
          <a-col span="12">
            <div class="user-info" v-if="order.operator">
              <span class="label">拜访人</span>
              <span class="info">{{ order.operator.name }}</span>
            </div>
          </a-col>
          <a-col span="12"></a-col>
        </a-row>
      </div>
    </div>
    <div class="operate" v-if="order.id">
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
import * as OrderService from '@/service/data/OrderService'
import FormConfig from '@/config/form.config'

export default {
  components: {
  },
  data () {
    return {
      ...FormConfig,
      order: {}
    }
  },
  created () {
  },
  methods: {
    async  onSearch (value) {
      const order = await OrderService.queryHistory({
        mobile: value
      })
      this.order = order
    },
    async handleConfirm () {
      await OrderService.history(this.order.id)
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
  bottom: 90px;
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
