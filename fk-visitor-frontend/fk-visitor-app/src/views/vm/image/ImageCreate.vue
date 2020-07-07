<template>
  <c-modal
    title="添加镜像"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="imageForm"
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
      <a-form-model-item label="上传镜像">
        <a-upload
          :file-list="fileList"
          :accept="imageType"
          :beforeUpload="beforeUpload"
          @change="handleChange"
        >
          <a-button>
            <a-icon type="upload" />浏览文件
          </a-button>
        </a-upload>
      </a-form-model-item>
      <a-form-model-item v-if="uploadPercent !== 0">
        <a-progress stroke-linecap="square" :percent="uploadPercent" />
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
        name: '',
        description: '',
        systemType: ''
      },
      uploadPercent: 0,
      rules: {},
      activeImageTab: 'basic',
      fileList: [],
      imageType: ['.img', '.iso']
    }
  },
  computed: {},
  methods: {
    add () {
      this.rules = ImageRuleBuilder.build(this.form)
      this.visible = true
      this.$emit('stop-timer')
    },
    handleSubmit () {
      this.$refs.imageForm.validate(async valid => {
        if (valid) {
          // 手动验证镜像
          if (this.fileList[0] === undefined) {
            this.$message.warning('请选择镜像文件')
            return false
          }
          this.confirmLoading = true
            const data = new FormData()
            data.append('file', this.fileList[0]['originFileObj'])
            data.append('name', this.form.name)
            data.append('description', this.form.description)
            data.append('systemType', this.form.systemType)
            const config = {
              headers: {
                'content-type': 'multipart/form-data'
              },
              onUploadProgress: event => {
                this.uploadPercent = (event.loaded / event.total * 100 | 0)
              }
            }
            ImageService.create(data, config).then(resp => {
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
    handleChange (info) {
      // 限制上传数量
      let tmpList = [...info.fileList]
      if (tmpList.length > 1) {
        tmpList = tmpList.slice(-1)
      } else if (!this.confirmLoading) {
        this.fileList = tmpList
      }
    },
    handleClose () {
      if (this.confirmLoading) {
        return
      }
      this.$refs.imageForm.resetFields()
      this.visible = false
      this.fileList = []
      this.uploadPercent = 0
      this.$emit('start-timer')
    },
    beforeUpload () {
      return false
    }
  }
}
</script>
