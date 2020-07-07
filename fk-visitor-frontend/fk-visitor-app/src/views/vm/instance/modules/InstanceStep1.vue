<template>
  <div style="max-width: 500px; margin: 40px auto 0;">
    <a-form-model-item
      label="选择镜像"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      prop="image.id"
    >
      <a-select v-model="form.image.id">
        <a-select-option v-for="d in loadImage" :key="d.id">
          {{ d.name }}
        </a-select-option>
      </a-select>
    </a-form-model-item>
  </div>
</template>

<script>
import * as ImageService from '@/service/vm/ImageService'

export default {
  name: 'Step1',
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      labelCol: { lg: { span: 5 }, sm: { span: 5 } },
      wrapperCol: { lg: { span: 19 }, sm: { span: 19 } },
      loadImage: []
    }
  },
  created () {
    ImageService.queryAll({
      showLoading: false
    }).then(res => {
      this.loadImage = res
    })
  },
  methods: {
    nextStep () {
      // 先校验，通过表单校验后，才进入下一步
      this.$emit('nextStep')
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
