<template>
  <a-modal
    title="添加云存储"
    :destroyOnClose="true"
    :maskClosable="false"
    width="40%"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-form-model
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
      ref="ruleForm"
      :model="form"
      :rules="rules">
      <a-form-model-item label="名称" prop="name" has-feedback>
        <a-input v-model="form.name" />
      </a-form-model-item>
      <a-form-model-item label="描述" prop="description" has-feedback>
        <a-input v-model="form.description" />
      </a-form-model-item>
      <a-form-model-item label="硬盘大小" prop="size" >
        <a-input-number :min="1" :max="20" v-model="form.size" />
      </a-form-model-item>
      <a-form-model-item label="所使用镜像" prop="images" has-feedback>
        <a-select v-model="form.images">
          <a-select-option v-for="image in images" :key="image.id" :value="image.id">
            {{ image.name }}
          </a-select-option>
        </a-select>
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
import * as StorageUnitGeneralRules from './StorageUnitGeneralRules'
import * as StorageUnitService from '@/service/vm/StorageUnitService'
import * as ImageService from '@/service/vm/ImageService'
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      visible: false,
      confirmLoading: false,
      images: [],
      form: {
        name: '',
        description: '',
        bootable: false,
        size: 1,
        image: { id: '' },
        images: ''
      },
      rules: {}
    }
  },
  computed: {},
  async created () {
    await ImageService.queryAll({
      showLoading: false,
      showSuccess: false,
      showError: false
      }).then(res => {
        this.images = res
    })
  },
  methods: {
    add () {
      this.rules = StorageUnitGeneralRules.build(this.form)
      this.visible = true
      this.$emit('stop-timer')
    },
    handleSubmit () {
      this.confirmLoading = true
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          this.form['bootable'] = true
          this.form['image'] = { id: this.form.images }
          StorageUnitService.create(this.form).then(res => {
            this.confirmLoading = false
            this.$refs.ruleForm.resetFields()
            this.visible = false
            this.$emit('ok')
            this.$emit('start-timer')
          })
        } else {
          this.confirmLoading = false
          return false
        }
      })
    },
    handleCancel () {
      this.form.images = undefined
      this.$refs.ruleForm.resetFields()
      this.visible = false
      this.$emit('start-timer')
    }
  }
}
</script>
