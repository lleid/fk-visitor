/* eslint-disable vue/require-v-for-key */
<template>
  <div class="container">
    <div class="swipper">
      <a-carousel effect="fade" autoplay>
        <div class="swipper-img" v-for="(item,index) in banners" :key="index">
          <img :src="item.url" />
        </div>
      </a-carousel>
    </div>
    <div class="operate">
      <a-row>
        <a-col :span="4">
          <a-button type="danger" size="large" shape="round" @click="handleSignout">签出</a-button>
        </a-col>
        <a-col :span="4">
          <a-button
            type="primary"
            shape="round"
            icon="history"
            size="large"
            @click="handleHistory"
          >历史</a-button>
        </a-col>
        <a-col :span="4"></a-col>
        <a-col :span="4"></a-col>
        <a-col :span="4" style="text-align:center">
          <a-button size="large" shape="round" @click="handleInvite">受邀访客</a-button>
        </a-col>
        <a-col :span="4" style="text-align:right">
          <a-button type="primary" size="large" shape="round" @click="handleOrder">临时访客</a-button>
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
