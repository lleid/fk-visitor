<template>
  <c-modal
    title="编辑用户组"
    centered
    :destroyOnClose="true"
    :visible="visible"
    :loading="loading"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleClose"
  >
    <a-form-model
      ref="operatorGroupUpdate"
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

import * as OperatorGroupService from '@/service/system/OperatorGroupService'

export default {
  data () {
    return {
      ...FormConfig,
      labelCol: FormConfig.labelCol,
      wrapperCol: FormConfig.wrapperCol,
      validateMessage: FormConfig.validateMessages,
      visible: false,
      loading: false,
      confirmLoading: false,
      form: {
        name: '',
        pid: ''
      },
      treeData: [],
      rules: {}
    }
  },
  created () {
  },
  methods: {
    async edit (record) {
      this.visible = true
      this.loading = true
      try {
        const result = await OperatorGroupService.get(record.id, { showLoading: false })

        this.form = result
        this.rules = OperatorGroupRuleBuilder.build(this.form)

        const groups = await OperatorGroupService.groups({
          showLoading: false,
          showSuccess: false,
          showError: false
        })
        this.treeData = this.genTree(groups, record.id)
      } catch (error) {

      }
      this.loading = false
    },
    genTree (data, id) {
      return data.map(item => {
        const treeData = {
          title: item.name,
          value: item.id,
          key: item.id
        }
        if (item.id === id || item.pid === id) {
          treeData.selectable = false
        }
        if (item.subGroups && item.subGroups.length > 0) {
          treeData.children = this.genTree(item.subGroups, id)
        }
        return treeData
      })
    },
    handleSubmit () {
      this.$refs['operatorGroupUpdate'].validate(async valid => {
        if (valid) {
          this.confirmLoading = true
          await OperatorGroupService.update(this.form.id, this.form)
          this.confirmLoading = false
          this.handleClose()
          this.$emit('ok')
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs['operatorGroupUpdate'] !== undefined) {
        this.$refs['operatorGroupUpdate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>
