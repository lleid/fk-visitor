<template>
  <div class="container">
    <div class="wrapper">
      <div class="form">
        <div class="steps">
          <span>{{ msgItem.title }}</span>
        </div>
        <div class="step-wrapper">
          <div class="team-wrapper">
            <div class="company">{{ team.company }}</div>
            <template v-if="language === 'CN'">
              <div class="visit">
                <span class="purpose" v-if="team.purpose">{{ team.purpose.cnName }}</span>/
                <span class="area" v-if="team.visitArea">{{ team.visitArea.cnName }}</span>
              </div>
              <div class="interviewer">受访人： {{ team.interviewer }}</div>
            </template>
            <template v-else>
              <div class="visit">
                <span class="purpose" v-if="team.purpose">{{ team.purpose.enName }}</span>/
                <span class="area" v-if="team.visitArea">{{ team.visitArea.enName }}</span>
              </div>
              <div class="interviewer">People visited： {{ team.interviewer }}</div>
            </template>
          </div>
          <swiper class="swiper swiper-example" :options="swiperOption" @clickSlide="handleClick">
            <swiper-slide
              v-for="(item,index) in staffs"
              :key="index"
              class="swiper-slide-example"
              @click="handleSubmit(item.id)"
            >
              <div class="staff-wrapper">
                <c-icon type="fv-chenggong" class="success" v-if="item.isCame"></c-icon>
                <div class="avatar">
                  <c-icon type="fv-touxiang" style="font-size:48px"></c-icon>
                </div>
                <div class="name">{{ item.name }}</div>
                <div class="item">{{ item.mobile }}</div>
                <div class="item">{{ item.idCard }}</div>
                <div class="item">{{ item.email }}</div>
                <div class="item">{{ item.title }}</div>
              </div>
            </swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
          </swiper>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'

import { mapState } from 'vuex'

import ROUTE_PATH from '@/router/route-paths'
import * as TeamService from '@/service/data/TeamService'

const MsgCN = {
  title: '团队预约'
}

const MsgEN = {
  title: 'Team appointment'
}

export default {
  components: {
    Swiper,
    SwiperSlide
  },
  data () {
    return {
      team: {},
      staffs: [],
      swiperOption: {
        slidesPerView: 5,
        spaceBetween: 30,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        }
      }
    }
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    msgItem () {
      if (this.language === 'EN') {
        return MsgEN
      }
      return MsgCN
    }
  },
  async created () {
    const teamId = this.$route.query.teamId
    if (teamId) {
      const team = await TeamService.get(teamId)
      this.team = team

      team.appointmentList.forEach(element => {
        if (!element.isDeleted) {
          this.staffs.push(element)
        }
      })

      console.log(this.staffs)
    }
  },
  methods: {
    handleClick (idx) {
      this.staffs.forEach((ele, index) => {
        if (idx === index) {
          this.$router.push({ path: ROUTE_PATH.APP_PATH.ORDER_PATH, query: { appointmentId: ele.id } })
        }
      })
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  position: relative;
  padding-bottom: 100px;
}

.wrapper {
  background: url(../../assets/background.png);
  padding: 10px;
  height: 100%;
  box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
  background-size: cover;
}

.form {
  padding: 50px;
  height: 100%;
  overflow: auto;
  background: #eef3f9;
  position: relative;
  padding-top: 80px;

  .steps {
    height: 80px;
    line-height: 80px;
    font-size: 24px;
    font-weight: bold;
    color: #003b86;
    position: absolute;
    top: 0;
    left: 50px;
    right: 50px;
  }

  .step-wrapper {
    background: #ffffff;
    height: 100%;
    padding: 24px;
    padding-top: 24px;
  }
}
.swiper-example {
  height: 230px;
  background-color: #f7f7f7;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.1);
}

.swiper-slide-example {
  background-color: #ffffff;
}

.team-wrapper {
  .company {
    height: 35px;
    line-height: 35px;
    text-align: center;
    font-size: 24px;
    font-weight: bold;
  }
  .visit {
    margin: 12px 0;
    height: 35px;
    line-height: 35px;
    text-align: center;
    font-size: 16px;
    color: #666;
    .purpose {
    }
    .area {
    }
  }
  .interviewer {
    margin: 12px 0;
    height: 35px;
    line-height: 35px;
    font-size: 16px;
    text-align: center;
    color: #666;
  }
}

.staff-wrapper {
  padding: 12px;
  height: 100%;
  text-align: center;
  padding-top: 80px;
  position: relative;

  .success {
    font-size: 18px;
    position: absolute;
    right: 12px;
    top: 12px;
  }
  .avatar {
    position: absolute;
    top: 15px;
    left: 0;
    right: 0;
  }
  .name {
    height: 35px;
    line-height: 35px;
    font-weight: bold;
  }
  .item {
  }
}
</style>
