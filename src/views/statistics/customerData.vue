<template>
  <el-main>
    <el-card>
      <div slot="header" class="clearfix">单日顾客数据图</div>
      <div ref="chart" class="chart" style="height:300px; width:100%" />
    </el-card>
  </el-main>
</template>

<script>
import { getDayCustomerData } from '@/api/statistics.js'
import { getRestaurantInfo } from '@/utils/auth'
import { hoursArray } from '@/api/statistics'

const echarts = require('echarts')

const animationDuration = 600

export default {
  name: 'CustomerData',
  data: () => ({
    chart: null,
    customerData: [],
    info: {
      restaurantId: 0
    }
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.$nextTick(() => {
      getDayCustomerData(this.info.restaurantId)
        .then(data => {
          this.customerData = data
          this.initChart()
        })
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$refs.chart)

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'line' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: 10,
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: hoursArray,
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        series: [{
          name: '顾客数',
          type: 'line',
          itemStyle: {
            color: '#2274a5'
          },
          areaStyle: {
            color: '#2274a5'
          },
          data: this.customerData,
          animationDuration
        }]
      })
    }
  }
}
</script>
