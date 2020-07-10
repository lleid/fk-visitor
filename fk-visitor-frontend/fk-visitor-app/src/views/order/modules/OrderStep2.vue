<template>
  <div class="wrapper">请拍照</div>
</template>

<script>

export default {
  data () {
    return {

    }
  },
  created () {
  },
  methods: {
    handleSubmit () {
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100%;
}
.form {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 100px;
  background: #fff;
  padding: 24px;
  padding-top: 0;
}

.form .title {
  height: 50px;
  line-height: 50px;
  padding: 0 24px;
  font-weight: bold;
  border-bottom: 1px solid #ccc;
  margin-bottom: 24px;
}

.form .form-img img {
  height: 100%;
  width: 100%;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 450px;
  background: #364d79;
  overflow: hidden;
}

.operate {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>
