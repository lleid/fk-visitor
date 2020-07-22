<template>
  <div class="wrapper">
    <div class="form">预约成功</div>
    <div class="form">倒计时 {{ state.time }} s，返回首页</div>
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

<style scoped>
.wrapper {
  height: 100%;
}
.form {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 100px;
  background: #fff;
  padding: 24px;
}

.operate {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>
