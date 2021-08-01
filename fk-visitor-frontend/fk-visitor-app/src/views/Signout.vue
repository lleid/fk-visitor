<template>
  <div class="singout-container">
    <div class="signout-wrapper">
      <div class="signout-form">
        <div class="form-header">
          <span>{{ msg.title }}</span>
        </div>
        <div class="form-content">
          <div class="scanner-wrapper">
            <a-input class="scanner" ref="scanner" v-model="orderNo" :placeholder="msg.tips" @keyup.enter.native="handleSignOut"></a-input>
          </div>
          <img class="scanner-image" src="~@/assets/scanner.png" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { APP_MUTATIONS } from '@/store/modules/app-store'
import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import * as OrderService from '@/service/data/OrderService'

const lang = {
  cn: {
    title: '签出',
    btn: '确定',
    tips: '请扫描访客证上的条形码'
  },
  en: {
    title: 'Sign out',
    btn: 'Confirm',
    tips: 'Please scan the bar code on the visitor\'s pass'
  }
}

export default {
  data () {
    return {
      orderNo: ''
    }
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    msg () {
      return this.language === 'EN' ? lang.en : lang.cn
    }
  },
  mounted () {
    this.$refs['scanner'].focus()
  },
  methods: {
    async handleSignOut () {
      const that = this
      if (that.orderNo) {
        that.$confirm({
          title: '确认信息',
          content: '确定签出当前访客信息吗？',
          onOk () {
            OrderService.singOut(that.orderNo).then(res => {
              if (res) {
                that.$router.push({ path: ROUTE_PATH.HOME_PATH })
                that.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, true)
              }
            })
            that.$destroyAll()
          },
          onCancel () {
            that.orderNo = ''
          }
        })
      }
    }
  }
}
</script>

<style scoped lang="less">
.singout-container {
  height: 100%;
  position: relative;
  padding-bottom: 100px;

  .signout-wrapper {
    background: url(../assets/background.png);
    padding: 10px;
    height: 100%;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
    background-size: cover;

    .signout-form {
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

        .scanner-wrapper {
          width: 400px;
          margin: auto auto;
          vertical-align: middle;
          margin-top: 24px;

          .scanner {
            color: #013b84;
            border: 2px solid #013b84;
            padding: 5px 24px;
            height: 50px;
            line-height: 40px;
            border-radius: 8px;
            margin-bottom: 34px;
          }
        }

        .scanner-image {
          margin: 0 auto;
          width: 400px;
          display: block;
          height: 300px;
        }
      }
    }
  }
}
</style>
