<template>
  <div>
    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">
        <a-tabs
          default-active-key="1"
          size="large"
          :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}"
        >
          <div class="extra-wrapper" slot="tabBarExtraContent">
            <div class="extra-item">
              <a @click="handleToggle()">本周</a>
            </div>
          </div>
          <a-tab-pane tab="访问量" key="1">
            <a-row>
              <a-col :md="24" :sm="24" :xs="24">
                <div class="echarts" id="week" v-show="!isYear"></div>
                <div class="echarts" id="year" v-show="isYear"></div>
              </a-col>
            </a-row>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-card>
    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}"></a-card>
  </div>
</template>

<script>

import * as OrderService from '@/service/data/OrderService'

export default {
  components: {
  },
  data () {
    return {
      isYear: false
    }
  },
  methods: {
    handleToggle () {
      this.isYear = !this.isYear
    },
    async weekEcharts () {
      // 基于准备好的dom，初始化echarts实例
      var myChart = this.$echarts.init(document.getElementById('week'))

      const arrs = await OrderService.groupWeek({
        showLoading: false,
        showSuccess: false
      })

      // 指定图表的配置项和数据
      var option = {
        tooltip: {},
        legend: {
          data: ['访问量']
        },
        xAxis: {
          type: 'category',
          data: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天']
        },
        yAxis: {
          interval: 1
        },
        series: [{
          data: arrs,
          type: 'line'
        }]
      }

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option)
    }
  },
  mounted () {
    this.weekEcharts()
  }
}
</script>

<style lang="less" scoped>
.extra-wrapper {
  line-height: 55px;
  padding-right: 24px;
  .extra-item {
    display: inline-block;
    margin-right: 24px;
    a {
      margin-left: 24px;
    }
  }
}

.echarts {
  padding: 0 24px 24px;
  width: 100%;
  height: 500px;
}
</style>
