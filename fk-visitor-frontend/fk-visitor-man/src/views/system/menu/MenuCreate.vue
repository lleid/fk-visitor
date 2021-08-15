<template>
  <c-modal title="添加菜单" centered :destroyOnClose="true" :visible="visible" :loading="loading" :confirmLoading="confirmLoading" @ok="handleSubmit" @cancel="handleClose">
    <a-form-model ref="menuCreate" :colon="false" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" :validate-messages="validateMessages">
      <a-form-model-item label="父菜单" prop="parentCode">
        <a-tree-select v-model="form.parentCode" :treeData="menuData" :allowClear="true" placeholder="请选择" treeDefaultExpandAll>
          <template slot-scope="menu" slot="title">
            <c-icon v-if="menu.icon !== undefined && menu.icon.trim() !== ''" :type="menu.icon" class="menu-selection-icon" />
            {{ menu.name + '[' + menu.value + ']' }}
          </template>
        </a-tree-select>
      </a-form-model-item>
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="别名" prop="alias">
        <a-input v-model="form.alias" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="跳转路径" prop="url">
        <a-input v-model="form.url" :max-length="128" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="关联路径" prop="relatedUrlList">
        <a-select v-model="form.relatedUrlList" mode="tags" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="排序" prop="code">
        <a-input v-model="form.code" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
      <a-form-model-item label="图标" prop="icon">
        <a-input v-model="form.icon" :max-length="16" placeholder="请输入" />
      </a-form-model-item>
    </a-form-model>
  </c-modal>
</template>

<script>
import FormConfig from '@/config/form.config'
import MenuRuleBuilder from './MenuRule'

import * as SysMenuService from '@/service/system/SysMenuService'

export default {
  data () {
    return {
      ...FormConfig,
      visible: false,
      loading: false,
      confirmLoading: false,
      form: {
        name: '',
        alias: '',
        url: '',
        relatedUrlList: [],
        icon: '',
        code: '',
        parentCode: undefined
      },
      menuData: [],
      rules: MenuRuleBuilder.build()
    }
  },
  methods: {
    async add () {
      this.visible = true
      this.loading = true
      try {
        const result = await SysMenuService.all({
          showLoading: false,
          showSuccess: false
        })
        this.menuData = this.genMenuData(result)
      } catch (e) {
      }
      this.loading = false
    },
    genMenuData (data) {
      return data.map(item => {
        const menuData = {
          value: item.code,
          key: item.code,
          name: item.name,
          icon: item.icon,
          scopedSlots: {
            title: 'title'
          }
        }
        if (item.subMenus && item.subMenus.length > 0) {
          menuData.children = this.genMenuData(item.subMenus)
        }
        return menuData
      })
    },
    handleSubmit () {
      this.$refs['menuCreate'].validate(async (valid) => {
        if (valid) {
          this.confirmLoading = true
          try {
            await SysMenuService.create(this.form)
            this.handleClose()
            this.$emit('ok')
          } catch (_) {
          }
          this.confirmLoading = false
        } else {
          return false
        }
      })
    },
    handleClose () {
      if (this.$refs['menuCreate'] !== undefined) {
        this.$refs['menuCreate'].resetFields()
      }
      this.visible = false
    }
  }
}
</script>

<style lang="less" scoped>
.menu-selection-icon {
  margin-right: 4px;
}
</style>
