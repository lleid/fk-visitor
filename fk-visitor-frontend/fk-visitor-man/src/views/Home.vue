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
              <a>今日</a>
              <a>本周</a>
              <a>本年</a>
            </div>
          </div>
          <a-tab-pane tab="访问量" key="1">
            <a-row>
              <a-col :md="24" :sm="24" :xs="24">
                <div class="echarts" id="main"></div>
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

import {
  ChartCard
} from '@/components'

export default {
  components: {
    ChartCard
  },
  methods: {
    myEcharts () {
      // 基于准备好的dom，初始化echarts实例
      var myChart = this.$echarts.init(document.getElementById('main'))

      // 指定图表的配置项和数据
      var option = {
        tooltip: {},
        legend: {
          data: ['访问量']
        },
        xAxis: {
          data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
        },
        yAxis: {},
        series: [{
          name: '销量',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20]
        }]
      }

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option)
    }
  },
  mounted () {
    this.myEcharts()
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
