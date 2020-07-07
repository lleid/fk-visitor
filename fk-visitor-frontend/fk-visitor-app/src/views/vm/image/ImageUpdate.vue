<template>
  <c-modal
    title="修改镜像"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="updateLoading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="imageUpdateForm"
      :model="form"
      :rules="rules"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name" />
      </a-form-model-item>
      <a-form-model-item label="描述" prop="description">
        <a-input v-model="form.description" />
      </a-form-model-item>
      <a-form-model-item label="系统类型" prop="systemType">
        <a-radio-group v-model="form.systemType" button-style="solid">
          <a-radio-button value="Linux">Linux</a-radio-button>
          <a-radio-button value="Microsoft Windows">Microsoft Windows</a-radio-button>
        </a-radio-group>
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import ImageRuleBuilder from './ImageGeneralRules'
import * as ImageService from '@/service/vm/ImageService'
import FormConfig from '@/config/form.config'

export default {
  data () {
    return {
      ...FormConfig,
      visible: false,
      confirmLoading: false,
      form: {
        id: '',
        name: '',
        description: '',
        path: ''
      },
      uploadPercent: 0,
      rules: {},
      activeImageTab: 'basic',
      imageType: '.img',
      updateLoading: false
    }
  },
  computed: {},
  methods: {
    edit (record) {
      this.visible = true
      this.loading = true
      this.$emit('stop-timer')
      try {
        this.form = { ...record }
        this.rules = ImageRuleBuilder.build(this.form)
      } catch (e) {
      }
      this.loading = false
    },
    handleSubmit () {
      this.$refs.imageUpdateForm.validate(async valid => {
        if (valid) {
          // 手动验证镜像
          this.confirmLoading = true
            ImageService.update(this.form).then(resp => {
              this.$emit('ok')
              this.confirmLoading = false
              this.handleClose()
              }
            ).catch((e) => {
              this.$message.warning(e.message)
              this.confirmLoading = false
            })
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.confirmLoading) {
        return
      }
      console.log(JSON.stringify(this.form))
      // this.$refs.imageUpdateForm.resetFields()
      this.visible = false
      this.uploadPercent = 0
      this.$emit('start-timer')
    },
    beforeUpload () {
      return false
    }
  }
}
</script>
