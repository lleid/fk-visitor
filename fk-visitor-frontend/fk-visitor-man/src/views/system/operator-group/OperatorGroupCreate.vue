<template>
  <c-modal
    title="添加用户组"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="operatorGroupCreate"
      :model="form"
      :rules="rules"
      :labelCol="labelCol"
      :wrapperCol="wrapperCol"
      :validate-messages="validateMessages"
    >
      <a-form-model-item label="用户组名称" prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="请输入"/>
      </a-form-model-item>
      <a-form-model-item label="父用户组" prop="pid">
        <a-tree-select
          style="width:100%"
          v-model="form.pid"
          :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
          :treeData="treeData"
          :allowClear="true"
          treeDefaultExpandAll
        >
        </a-tree-select>
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'

import OperatorGroupRuleBuilder from './OperatorGroupRule'

import * as OpeartorGroupService from '@/service/system/OperatorGroupService'

export default {
  data () {
    return {
      ...FormConfig,
      labelCol: FormConfig.labelCol,
      wrapperCol: FormConfig.wrapperCol,
      validateMessage: FormConfig.validateMessages,
      loading: false,
      visible: false,
      confirmLoading: false,
      form: {
        name: '',
        pid: ''
      },
      treeData: [],
      rules: OperatorGroupRuleBuilder.build()
    }
  },
  methods: {
    async add () {
      this.loading = true
      this.visible = true
      try {
        const operatorGroup = await OpeartorGroupService.groups({
          showLoading: false
        })
        this.loading = false
        this.treeData = this.genTree(operatorGroup)
      } catch (error) {

      }
      this.loading = false
    },
    genTree (data) {
      return data.map(item => {
        const treeData = {
          title: item.name,
          value: item.id,
          key: item.id
        }
        if (item.subGroups && item.subGroups.length > 0) {
          treeData.children = this.genTree(item.subGroups)
        }
        return treeData
      })
    },
    handleSubmit () {
      this.$refs['operatorGroupCreate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          await OpeartorGroupService.create(this.form)
          this.confirmLoading = false
          this.handleClose()
          this.$emit('ok')
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs['operatorGroupCreate'] !== undefined) {
        this.$refs['operatorGroupCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
