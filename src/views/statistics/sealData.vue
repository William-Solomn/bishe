<template>
  <el-main>
    <el-card>
      <div slot="header" class="clearfix">单日营业数据图</div>
      <div ref="chart" class="chart" style="height:300px; width:100%" />
    </el-card>
  </el-main>
</template>

<script>
import { getRestaurantInfo } from '@/utils/auth'
import { getDaySealData, hoursArray } from '@/api/statistics'

const echarts = require('echarts')

const animationDuration = 600

export default {
  name: 'SealData',
  data: () => ({
    chart: null,
    sealData: [],
    info: {
      restaurantId: 0
    }
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.$nextTick(() => {
      getDaySealData(this.info.restaurantId)
        .then(data => {
          this.sealData = data
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
          name: '销售额',
          type: 'line',
          data: this.sealData,
          itemStyle: {
            color: '#ffbf00'
          },
          areaStyle: {
            color: '#ffbf00'
          },
          animationDuration
        }]
      })
    }
  }
}
</script>
