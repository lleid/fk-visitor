<template>
  <div :class="prefixCls">
    <div ref="editor" class="editor-wrapper"></div>
  </div>
</template>

<script>
import WEditor from 'wangeditor'

export default {
  name: 'WangEditor',
  props: {
    prefixCls: {
      type: String,
      default: 'ant-editor-wang'
    },
    description: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      editor: null,
      editorContent: null,
      basePath: process.env.VUE_APP_API_BASE_URL
    }
  },
  mounted () {
    this.initEditor()
  },
  methods: {
    initEditor () {
      this.editor = new WEditor(this.$refs.editor)
      this.editor.customConfig.uploadImgServer = this.basePath + '/pub/editor/upload'
      this.editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024
      // this.editor.onchangeTimeout = 200
      this.editor.customConfig.onchange = (html) => {
        this.editorContent = html
        this.$emit('change', this.editorContent)
      }
      this.editor.customConfig.pasteTextHandle = (content) => { // 支持粘贴
        return content
      }
      this.editor.create()
      this.editorContent = this.description
      this.editor.txt.html(this.description)
    }
  }
}
</script>

<style lang="less" scoped>
.ant-editor-wang {
  .editor-wrapper {
    text-align: left;
  }
}
</style>
