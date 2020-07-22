<template>
  <div class="wrapper">
    <div class="form">
      <div class="success">
        <div class="logo">
          <a-icon type="printer" />
        </div>
        <div class="info">倒计时 {{ state.time }} s，返回首页</div>
      </div>
    </div>
  </div>
</template>

<script>
import ROUTE_PATH from '@/router/route-paths'

export default {
  components: {
  },
  data () {
    return {
      state: {
        time: 5
      }
    }
  },
  created () {
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
  methods: {
  }
}
</script>

<style scoped lang="less">
.wrapper {
  height: 100%;
}
.form {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 90px;
  background: #fff;
  padding: 24px;

  .success {
    position: absolute;
    height: 200px;
    width: 200px;
    left: 50%;
    top: 50%;
    margin-top: -100px;
    margin-left: -100px;

    .logo {
      font-size: 80px;
      text-align: center;
    }

    .info {
      font-size: 16px;
      font-weight: bold;
      text-align: center;
      margin-top: 50px;
    }
  }
}
</style>
