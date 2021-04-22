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
        <c-icon type="fv-huanyingye"></c-icon>下午好，欢迎您访问复星凯特
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

export default {
  components: {
    Swiper,
    SwiperSlide
  },
  name: 'BlankLayout',
  data () {
    return {
      banners: [],
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
      operatorData: state => state.operator.operator,
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config,
      language: state => state.app.language,
      isHome: state => state.app.isHome
    }),
    userData: function () {
      if (this.operatorData !== undefined) {
        return {
          name: this.operatorData.username
        }
      }
      return {}
    }
  },
  async created () {
    const banners = await BannerService.queryAll({
      showLoading: false
    })
    this.banners = banners
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
  }

  .welcome {
    color: rgba(0, 0, 0, 0.65);
    font-size: 18px;
    line-height: 1.5;
    position: fixed;
    right: 48px;
    top: 60px;
    text-align: center;
    box-shadow: 0 2px 40px 0 rgba(0, 0, 0, 0.25);
    padding: 10px 16px;
    background: #fff;
    border-radius: 0px;
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
