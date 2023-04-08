<template>
  <el-main class="main">
    <el-row>
      <el-alert
        title="提示"
        type="info"
        show-icon
        :closable="false"
      >
        <div slot="default">
          统计数据每十分钟更新一次，如需立即更新请
          <el-button size="mini" type="text" @click="handleRefresh">点击此处更新</el-button>
        </div>
      </el-alert>
    </el-row>
    <el-row :gutter="16">
      <SealData />
    </el-row>

    <el-row :gutter="16">
      <CustomerData />
    </el-row>

    <el-row :gutter="16">
      <FoodData />
    </el-row>

  </el-main>
</template>

<script>
import SealData from '@/views/statistics/sealData'
import FoodData from '@/views/statistics/foodData'
import CustomerData from '@/views/statistics/customerData'
import { getRestaurantInfo } from '@/utils/auth'
import { getDayDataNoCache } from '@/api/statistics'

export default {
  components: { CustomerData, FoodData, SealData },
  data: () => ({
    info: {
      restaurantId: -1
    }
  }),
  mounted() {
    this.info = getRestaurantInfo()
  },
  methods: {
    async handleRefresh() {
      await getDayDataNoCache(this.info.restaurantId)
      location.reload()
    }
  }
}
</script>
