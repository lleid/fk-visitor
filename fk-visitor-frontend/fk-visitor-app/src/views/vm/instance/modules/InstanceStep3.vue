<template>
  <div style="max-width: 500px; margin: 40px auto 0;">
    <a-form-model-item
      label="基础网络"
      prop="networks"
    >
      <a-select v-model="networkId" :options="CSNetworkServiceSelect" @change="networkChange">
      </a-select>
    </a-form-model-item>
  </div>
</template>

<script>
import * as CSNetworkService from '@/service/vm/CSNetworkService'

export default {
  name: 'Step3',
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  created () {
    CSNetworkService.queryAll({ showLoading: false }).then(res => {
      this.CSNetworkServiceSelect = res.map(item => {
        return {
          label: item.name,
          value: item.id
        }
      })
    })
  },
  data () {
    return {
      labelCol: { lg: { span: 5 }, sm: { span: 5 } },
      wrapperCol: { lg: { span: 19 }, sm: { span: 19 } },
      CSNetworkServiceSelect: [],
      networkId: ''
    }
  },
  methods: {
    networkChange () {
      this.form.networks = [
        { id: this.networkId }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
.step-form-style-desc {
  padding: 0 56px;
  color: rgba(0,0,0,.45);

  h3 {
    margin: 0 0 12px;
    color: rgba(0,0,0,.45);
    font-size: 16px;
    line-height: 32px;
  }

  h4 {
    margin: 0 0 4px;
    color: rgba(0,0,0,.45);
    font-size: 14px;
    line-height: 22px;
  }

  p {
    margin-top: 0;
    margin-bottom: 12px;
    line-height: 22px;
  }
}
</style>
