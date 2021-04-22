<template>
  <div class="home-container">
    <div class="home-operate-wrapper">
      <div class="operate">
        <div class="press-btn invite" @click="handleInvite">
          <c-icon type="fv-erweima" style="font-size:110px"></c-icon>
        </div>
        <div class="operate-name">{{ btns.btn3.name }}</div>
        <div class="operate-desc">{{ btns.btn3.desc }}</div>
      </div>
      <div class="operate">
        <div class="press-btn history" @click="handleHistory">
          <c-icon type="fv-touxiang1" style="font-size:110px"></c-icon>
        </div>
        <div class="operate-name">{{ btns.btn2.name }}</div>
        <div class="operate-desc">{{ btns.btn2.desc }}</div>
      </div>
      <div class="operate">
        <div class="press-btn order" @click="handleOrder">
          <c-icon type="fv-card-fill" style="font-size:120px"></c-icon>
        </div>
        <div class="operate-name">{{ btns.btn4.name }}</div>
        <div class="operate-desc">{{ btns.btn4.desc }}</div>
      </div>
    </div>
    <div class="signout">
      <div class="press-btn" @click="handleSignout">
        <c-icon type="fv-tuichu2" style="font-size:36px">
        </c-icon>
      </div>
      <div class="operate-name">{{ btns.btn1 }}</div>
    </div>
    <div class="language">
      <div class="press-btn" @click="handleLanguage">
        <c-icon :type="btns.btn5" style="font-size:36px"></c-icon>
      </div>
      <div class="operate-name">{{ btns.btn6 }}</div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import { APP_MUTATIONS } from '@/store/modules/app-store'

const mes = {
  cn: {
    btns: {
      btn1: '签出',
      btn2: {
        name: '历史访客',
        desc: '（ 曾经到访过 ）'
      },
      btn3: {
        name: '受邀访客',
        desc: '（ 需要预先登记 ）'
      },
      btn4: {
        name: '临时访客',
        desc: '（ 需要受访人陪同 ）'
      },
      btn5: 'fv-yuyanzhongwen',
      btn6: '语言'
    }
  },
  en: {
    btns: {
      btn1: 'Sign Out',
      btn2: {
        name: 'Visited',
        desc: '（Have visited ）'
      },
      btn3: {
        name: 'Invited Visitor',
        desc: '（Pre-registration required ）'
      },
      btn4: {
        name: 'Walk-in Guest',
        desc: '（Need to accompany ）'
      },
      btn5: 'fv-yuyanyingwen',
      btn6: 'Language'
    }
  }
}

export default {
  components: {
  },
  data () {
    return {
    }
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    btns () {
      if (this.language === 'EN') {
        return mes.en.btns
      }
      return mes.cn.btns
    }
  },
  async created () {
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
    },
    async handleLanguage () {
      if (this.language === 'CN') {
        this.$store.commit(APP_MUTATIONS.UPDATE_LANGUAGE, 'EN')
      } else {
        this.$store.commit(APP_MUTATIONS.UPDATE_LANGUAGE, 'CN')
      }
    }
  }
}
</script>

<style scoped lang="less">
.home-container {
  height: 100%;
  padding-bottom: 100px;

  .press-btn {
    width: 100px;
    height: 100px;
    box-shadow: 18px 18px 30px rgba(0, 59, 131, 0.3), -18px -18px 30px rgba(255, 255, 255, 0.5);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #ffffff;
    transition: box-shadow 0.2s ease-out;
    position: relative;
  }

  .signout {
    position: absolute;
    left: 58px;
    bottom: 0;
    font-size: 16px;
    font-weight: bold;
    .press-btn {
      background: #003b82;

      i {
        color: #ffffff;
      }
    }

    .operate-name {
      color: #003b82;
      margin-top: 10px;
      height: 100px;
      line-height: 100px;
      font-size: 18px;
      font-weight: bold;
      text-align: center;
      position: absolute;
      left: 110px;
      bottom: 0;
      width: 100px;
    }
  }

  .language {
    position: absolute;
    right: 58px;
    bottom: 0;
    font-size: 16px;
    font-weight: bold;
    color: #003b82;

    .operate-name {
      color: #003b82;
      margin-top: 10px;
      height: 100px;
      line-height: 100px;
      font-size: 18px;
      font-weight: bold;
      text-align: center;
      position: absolute;
      right: 110px;
      bottom: 0;
      width: 100px;
    }
  }

  .home-operate-wrapper {
    position: absolute;
    height: 300px;
    left: 120px;
    right: 120px;
    top: 50%;
    display: flex;
    margin-top: -250px;

    .operate {
      flex: 1;
      color: #003b82;
      .history,
      .invite,
      .order {
        border: 5px solid #0066aa;
        color: #003b82;
        margin: 0 auto;
      }

      .operate-name {
        margin-top: 30px;
        height: 50px;
        line-height: 50px;
        font-size: 28px;
        font-weight: bold;
        text-align: center;
      }

      .operate-desc {
        margin-top: 10px;
        height: 35px;
        line-height: 35px;
        font-size: 22px;
        font-weight: bold;
        text-align: center;
      }

      .press-btn {
        width: 240px;
        height: 240px;
        line-height: 240px;
      }
    }
  }
}
</style>
