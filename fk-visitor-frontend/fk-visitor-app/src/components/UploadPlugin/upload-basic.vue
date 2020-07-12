
<script>
export default {
  name: 'UploadBasic',
  methods: {
    _changeFile (e) {
      const file = e.target.files[0]
      const reader = new FileReader()
      const self = this

      reader.onloadend = function () {
        // 这个this指代的是reader实例
        // result就是一个base64格式的文本
        const result = this.result
        // 当reader实例读取文件完毕后，咱们把这个result和file都emit出去
        self.$emit('finish', {
          base64: result,
          file: file
        })
      }
      reader.readAsDataURL(file)
    },
    upload () {
      this.$refs.upBInput.click()
    }
  },
  render () {
    return (
      <input ref="upBInput" style="display:none;" type="file" accept="image/*" onChange={(e) => { this._changeFile(e) }} />
    )
  }
}
</script>

<style>
</style>
