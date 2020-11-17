<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <c-icon type="fv-yuan"></c-icon>
          <span>{{ msgItem.title }}</span>
        </div>
        <div>
          <a-input-group>
            <a-row :gutter="10">
              <a-col :span="20">
                <a-input :placeholder="msgItem.placeholder" v-model="form.mobile" />
              </a-col>
              <a-col :span="4">
                <a-button type="primary" icon="search" @click="onSearch">{{ msgItem.search }}</a-button>
              </a-col>
            </a-row>
          </a-input-group>
        </div>
        <div v-if="order.id" style="margin-top:20px">
          <a-row>
            <a-col span="12">
              <div class="user-info">
                <span class="label">{{ formItem.item1 }}</span>
                <span class="info">{{ order.name }}</span>
              </div>
            </a-col>
            <a-col span="12">
              <div class="user-info">
                <span class="label">{{ formItem.item2 }}</span>
                <span class="info">{{ order.mobile }}</span>
              </div>
            </a-col>
            <a-col span="12">
              <div class="user-info">
                <span class="label">{{ formItem.item3 }}</span>
                <span class="info">{{ order.idCard }}</span>
              </div>
            </a-col>
            <a-col span="12">
              <div class="user-info">
                <span class="label">{{ formItem.item4 }}</span>
                <span class="info">{{ order.company }}</span>
              </div>
            </a-col>
            <a-col span="12">
              <div class="user-info">
                <span class="label">{{ formItem.item5 }}</span>
                <span class="info">{{ order.job }}</span>
              </div>
            </a-col>
            <a-col span="12">
              <div class="user-info">
                <span class="label">{{ formItem.item6 }}</span>
                <span class="info">{{ order.interviewer }}</span>
              </div>
            </a-col>
            <a-col span="12" v-if="order.purpose">
              <div class="user-info">
                <span class="label">{{ formItem.item7 }}</span>
                <span class="info" v-if="language === 'CN'">{{ order.purpose.cnName }}</span>
                <span class="info" v-else>{{ order.purpose.enName }}</span>
              </div>
            </a-col>
            <a-col span="12" v-if="order.visitArea">
              <div class="user-info">
                <span class="label">{{ formItem.item8 }}</span>
                <span class="info" v-if="language === 'CN'">{{ order.visitArea.cnName }}</span>
                <span class="info" v-else>{{ order.visitArea.enName }}</span>
              </div>
            </a-col>
          </a-row>
        </div>
      </div>
    </div>
    <div class="operate" v-if="order.id">
      <a-row>
        <a-col :span="20"></a-col>
        <a-col :span="4" style="text-align:right">
          <a class="button button-primary button-pill" @click="handleConfirm">{{ msgItem.btn }}</a>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import * as OrderService from '@/service/data/OrderService'
import FormConfig from '@/config/form.config'

const MsgCN = {
  title: '以前来过',
  btn: '确定',
  search: '搜索',
  placeholder: '请输入手机号',
  errorMsg1: '请输入手机号',
  errorMsg2: '无历史记录'
}

const MsgEN = {
  title: 'Visited',
  btn: 'Confirm',
  search: 'Search',
  placeholder: 'Please input you phone number',
  errorMsg1: 'Please input you phone number',
  errorMsg2: 'No history'
}

const FormCN = {
  item1: '您的姓名',
  item2: '联系方式',
  item3: '您的证件',
  item4: '您的公司',
  item5: '您的职务',
  item6: '受访人姓名',
  item7: '拜访事由',
  item8: '拜访区域'
}

const FormEN = {
  item1: 'Name',
  item2: 'Contact',
  item3: 'ID Card',
  item4: 'Company',
  item5: 'Post',
  item6: 'Interviewer',
  item7: 'Visit purpose',
  item8: 'Content'
}

export default {
  components: {
  },
  data () {
    return {
      ...FormConfig,
      form: {},
      order: {}
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
    },
    formItem () {
      if (this.language === 'EN') {
        return FormEN
      }
      return FormCN
    }
  },
  methods: {
    async onSearch () {
      if (this.form.mobile === '' || this.form.mobile === undefined) {
        this.$message.error(this.msgItem.errorMsg1)
        return
      }
      this.order = {}
      const order = await OrderService.queryHistory(this.form, { showLoading: false })
      this.order = order

      if (!order) {
        this.$message.error(this.msgItem.errorMsg2)
      }
    },
    async handleConfirm () {
      await OrderService.history(this.order.id, { showLoading: false, showSuccess: false })
      this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH, query: { orderId: this.order.id } })
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  position: relative;
  padding-bottom: 80px;
}

.wrapper {
  background: url(../../assets/background.png);
  padding: 10px;
  height: 100%;
  box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
  background-size: cover;
}

.form {
  padding: 24px;
  height: 100%;
  overflow: auto;
  background: #fff;
  position: relative;
  padding-top: 80px;

  .steps {
    height: 60px;
    line-height: 60px;
    font-size: 18px;
    font-weight: bold;
    border-bottom: 1px solid #0565aa;
    background: #fff;
    color: #003b86;
    position: absolute;
    top: 0;
    left: 24px;
    right: 24px;

    i {
      margin-right: 12px;
    }
  }
}

.user-info {
  height: 45px;
  line-height: 45px;

  span {
    display: inline-block;

    &.label {
      font-weight: bold;
      width: 120px;
      font-size: 16px;
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
