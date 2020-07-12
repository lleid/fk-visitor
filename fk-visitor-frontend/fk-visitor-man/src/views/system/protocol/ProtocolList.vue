<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <a-card slot="children" :bordered="false" class="list-card">
      <wang-editor @change="description=$event" :description="description"></wang-editor>
      <div class="editor-btn">
        <a-button type="primary" @click="handleUpdate">提交</a-button>
      </div>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'
import WangEditor from '@/components/WangEditor'
import * as ProtocolService from '@/service/system/ProtocolService'

export default {
  components: {
    WangEditor
  },
  data () {
    return {
      form: {
        id: null,
        description: ''
      },
      description: ''
    }
  },
  computed: {
    ...mapState({
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config
    })
  },
  watch: {
    'form.description' (val) {
      console.log('123')
      if (val !== undefined) {
        this.description = val
      }
    }
  },
  created () {
    ProtocolService.get(1, { showLoading: false }).then(res => {
      this.form = res
    })
  },
  methods: {
    async handleUpdate () {
      const { form, description } = this
      const requestModel = {
        description: description
      }
      await ProtocolService.update(form.id, requestModel, { showLoading: false })
    }
  }
}
</script>

<style>
.editor-btn {
  height: 50px;
  line-height: 50px;
  float: right;
}
</style>
