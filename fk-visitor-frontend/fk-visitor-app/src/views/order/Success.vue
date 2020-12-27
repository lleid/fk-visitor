<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <span>{{ tipName.title }}</span>
        </div>
        <div class="step-wrapper">
          <div class="order-wrapper" v-if="order" ref="imageWrapper">
            <div class="order">
              <img class="avatar" :src="order.avatar" />
              <div class="info-wrapper">
                <div class="name">{{ order.name }}</div>
                <div class="company">{{ order.company }}</div>
                <template v-if="language === 'CN'">
                  <div class="visit">
                    <span class="purpose">{{ order.purpose.cnName }}</span>/
                    <span class="area">{{ order.visitArea.cnName }}</span>
                  </div>
                  <div class="interviewer">受访人： {{ order.interviewer }}</div>
                </template>
                <template v-else>
                  <div class="visit">
                    <span class="purpose">{{ order.purpose.enName }}</span>/
                    <span class="area">{{ order.visitArea.enName }}</span>
                  </div>
                  <div class="interviewer">People visited： {{ order.interviewer }}</div>
                </template>
                <div class="date">{{ order.visitAt }}</div>
              </div>
              <img id="barcode" class="barcode" />
            </div>
          </div>
        </div>
      </div>
      <!-- <img :src="htmlData" width="268" height="420" /> -->
      <print ref="printer" :html-data="htmlData" :key="timer" v-show="false"></print>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import * as OrderService from '@/service/data/OrderService'
import { APP_MUTATIONS } from '@/store/modules/app-store'

import html2canvas from 'html2canvas'
import jsbarcode from 'jsbarcode'
import Print from '../../components/Printer/Print.vue'

import ROUTE_PATH from '@/router/route-paths'
const TipCN = {
  title: '标签打印'
}

const TipEN = {
  title: 'Label printing'
}

export default {
  components: {
    Print
  },
  data () {
    return {
      timer: '',
      htmlData: '',
      order: undefined
    }
  },
  async created () {
    const that = this
    const orderId = this.$route.query.orderId

    const order = await OrderService.get(orderId, { showLoading: false })
    this.order = order

    setTimeout(() => {
      jsbarcode(
        '#barcode',
        orderId, {
        format: 'CODE128',
        width: 3,
        height: 30,
        displayValue: false
      }
      )
    }, 500)

    this.$message.loading({
      content: '访客证打印中，请稍候...',
      duration: 5,
      onClose: function () {
        that.$router.push({ path: ROUTE_PATH.HOME_PATH })
        that.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, true)
      }
    })

    setTimeout(() => {
      this.doPrint()
    }, 1000)
  },
  mounted () {

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
    doPrint () {
      html2canvas(this.$refs.imageWrapper).then(canvas => {
        const dataURL = canvas.toDataURL('image/png')
        this.htmlData = dataURL
        this.timer = new Date().getTime()
      })
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  position: relative;
  padding-bottom: 20px;
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
  width: 268px;
  height: 420px;
  position: absolute;
  left: 50%;
  top: 50%;
  margin-top: -210px;
  margin-left: -134px;
  border: 1px solid #eee;

  .order {
    position: relative;
    height: 100%;
    padding: 24px;

    .logo {
      width: 160px;
      position: absolute;
      top: 24px;
      left: 24px;
    }

    .avatar {
      display: block;
      width: 140px;
      height: 120px;
      background-position: center;
      background-size: cover;
      background-repeat: no-repeat;
      margin: 0 auto;
    }

    .barcode {
      display: block;
      margin: 0 auto;
      bottom: 24px;
      position: absolute;
      left: 0;
      right: 0;
    }

    .info-wrapper {
      margin-top: 12px;

      .name {
        height: 40px;
        line-height: 40px;
        text-align: center;
        font-size: 24px;
        font-weight: bold;
      }

      .company {
        height: 30px;
        line-height: 30px;
        text-align: center;
        font-size: 16px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        font-weight: bold;
      }

      .visit {
        text-align: center;
        margin-top: 6px;
        font-weight: bold;

        span {
          display: inline-block;
          padding: 0 5px;
        }
      }

      .interviewer {
        font-weight: bold;
        text-align: center;
        margin-top: 6px;
      }
      .date {
        margin-top: 6px;
        font-weight: bold;
        height: 30px;
        line-height: 30px;
        text-align: center;
      }
    }
  }
}

.loadding-wrapper {
  position: absolute;
  bottom: 100px;
  left: 50%;
  margin-left: -30px;
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
