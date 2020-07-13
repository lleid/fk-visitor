/* eslint-disable vue/require-v-for-key */
<template>
  <div class="container">
    <div class="operate">
      <a-row>
        <a-col :span="4">
          <a-button type="danger" size="large" shape="round">
            登出
            <a-icon type="logout" />
          </a-button>
        </a-col>
        <a-col :span="4"></a-col>
        <a-col :span="4"></a-col>
        <a-col :span="4"></a-col>
        <a-col :span="4" style="text-align:center">
          <a-button size="large" shape="round">
            受邀访客
            <a-icon type="right-square" />
          </a-button>
        </a-col>
        <a-col :span="4" style="text-align:right">
          <a-button type="primary" size="large" shape="round">
            临时访客
            <a-icon type="right-circle" />
          </a-button>
        </a-col>
      </a-row>
    </div>
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
