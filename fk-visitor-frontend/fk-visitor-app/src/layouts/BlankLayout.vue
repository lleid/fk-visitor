<template>
  <div class="layout-wrapper">
    <div class="swiper-modal swiper-no-swiping">
      <swiper class="swiper" :options="swiperOption">
        <swiper-slide v-for="(item,index) in banners" :key="index">
          <div class="img" :style="{backgroundImage:'url('+item.url+')'}" />
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper>
    </div>
    <div class="layout-header">
      <img class="logo" src="~@/assets/logo.png" />
      <div class="welcome" v-if="isHome">
        <c-icon type="fv-huanyingye"></c-icon>{{ datePeriod === 'morning'? msg.morning: datePeriod === 'afternoon'? msg.afternoon : msg.night }} ，{{ msg.welcome }}
      </div>
      <div class="home" @click="handleHome" v-if="!isHome">
        <c-icon type="fv-shouye" style="font-size:64px"></c-icon>
      </div>
    </div>
    <div class="layout-content">
      <router-view />
    </div>
    <div class="layout-footer">Copyright &copy; 2020 复星凯特生物科技有限公司</div>
  </div>
</template>

<script>

import { mapState } from 'vuex'

import { APP_MUTATIONS } from '@/store/modules/app-store'
import ROUTE_PATH from '@/router/route-paths'

import * as BannerService from '@/service/system/BannerService'

import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'

const lang = {
  cn: {
    morning: '早上好',
    afternoon: '下午好',
    night: '晚上好',
    welcome: '欢迎您访问复星凯特'
  },
  en: {
    morning: 'Good morning',
    afternoon: 'Good afternoon',
    night: 'Good night',
    welcome: 'Welcome to visit the Fosunkite'
  }
}

export default {
  components: {
    Swiper,
    SwiperSlide
  },
  name: 'BlankLayout',
  data () {
    return {
      banners: [],
      datePeriod: 'morning',
      swiperOption: {
        autoplay: {
          delay: 5000,
          disableOnInteraction: false
        },
        loop: true
      }
    }
  },
  computed: {
    ...mapState({
      loadingState: state => state.app.loading,
      themeConfig: state => state.theme.config,
      language: state => state.app.language,
      isHome: state => state.app.isHome
    }),
    msg () {
      if (this.language === 'EN') {
        return lang.en
      }
      return lang.cn
    }
  },
  async created () {
    const that = this
    const banners = await BannerService.queryAll({
      showLoading: false
    })
    this.banners = banners

    setInterval(() => {
      const time = new Date()
      const hour = time.getHours()
      if (hour > 12 && hour < 18) {
        that.datePeriod = 'afternoon'
      }
      if (hour >= 18 && hour < 24) {
        that.datePeriod = 'night'
      }
    }, 60000)
  },
  methods: {
    handleHome () {
      this.$router.push({ path: ROUTE_PATH.HOME_PATH })
      this.$store.commit(APP_MUTATIONS.UPDATE_ISHOME, true)
    }
  }
}
</script>

<style scoped lang="less">
.layout-wrapper {
  height: 100%;
  background-color: rgba(255, 255, 255, 0.2);
  padding-top: 180px;
  padding-bottom: 55px;
}

.swiper-modal {
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  z-index: -1;
  opacity: 0.5;

  .swiper-container {
    height: 100%;
  }
  .img {
    height: 100%;
    width: 100%;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
  }
}

.layout-header {
  .logo {
    position: absolute;
    left: 58px;
    top: 48px;
    width: 360px;
    z-index: 9999;
  }

  .welcome {
    color: rgba(0, 0, 0, 0.65);
    font-size: 18px;
    line-height: 1.5;
    position: fixed;
    right: 0;
    left: 300px;
    top: 50px;
    text-align: center;
    padding: 10px 58px;
    border-radius: 0px;
    color: #003b82;
    text-align: right;
    background-image: linear-gradient(to right, rgba(255, 255, 255, 0.5), rgba(255, 255, 255, 1));

    i {
      color: #faad14;
      margin-right: 8px;
    }
  }

  .home {
    position: absolute;
    right: 115px;
    top: 58px;
    color: #3263ad;
    box-shadow: 18px 18px 30px rgba(0, 59, 131, 0.3), -18px -18px 30px rgba(255, 255, 255, 0.5);
    border-radius: 50%;
    border: 0;
    background: #ffffff;
  }
}

.layout-content {
  height: 100%;
  position: relative;
  padding-left: 120px;
  padding-right: 120px;
}

.layout-footer {
  line-height: 35px;
  position: absolute;
  bottom: 0;
  height: 35px;
  text-align: center;
  left: 0;
  right: 0;
  color: #ffffff;
}
</style>
