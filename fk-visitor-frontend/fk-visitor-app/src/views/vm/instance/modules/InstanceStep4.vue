<template>
  <div style="max-width: 500px; margin: 40px auto 0;">
    <a-form-model-item
      label="主机名称"
      prop="name"
    >
      <a-input v-model="form.name" />
    </a-form-model-item>
    <a-form-model-item
      label="SSH秘钥"
      prop="keypair.id"
    >
      <a-select v-model="form.keypair.id" :options="keypairSelect">
      </a-select>
    </a-form-model-item>
  </div>
</template>

<script>
import * as SSHKeypairService from '@/service/vm/SSHKeypairService'

export default {
  name: 'Step4',
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  created () {
    SSHKeypairService.queryAll({ showLoading: false }).then(res => {
      this.keypairSelect = res.map(item => {
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
      keypairSelect: []
    }
  },
  methods: {
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
