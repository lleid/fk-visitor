<template>
  <div class="container">
    <div class="form">
      <div class="steps">
        <span>{{ tipName.title }}</span>
      </div>
      <div class="step-wrapper">
        <div class="order-wrapper" v-if="order">
          <div class="order">
            <img class="logo" src="~@/assets/logo.png" />
            <div class="avatar" :style="{backgroundImage:'url('+order.avatar+')'}"></div>
            <div class="info-wrapper">
              <div class="name">{{ order.name }}</div>
              <div class="company">{{ order.company }}</div>
              <div class="date">{{ order.visitAt }}</div>
            </div>
            <div class="qrcode" id="qrcode" ref="qrcode"></div>
          </div>
        </div>
        <div id="loadding-wrapper" class="loadding-wrapper">
          <div class="loading"></div>
          <div class="loading"></div>
          <div class="loading"></div>
          <div class="loading"></div>
          <div class="loading"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import * as OrderService from '@/service/data/OrderService'
import QRCode from 'qrcodejs2'
// import ROUTE_PATH from '@/router/route-paths'
const TipCN = {
  title: '标签打印',
  item1: '打印中，倒计时',
  item3: 's，返回首页'
}

const TipEN = {
  title: 'Label printing',
  item1: 'In print, countdown',
  item3: 's，back homepage '
}

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

    setTimeout(() => {
      const qrcode = new QRCode('qrcode', {
        text: orderId,
        width: 40, // 图像宽度
        height: 40 // 图像高度
      })
      qrcode.clear() // 清除二维码
      qrcode.makeCode(orderId) // 生成另一个新的二维码
    }, 500)

    // setTimeout(() => {
    //   this.$router.push({ path: ROUTE_PATH.HOME_PATH })
    // }, 10000)
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    tipName () {
      if (this.language === 'EN') {
        return TipEN
      }
      return TipCN
    }
  },
  methods: {
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

.order-wrapper {
  width: 420px;
  height: 268px;
  position: absolute;
  left: 50%;
  top: 50%;
  margin-top: -134px;
  margin-left: -210px;
  border: 1px solid #eee;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.2);

  .order {
    position: relative;
    padding-left: 170px;
    height: 100%;
    padding-top: 90px;
    padding-right: 12px;
    padding-bottom: 12px;

    .logo {
      width: 160px;
      position: absolute;
      top: 12px;
      left: 12px;
    }

    .avatar {
      width: 140px;
      height: 160px;
      position: absolute;
      left: 15px;
      top: 90px;
      bottom: 1;
      background-position: center;
      background-size: cover;
      background-repeat: no-repeat;
    }

    .qrcode {
      position: absolute;
      right: 12px;
      bottom: 12px;
    }

    .name {
      height: 50px;
      line-height: 50px;
      text-align: right;
      font-size: 24px;
      font-weight: bold;
    }

    .company {
      height: 35px;
      line-height: 35px;
      text-align: right;
      font-size: 14px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .date {
      height: 35px;
      line-height: 35px;
      float: right;
    }
  }
}

.loadding-wrapper {
  position: absolute;
  bottom: 100px;
  left: 50%;
  margin-left: -30px;
  /* right: 0; */
  text-align: center;
}
.loading {
  width: 6px;
  height: 10px;
  background: #68b2ce;
  float: left;
  margin: 0 3px;
  animation: loading linear 1s infinite;
  -webkit-animation: loading linear 1s infinite;
}
.loading:nth-child(1) {
  animation-delay: 0s;
}
.loading:nth-child(2) {
  animation-delay: 0.15s;
}
.loading:nth-child(3) {
  animation-delay: 0.3s;
}
.loading:nth-child(4) {
  animation-delay: 0.45s;
}
.loading:nth-child(5) {
  animation-delay: 0.6s;
}
@keyframes loading {
  0%,
  60%,
  100% {
    transform: scale(1);
  }
  30% {
    transform: scaleY(3);
  }
}
@-webkit-keyframes loading {
  0%,
  60%,
  100% {
    transform: scale(1);
  }
  30% {
    transform: scaleY(3);
  }
}
</style>
