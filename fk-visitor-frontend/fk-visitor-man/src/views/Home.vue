<template>
  <div style=" padding: 20px;">
    <a-row :gutter="[16,16]">
      <a-col :span="24">
        <a-card title="月报" :bordered="false">
          <div id="month" class="echarts"></div>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="[16,16]">
      <a-col :span="24">
        <a-card title="年报" :bordered="false">
          <div id="year" class="echarts"></div>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="[16,16]">
      <a-col :span="12">
        <a-card title="季报" :bordered="false">
          <div id="quarter" class="echarts"></div>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card title="部门" :bordered="false">
          <div id="department" class="echarts"></div>
        </a-card>
      </a-col>
    </a-row>
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
    weekEcharts () {
      // 基于准备好的dom，初始化echarts实例
      var chart1 = this.$echarts.init(document.getElementById('month'))

      OrderService.groupMonth({
        showLoading: false,
        showSuccess: false
      }).then((res) => {
        var keys = []
        var values = []
        res.forEach(element => {
          keys.push(element.name)
          values.push(element.value)
        })
        // 指定图表的配置项和数据
        var option = {
          tooltip: {},
          legend: {
            data: ['访问量']
          },
          xAxis: {
            type: 'category',
            data: keys
          },
          yAxis: {
            interval: 1
          },
          series: [{
            data: values,
            type: 'line'
          }]
        }
        // 使用刚指定的配置项和数据显示图表。
        chart1.setOption(option)
      })
    },
    quarterEcharts () {
      // 基于准备好的dom，初始化echarts实例
      var chart2 = this.$echarts.init(document.getElementById('quarter'))

      OrderService.groupQuarter({
        showLoading: false,
        showSuccess: false
      }).then(res => {
        const arrs = res

        // 指定图表的配置项和数据
        var option = {
          tooltip: {},
          legend: {
            data: ['访问量']
          },
          xAxis: {
            type: 'category',
            data: ['一季度', '二季度', '三季度', '四季度']
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            data: arrs,
            type: 'bar'
          }]
        }
        // 使用刚指定的配置项和数据显示图表。
        chart2.setOption(option)
      })
    },
    yearEcharts () {
      // 基于准备好的dom，初始化echarts实例
      var chart3 = this.$echarts.init(document.getElementById('year'))

      OrderService.groupYear({
        showLoading: false,
        showSuccess: false
      }).then(res => {
        const arrs = res
        // 指定图表的配置项和数据
        var option = {
          tooltip: {},
          legend: {
            data: ['访问量']
          },
          xAxis: {
            type: 'category',
            data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            data: arrs,
            type: 'bar'
          }]
        }

        // 使用刚指定的配置项和数据显示图表。
        chart3.setOption(option)
      })
    },
    titleEcharts () {
      // 基于准备好的dom，初始化echarts实例
      var chart4 = this.$echarts.init(document.getElementById('department'))

      OrderService.groupDepartment({
        showLoading: false,
        showSuccess: false
      }).then(res => {
        var keys = []
        res.forEach(element => {
          keys.push(element.name)
        })

        // 指定图表的配置项和数据
        var option = {
          title: {
            text: '访问量',
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            orient: 'vertical',
            left: 'left',
            data: keys
          },
          series: [
            {
              name: '访问来源',
              type: 'pie',
              radius: '55%',
              center: ['50%', '60%'],
              data: res,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }

        // 使用刚指定的配置项和数据显示图表。
        chart4.setOption(option)
      })
    }
  },
  mounted () {
    this.weekEcharts()
    this.quarterEcharts()
    this.yearEcharts()
    this.titleEcharts()
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
