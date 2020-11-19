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
          <a class="btn" @click="handleSignout">{{ btnName.btn1 }}</a>
        </a-col>
        <a-col :span="4">
          <div class="history" @click="handleHistory">
            <c-icon type="fv-history"></c-icon>
            <span class="history-info">{{ btnName.btn2 }}</span>
          </div>
        </a-col>
        <a-col :span="8"></a-col>
        <a-col :span="4" style="text-align:center">
          <a class="btn btn-primary" @click="handleInvite">{{ btnName.btn3 }}</a>
        </a-col>
        <a-col :span="4" style="text-align:right">
          <a class="btn btn-primary" @click="handleOrder">{{ btnName.btn4 }}</a>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import * as BannerService from '@/service/system/BannerService'

const BtnCN = {
  btn1: '签出',
  btn2: '历史访客',
  btn3: '受邀访客',
  btn4: '临时访客'
}

const BtnEN = {
  btn1: 'Sign Out',
  btn2: 'Visited',
  btn3: 'Invited Visitor',
  btn4: 'Walk-in Guest'
}

export default {
  data () {
    return {
      banners: []
    }
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    btnName () {
      if (this.language === 'EN') {
        return BtnEN
      }
      return BtnCN
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
  padding-bottom: 120px;
}

.swipper {
  background: url(../assets/background.png);
  padding: 12px;
  height: 100%;
  box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
  background-size: cover;
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
  bottom: 30px;
  left: 180px;
  right: 180px;
}

.operate .history {
  font-size: 16px;
  font-weight: bold;
  color: #ffffff;
  height: 40px;
  line-height: 45px;
}

.operate .history i {
  font-size: 38px;
}

.operate .history .history-info {
  display: inline-block;
  vertical-align: 0.5em;
  margin-left: 8px;
}
</style>
