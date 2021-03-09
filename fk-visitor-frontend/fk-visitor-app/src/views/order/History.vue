<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <span @click="showDrawer">{{ msgItem.title }}</span>
        </div>
        <div class="step-wrapper">
          <keyboard
            :inputValue="mobile"
            :placeholder="msgItem.errorMsg1"
            :submitMsg="msgItem.btn"
            @add="handleAdd"
            @delete="handleDelete"
            @empty="handleEmpty"
            @submit="handleSubmit"
          ></keyboard>
        </div>
      </div>
    </div>
    <a-drawer
      title="今日访客"
      width="400"
      placement="right"
      :closable="false"
      :visible="visible"
      @close="handleDrawerClose"
    >
      <div class="order-wrapper">
        <div
          class="order-item"
          v-for="(item,index) in todayOrders"
          :key="index"
          @click="handleOrder(item.id)"
        >
          <div class="avatar">
            <img :src="item.avatar" />
          </div>
          <div class="info name">{{ item.name }}</div>
          <div class="info company">{{ item.company }}</div>
          <div class="info">{{ item.purpose.cnName }} / {{ item.visitArea.cnName }}</div>
          <div class="info">{{ item.interviewer }}</div>
          <div class="info">{{ item.visitAt }}</div>
        </div>
      </div>
    </a-drawer>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Keyboard from '@/components/Keyboard'

import ROUTE_PATH from '@/router/route-paths'
import * as OrderService from '@/service/data/OrderService'

const MsgCN = {
  title: '历史访客',
  btn: '确定',
  errorMsg1: '请输入您的手机号',
  errorMsg2: '无历史记录'
}

const MsgEN = {
  title: 'Visitors to the history',
  btn: 'Confirm',
  errorMsg1: 'Please input you phone number',
  errorMsg2: 'No history record'
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
      order: {},
      todayOrders: []
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
    }
  },
  async created () {
    const orders = await OrderService.queryToday({
      showSuccess: false, showFailure: false
    })
    this.todayOrders = orders
  },
  methods: {
    async handleSubmit () {
      if (this.mobile === '' || this.mobile === undefined) {
        this.$message.error(this.msgItem.errorMsg1)
        return false
      }
      this.order = {}
      const order = await OrderService.queryHistory({
        mobile: this.mobile
      }, { showLoading: false })
      this.order = order

      if (!order) {
        this.$message.error(this.msgItem.errorMsg2)
      } else {
        this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH, query: { orderId: this.order.id } })
      }
    },
    handleAdd (param) {
      if (this.mobile.length < 11) {
        this.mobile = this.mobile + param
      }
    },
    handleDelete () {
      if (this.mobile.length > 0) {
        this.mobile = this.mobile.substring(0, this.mobile.length - 1)
      }
    },
    handleEmpty () {
      this.mobile = ''
    },
    handleOrder (id) {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.SUCCESS_PATH, query: { orderId: id } })
    },
    async showDrawer () {
      this.visible = true
    },
    handleDrawerClose () {
      this.visible = false
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  position: relative;
  padding-bottom: 100px;
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
    padding-top: 24px;
  }
}

.order-wrapper {
  padding-bottom: 50px;
  .order-item {
    position: relative;
    padding-left: 150px;
    height: 120px;
    border-bottom: 1px dashed #ccc;
    margin-bottom: 24px;
    .avatar {
      position: absolute;
      left: 0;
      top: 0;
      width: 135px;
      img {
        width: 100%;
        height: 100%;
      }
    }

    .info {
      &.name {
        font-weight: bold;
      }
      &.company {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
  }
}
</style>
