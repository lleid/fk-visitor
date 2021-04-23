<template>
  <div class="histor-container">
    <div class="history-wrapper">
      <div class="history-form">
        <div class="form-header" @click="handleDrawer">
          <span>{{ msg.title }}</span>
        </div>
        <div class="form-content">
          <keyboard :inputValue="mobile" :placeholder="msg.error1" :submitMsg="msg.btn" @change="mobile=$event" @submit="handleSubmit"></keyboard>
        </div>
      </div>
    </div>
    <a-drawer title="今日访客" width="400" placement="right" :closable="true" :visible="visible" @close="handleDrawerClose">
      <div class="order" v-for="(item,index) in todayOrders" :key="index" @click="handleOrder(item.id)">
        <div class="avatar">
          <img :src="item.avatar" />
        </div>
        <div class="info name">{{ item.name }}</div>
        <div class="info company">{{ item.company }}</div>
        <div class="info">{{ item.purpose.cnName }} / {{ item.visitArea.cnName }}</div>
        <div class="info">{{ item.interviewer }}</div>
        <div class="info">{{ item.visitAt }}</div>
      </div>
    </a-drawer>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Keyboard from '@/components/Keyboard'

import ROUTE_PATH from '@/router/route-paths'
import * as OrderService from '@/service/data/OrderService'

const lang = {
  cn: {
    title: '历史访客',
    btn: '确定',
    error1: '请输入您的手机号',
    error2: '该手机号无拜访记录'
  },
  en: {
    title: 'Visitors to the history',
    btn: 'Confirm',
    error1: 'Please input you phone number',
    error2: 'This phone number has no call record'
  }
}

export default {
  components: {
    Keyboard
  },
  data () {
    return {
      visible: false,
      form: {},
      mobile: '',
      todayOrders: []
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
  methods: {
    async handleSubmit () {
      if (this.mobile === '' || this.mobile === undefined) {
        this.$message.error(this.msg.error1)
        return false
      }
      const order = await OrderService.queryHistory({ mobile: this.mobile }, { showLoading: false })
      if (!order) {
        this.$message.error(this.msg.error2)
      } else {
        this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH, query: { orderId: order.id } })
      }
    },
    handleOrder (id) {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.SUCCESS_PATH, query: { orderId: id } })
    },
    handleDrawer () {
      this.visible = true
      OrderService.queryToday({ showSuccess: false, showFailure: false }).then(res => {
        this.todayOrders = res
      })
    },
    handleDrawerClose () {
      this.visible = false
    }
  }
}
</script>

<style scoped lang="less">
.histor-container {
  height: 100%;
  position: relative;
  padding-bottom: 100px;

  .history-wrapper {
    background: url(../assets/background.png);
    padding: 10px;
    height: 100%;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
    background-size: cover;

    .history-form {
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
}

.order {
  position: relative;
  padding: 7px 0px;
  padding-left: 160px;
  height: 120px;
  box-shadow: 0 0 10px;
  border-radius: 8px;
  font-size: 12px;
  margin-bottom: 24px;

  .avatar {
    position: absolute;
    left: 7.5px;
    top: 7.5px;
    width: 135px;

    img {
      width: 100%;
      height: 100%;
    }
  }

  .info {
    &.name {
      font-weight: bold;
      font-size: 22px;
    }

    &.company {
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
}
</style>
