<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <a-card slot="children" :bordered="false" class="list-card">
      <a-upload
        action="http://10.240.68.100:9099/pub/banner/upload"
        list-type="picture-card"
        :file-list="fileList"
        @preview="handlePreview"
        @change="handleChange"
      >
        <div v-if="fileList.length < 8">
          <a-icon type="plus" />
          <div class="ant-upload-text">上传</div>
        </div>
      </a-upload>
      <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
        <img alt="example" style="width: 100%" :src="previewImage" />
      </a-modal>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { mapState } from 'vuex'

import * as BannerService from '@/service/system/BannerService'

function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

export default {
  components: {
  },
  data () {
    return {
      previewVisible: false,
      previewImage: '',
      fileList: []
    }
  },
  computed: {
    ...mapState({
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config
    })
  },
  created () {
    this.handleQuery()
  },
  methods: {
    async handleQuery () {
      this.fileList = []
      const banners = await BannerService.queryAll()
      if (banners) {
        banners.forEach(p => {
          this.fileList.push({
            uid: p.id,
            name: p.name,
            url: p.url,
            status: 'done'
          })
        })
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    async handleChange ({ file, fileList }) {
      const that = this
      if (file.status === 'removed') {
        this.$confirm({
          title: '确认信息',
          content: '确定删除当前图片信息吗？',
          onOk () {
            const _id = file.uid
            BannerService.del(_id).then(res => {
            })
          },
          onCancel () {
            that.handleQuery()
          }
        })
      }
      this.fileList = fileList
    }
  }
}
</script>

<style>
.operate-button {
  margin-left: 8px;
}
.search-form {
  padding: 14px;
}
</style>
