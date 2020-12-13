<template>
  <c-modal
    title="订单详情"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-row>
      <a-col :md="12" :sm="24" :xs="24">
        <div class="form-item">
          <div class="label">姓名</div>
          <div class="value">{{ order.name }}</div>
        </div>
      </a-col>
      <a-col :md="12" :sm="24" :xs="24">
        <div class="form-item">
          <div class="label">手机</div>
          <div class="value">{{ order.mobile }}</div>
        </div>
      </a-col>
      <a-col :md="12" :sm="24" :xs="24">
        <div class="form-item">
          <div class="label">公司</div>
          <div class="value">{{ order.company }}</div>
        </div>
      </a-col>
      <a-col :md="12" :sm="24" :xs="24">
        <div class="form-item">
          <div class="label">职务</div>
          <div class="value">{{ order.title }}</div>
        </div>
      </a-col>
      <a-col :md="12" :sm="24" :xs="24" v-if="order.purpose">
        <div class="form-item">
          <div class="label">拜访事由</div>
          <div class="value">{{ order.purpose.cnName }}</div>
        </div>
      </a-col>
      <a-col :md="12" :sm="24" :xs="24" v-if="order.visitArea">
        <div class="form-item">
          <div class="label">参观区域</div>
          <div class="value">{{ order.visitArea.cnName }}</div>
        </div>
      </a-col>
      <a-col :md="12" :sm="24" :xs="24">
        <div class="form-item">
          <div class="label">受访人</div>
          <div class="value">{{ order.interviewer }}</div>
        </div>
      </a-col>
      <a-col :md="12" :sm="24" :xs="24">
        <div class="form-item">
          <div class="label">拜访时间</div>
          <div class="value">{{ order.visitAt }}</div>
        </div>
      </a-col>
      <a-col :md="12" :sm="24" :xs="24">
        <div class="form-item">
          <div class="label">是否签出</div>
          <div class="value">{{ order.isSignOut ?'是':'否' }}</div>
        </div>
      </a-col>
      <template v-if="order.isSignOut">
        <a-col :md="12" :sm="24" :xs="24">
          <div class="form-item">
            <div class="label">签出时间</div>
            <div class="value">{{ order.signOutAt }}</div>
          </div>
        </a-col>
        <a-col :md="12" :sm="24" :xs="24">
          <div class="form-item">
            <div class="label">签出类型</div>
            <div class="value">{{ order.signOutType==='10'?'自动':'手动' }}</div>
          </div>
        </a-col>
        <a-col :md="24" :sm="24" :xs="24">
          <div class="form-item">
            <div class="label">签出原因</div>
            <div class="value">{{ order.signOutReason }}</div>
          </div>
        </a-col>
      </template>
    </a-row>
  </c-modal>
</template>

<script>
import moment from 'moment'

import * as OrderService from '@/service/data/OrderService'

export default {
  data () {
    return {
      visible: false,
      loading: false,
      confirmLoading: false,
      order: {}
    }
  },
  methods: {
    moment,
    async show (record) {
      this.visible = true
      this.loading = true
      try {
        const result = await OrderService.get(record.id, { showLoading: false })
        this.order = result
      } catch (error) {
      }
      this.loading = false
    },
    handleSubmit () {
      this.visible = false
    },
    handleClose () {
      this.visible = false
    }
  }
}
</script>

<style lang="less">
.form-item {
  height: 40px;
  line-height: 40px;
  position: relative;
  padding-left: 120px;
  .label {
    overflow: hidden;
    width: 100px;
    white-space: nowrap;
    text-align: right;
    vertical-align: middle;
    position: absolute;
    left: 0;
    font-weight: bold;
  }

  .value {
    display: inline-block;
    height: 32px;
    padding: 4px 11px;
    color: rgba(0, 0, 0, 0.65);
    font-size: 14px;
    line-height: 1.5;
    width: 100%;
  }
}
</style>
