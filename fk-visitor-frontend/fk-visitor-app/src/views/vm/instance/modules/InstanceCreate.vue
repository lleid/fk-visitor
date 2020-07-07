<template>
  <a-modal
    title="虚拟机添加"
    :destroyOnClose="true"
    :maskClosable="false"
    width="60%"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <template slot="footer">
      <a-button :disabled="currentTab === 0 ? true : false" key="pre" @click="prevStep">上一步</a-button>
      <a-button :disabled="currentTab === 3 ? true : false" key="next" @click="nextStep">下一步</a-button>
      <a-button :disabled="currentTab !== 3 ? true: false " key="submit" type="primary" :loading="confirmLoading" @click="handleSubmit">
        完成
      </a-button>
      <a-button key="back" @click="handleCancel">取消</a-button>
    </template>
    <a-form-model
      ref="ruleForm"
      :model="form"
      :rules="rules"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
    >
      <a-card :bordered="false">
        <a-row>
          <a-steps class="steps" direction="horizontal" :current="currentTab" size="small">
            <a-step title="选择镜像" />
            <a-step title="配置选择" />
            <a-step title="网络设置" />
            <a-step title="基本信息" />
          </a-steps>
          <div class="content">
            <keep-alive>
              <InstanceStep1 :form="form" v-if="currentTab === 0"/>
            </keep-alive>
            <keep-alive>
              <InstanceStep2 :form="form" v-if="currentTab === 1"/>
            </keep-alive>
            <keep-alive>
              <InstanceStep3 :form="form" v-if="currentTab === 2"/>
            </keep-alive>
            <keep-alive>
              <InstanceStep4 :form="form" v-if="currentTab === 3"/>
            </keep-alive>
          </div>
        </a-row>
      </a-card>
    </a-form-model>
  </a-modal>
</template>

<script>
import InstanceStep1 from './InstanceStep1'
import InstanceStep2 from './InstanceStep2'
import InstanceStep3 from './InstanceStep3'
import InstanceStep4 from './InstanceStep4'
import * as InstanceCreateRule from './InstanceCreateRule'
import * as InstanceService from '@/service/vm/InstanceService'

export default {
  components: {
    InstanceStep1,
    InstanceStep2,
    InstanceStep3,
    InstanceStep4
  },
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
      currentTab: 0,
      form: {
        name: '',
        image: {
          id: ''
        },
        template: {
          id: '',
          vCPUs: '',
          ram: '',
          size: 0
        },
        keypair: {
          id: ''
        },
        networks: []
      },
      rules: []
    }
  },
  created () {
    this.rules = InstanceCreateRule.build(this.form)
  },
  methods: {
    // handler
    nextStep () {
      this.currentTab += 1
    },
    prevStep () {
      if (this.currentTab === 1) {

      } else if (this.currentTab === 2) {
        this.form.name = ''
        this.form.keypair.id = ''
      }
      this.currentTab -= 1
    },
    add () {
      this.$emit('stop-timer')
      this.loading = false
      this.visible = true
    },
    handleSubmit () {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          InstanceService.create(this.form).then(res => {
            this.confirmLoading = false
            this.$refs.ruleForm.resetFields()
            this.visible = false
            this.$emit('ok')
          })
        } else {
          this.confirmLoading = false
          return false
        }
      })
    },
    handleCancel () {
      this.$emit('start-timer')
      this.$refs.ruleForm.resetFields()
      this.resetForm()
      this.currentTab = 0
      this.visible = false
    },
    resetForm () {
      this.form.template.ram = ''
      this.form.template.size = 0
      this.form.template.vCPUs = ''
    }
  }
}
</script>
