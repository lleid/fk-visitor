<template>
  <div class="container">
    <div class="swiper">
      <swiper ref="mySwiper" :options="swiperOptions">
        <swiper-slide v-for="(item,index) in banners" :key="index" data-swiper-autoplay="2000">
          <div class="img" :style="{backgroundImage:'url('+item.url+')'}" />
        </swiper-slide>
      </swiper>
    </div>
    <div class="operate">
      <a-row>
        <a-col :span="4">
          <span class="btn" @click="handleSignout">{{ btnName.btn1 }}</span>
        </a-col>
        <a-col :span="4">
          <div class="history" @click="handleHistory">
            <c-icon type="fv-history"></c-icon>
            <span class="history-info">{{ btnName.btn2 }}</span>
          </div>
        </a-col>
        <a-col :span="8"></a-col>
        <a-col :span="4" style="text-align:center">
          <span class="btn btn-primary" @click="handleInvite">{{ btnName.btn3 }}</span>
        </a-col>
        <a-col :span="4" style="text-align:right">
          <span class="btn btn-primary" @click="handleOrder">{{ btnName.btn4 }}</span>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import { APP_MUTATIONS } from '@/store/modules/app-store'
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
  components: {
  },
  data () {
    return {
      banners: [],
      swiperOptions: {
        pagination: {
          el: '.swiper-pagination'
        },
        slidesPreView: 1,
        autoplay: {
          delay: 1000,
          disableOnInteraction: false
        }
      }
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
    },
    swiper () {
      return this.$refs.mySwiper.$swiper
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
      this.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, false)
    },
    handleOrder () {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH })
      this.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, false)
    },
    handleInvite () {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.INVITE_PATH })
      this.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, false)
    },
    handleSignout () {
      this.$router.push({ path: ROUTE_PATH.APP_PATH.SIGNOUT_PATH })
      this.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, false)
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  padding-bottom: 100px;
}

.swiper {
  background: url(../assets/background.png);
  padding: 12px;
  height: 100%;
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.3);
  background-size: cover;

  .swiper-container {
    height: 100%;

    .img {
      height: 100%;
      width: 100%;
      background-position: center;
      background-repeat: no-repeat;
      background-size: cover;
    }
  }
}

.operate {
  position: absolute;
  bottom: 20px;
  left: 120px;
  right: 120px;
}

.operate .history {
  font-size: 16px;
  font-weight: 800;
  color: #ffffff;
  height: 40px;
  line-height: 45px;
  text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
}

.operate .history i {
  font-size: 38px;
  text-shadow: 2px 2px 2px rgba(0, 0, 0, 0.6);
}

.operate .history .history-info {
  display: inline-block;
  vertical-align: 0.5em;
  margin-left: 8px;
}
</style>
