<template>
  <div class="container">
    <div class="form">
      <div class="steps">
        <c-icon type="fv-yuan"></c-icon>
        <span>{{ tipName.title }}</span>
      </div>
      <div class="printer">
        <div class="icon">
          <c-icon type="fv-ziyuanldpi"></c-icon>
        </div>
        <div class="info">
          <span class="item1">{{ tipName.item1 }}</span>
          <span class="item2">{{ state.time }}</span>
          <span class="item3">{{ tipName.item3 }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ROUTE_PATH from '@/router/route-paths'

const TipCN = {
  title: '标签打印',
  item1: '打印中，倒计时',
  item3: 's，返回首页'
}

const TipEN = {
  title: 'Label printing',
  item1: 'In print, countdown',
  item3: 's，back homepage '
}

export default {
  components: {
  },
  data () {
    return {
      state: {
        time: 5
      },
      order: undefined
    }
  },
  async created () {
    const { state } = this
    const that = this
    const interval = window.setInterval(() => {
      if (state.time-- <= 0) {
        state.time = 5
        window.clearInterval(interval)
        that.$router.push({ path: ROUTE_PATH.HOME_PATH })
      }
    }, 1000)
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    tipName () {
      if (this.language === 'EN') {
        return TipEN
      }
      return TipCN
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  padding-top: 60px;
  position: relative;
  padding-bottom: 80px;
}

.form {
  .steps {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 60px;
    line-height: 60px;
    font-size: 20px;
    border-bottom: 1px solid #0565aa;
    background: #fff;
    padding: 0 24px;
    color: #003b86;

    i {
      margin-right: 12px;
    }

    span {
    }
  }

  padding: 24px;
  height: 100%;
  overflow: auto;
  background: #fff;
}

.printer {
  .icon {
    text-align: center;
    font-size: 56px;
    height: 100px;
    line-height: 100px;
    top: 50%;
    margin-top: 150px;
  }

  .info {
    text-align: center;
    margin-top: 24px;
    font-size: 16px;
    color: #003b86;

    .item1 {
    }
    .item2 {
      margin-left: 12px;
      margin-right: 12px;
      font-size: 24px;
      font-weight: bold;
    }
    .item3 {
    }
  }
}
</style>
