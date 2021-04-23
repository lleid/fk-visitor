<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <span>{{ msg.tip }}</span>
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
        <span class="btn printer" @click="doPrint" v-show="isPrinter">打印</span>
      </div>
      <print ref="printer" :html-data="htmlData" :key="timer" v-show="false"></print>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import * as OrderService from '@/service/data/OrderService'
import { APP_MUTATIONS } from '@/store/modules/app-store'
import ROUTE_PATH from '@/router/route-paths'

import html2canvas from 'html2canvas'
import jsbarcode from 'jsbarcode'

import Print from '../../components/Printer/Print.vue'
import logger from '../../utils/LogUtils'

const lang = {
  cn: { tip: '标签打印' },
  en: { tip: 'Label printing' }
}

export default {
  components: {
    Print
  },
  data () {
    return {
      timer: '',
      isPrinter: true,
      htmlData: '',
      order: undefined,
      printerName: process.env.VUE_APP_PRINTER_NAME
    }
  },
  async created () {
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
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    msg () {
      if (this.language === 'EN') {
        return lang.en
      }
      return lang.cn
    }
  },
  methods: {
    doPrint () {
      const that = this
      that.isPrinter = false

      this.$confirm({
        title: '确认信息',
        content: '确定打印访客信息吗？',
        onOk () {
          that.$message.loading({
            content: '访客证打印中，请稍候...',
            duration: 5,
            onClose: function () {
              that.$router.push({ path: ROUTE_PATH.HOME_PATH })
              that.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, true)
            }
          })
          try {
            const imageWrapper = that.$refs.imageWrapper
            const printer = that.$refs.printer
            const printerName = that.printerName
            html2canvas(imageWrapper).then(canvas => {
              const dataURL = canvas.toDataURL('image/png')
              printer.print(dataURL, printerName)
            })
          } catch (e) {
            logger.error(e)
          }
        },
        onCancel () {
          that.isPrinter = true
        }
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

.printer {
  position: absolute;
  right: 80px;
  bottom: 80px;
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
</style>
