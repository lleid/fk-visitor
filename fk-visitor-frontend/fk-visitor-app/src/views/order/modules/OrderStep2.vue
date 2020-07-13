<template>
  <div class="step2-wrapper">
    <img :src="avatar.base64" class="avatar" v-if="avatar && avatar.base64" />
    <span @click="handleClick" class="upload-btn">拍照</span>
  </div>
</template>

<script>

export default {
  props: {
    file: {
      type: Object,
      default: null
    }
  },
  components: {
  },
  data () {
    return {
      avatar: this.file
    }
  },
  methods: {
    handleClick () {
      this.$useUpload(data => {
        this.avatar = data
        this.$emit('change', this.avatar)
      })
    }
  }
}
</script>

<style scoped>
.step2-wrapper {
  height: 300px;
  width: 300px;
  margin: 0 auto;
  text-align: center;
}

.step2-wrapper .avatar {
  width: 250px;
  height: 250px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 2px 2px 5px #ccc;
}

.step2-wrapper .upload-btn {
  display: inline-block;
  height: 50px;
  line-height: 60px;
  font-size: 16px;
  font-weight: bold;
  border-bottom: 1px solid #000;
  width: 100px;
}
</style>
