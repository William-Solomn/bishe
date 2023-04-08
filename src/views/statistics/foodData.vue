<template>
  <el-main>
    <el-card>
      <div slot="header" class="clearfix">热门菜品统计</div>
      <div id="chart" class="chart" style="height:300px; width:100%">
        <el-alert
          title="无热门菜品数据"
          type="info"
          description="暂无今日热门菜品数据，请稍后尝试"
          show-icon
          :closable="false"
        />
      </div>
    </el-card>
  </el-main>
</template>

<script>
import { getRestaurantInfo } from '@/utils/auth'
import { getDayFoodData } from '@/api/statistics'

const echarts = require('echarts')

const animationDuration = 600

export default {
  name: 'FoodData',
  data: () => ({
    chart: null,
    foodData: {
      nameList: [],
      dataList: []
    },
    info: {
      restaurantId: 0
    }
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.$nextTick(() => {
      getDayFoodData(this.info.restaurantId)
        .then(data => {
          this.foodData = data
          if (this.foodData.nameList.length !== 0) {
            this.initChart()
          }
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
    handleColor(name) {
      let hex = Array.from(name).reduce(
        (previousValue, currentValue) => previousValue + currentValue.charCodeAt(),
        name.charCodeAt()
      ).toString(16)

      if (hex.length > 6) {
        hex = hex.substr(0, 6)
      } else if (hex.length < 6) {
        hex = hex + hex.substr(0, 6 - hex.length)
      }
      return `#${hex}`
    },
    initChart() {
      this.chart = echarts.init(document.getElementById('chart'))

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
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
          data: this.foodData.nameList,
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
          name: '菜品销量',
          type: 'bar',
          data: this.foodData.dataList,
          animationDuration,
          itemStyle: {
            color: (params) => this.handleColor(params.name)
          }
        }]
      })
    }
  }
}
</script>
