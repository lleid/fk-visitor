<template>
  <div class="container">
    <div class="swipper">
      <a-carousel arrows style="height:100%" autoplay>
        <div slot="prevArrow" class="custom-slick-arrow" style="left: 10px;zIndex: 1">
          <a-icon type="left-circle" />
        </div>
        <div slot="nextArrow" class="custom-slick-arrow" style="right: 10px">
          <a-icon type="right-circle" />
        </div>
        <div class="swipper-img" v-for="(item,index) in banners" :key="index">
          <div class="img" :style="{backgroundImage:'url('+item.url+')'}" />
        </div>
      </a-carousel>
    </div>
    <div class="operate">
      <a-row>
        <a-col :span="4">
          <a class="btn cyan rounded" @click="handleSignout">签出</a>
        </a-col>
        <a-col :span="4">
          <a class="btn rounded" @click="handleHistory">历史访客</a>
        </a-col>
        <a-col :span="4"></a-col>
        <a-col :span="4"></a-col>
        <a-col :span="4" style="text-align:center">
          <a class="btn blue rounded" @click="handleInvite">受邀访客</a>
        </a-col>
        <a-col :span="4" style="text-align:right">
          <a class="btn blue rounded" @click="handleOrder">临时访客</a>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import ROUTE_PATH from '@/router/route-paths'
import * as BannerService from '@/service/system/BannerService'

export default {
  data () {
    return {
      banners: []
    }
  },
  async created () {
    const banners = await BannerService.queryAll({
      showLoading: false
    })
    this.banners = banners
  },
  methods: {
    handleHistory () {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.HISTORY_PATH })
    },
    handleOrder () {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH })
    },
    handleInvite () {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.INVITE_PATH })
    },
    handleSignout () {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.SIGNOUT_PATH })
    }
  }
}
</script>

<style scoped >
.container {
  height: 100%;
  padding-bottom: 60px;
}

.swipper {
  background: #fff;
  padding: 5px;
  height: 100%;
}

.swipper .swipper-img {
}

.swipper .swipper-img .img {
  height: 100%;
  width: 100%;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

.ant-carousel >>> .slick-slider {
  height: 100%;
}

.ant-carousel >>> .slick-list {
  height: 100%;
}

.ant-carousel >>> .slick-slider .slick-track {
  height: 100%;
}

.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 100%;
  background: #364d79;
  overflow: hidden;
}

.ant-carousel >>> .slick-slide div {
  height: 100%;
}

.ant-carousel >>> .custom-slick-arrow {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: #fff;
  background-color: rgba(31, 45, 61, 0.11);
  opacity: 0.3;
}
.ant-carousel >>> .custom-slick-arrow:before {
  display: none;
}
.ant-carousel >>> .custom-slick-arrow:hover {
  opacity: 0.5;
}

.operate {
  position: absolute;
  bottom: 0;
  left: 50px;
  right: 50px;
}

.operate .btn {
  width: 120px;
  text-align: center;
}
</style>
