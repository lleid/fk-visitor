<template>
  <div class="wrapper">
    <div class="form">
      <div class="order-wrapper" v-if="order">
        <div class="order-avatar" :style="{backgroundImage:'url('+order.avatar+')'}"></div>
        <div class="order-info">
          <span class="param-key">姓名</span>
          <span class="param-value">{{ order.name }}</span>
        </div>
        <div class="order-info">
          <span class="param-key">拜访事由</span>
          <span class="param-value">{{ order.purpose.cnName }}</span>
        </div>
        <div class="order-qcode"></div>
      </div>
      <div class="order-printer">
        <a-icon type="printer" />
        倒计时 {{ state.time }} s，返回首页
      </div>
    </div>
  </div>
</template>

<script>
import ROUTE_PATH from '@/router/route-paths'
import * as OrderService from '@/service/data/OrderService'

export default {
  components: {
  },
  data () {
    return {
      state: {
        time: 5
      },
      order: undefined
    }
  },
  async created () {
    const orderId = this.$route.query.orderId

    const order = await OrderService.get(orderId)
    this.order = order

    const { state } = this
    const that = this
    const interval = window.setInterval(() => {
      if (state.time-- <= 0) {
        state.time = 5
        window.clearInterval(interval)
        that.$router.push({ path: ROUTE_PATH.HOME_PATH })
      }
    }, 1000)
  },
  methods: {
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

  .order-wrapper {
    width: 400px;
    height: 150px;
    position: absolute;
    left: 50%;
    top: 50%;
    margin-left: -200px;
    border: 1px solid #ccc;
    padding: 15px 15px 15px 150px;
    margin-top: -75px;

    .order-avatar {
      width: 120px;
      height: 120px;
      position: absolute;
      left: 15px;
      top: 15px;
      background-position: center;
      background-size: cover;
      background-repeat: no-repeat;
    }

    .order-info {
      height: 50px;
      line-height: 50px;
      padding-left: 24px;

      .param-key {
        font-weight: bold;
        padding-right: 24px;
      }
    }
  }

  .order-printer {
    position: absolute;
    bottom: 50px;
    text-align: center;
    left: 0;
    right: 0;
    font-size: 20px;
  }
}
</style>
