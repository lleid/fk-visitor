<template>
  <div class="container">
    <a-row>
      <a-col :span="12">1</a-col>
      <a-col :span="12">2</a-col>
    </a-row>
  </div>
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
  }
}
</script>

<style scoped>
.container {
  height: 100%;
}
.swipper {
  background: #fff;
  padding: 5px;
}

.swipper .swipper-img img {
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
