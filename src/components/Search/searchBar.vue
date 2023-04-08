<template>
  <el-form-item label-width="80px" label="菜名/拼音">
    <el-select
      v-model="keyword"
      filterable
      remote
      reserve-keyword
      :multiple-limit="1"
      placeholder="请输入关键词"
      :remote-method="handleSearch"
      :loading="loading"
      @change="handleSelect"
    >
      <el-option
        v-for="item in foodList"
        :key="item['id']"
        :label="item['name']"
        :value="item['id']"
      />
    </el-select>

  </el-form-item>
</template>

<script>
import { getFoodList } from '@/api/food'
import { getRestaurantInfo } from '@/utils/auth'

export default {
  name: 'SearchBar',
  props: {
    callback: {
      type: Function,
      default: () => {
        console.log('default')
      }
    }
  },
  data: () => ({
    info: {
      restaurantId: Number
    },
    keyword: '',
    loading: false,
    foodList: []

  }), mounted() {
    this.info = getRestaurantInfo()
  }, methods: {
    handleSearch(keyword) {
      this.loading = true

      const spellRegex = new RegExp('^[a-zA-Z]+$')
      const nameRegex = new RegExp('^[\u4E00-\u9FA5]+$')

      let requestResult = null

      if (spellRegex.test(keyword)) {
        // 菜品拼音
        requestResult = getFoodList(this.info.restaurantId, keyword, null)
      } else if (nameRegex.test(keyword)) {
        // 菜品名称
        requestResult = getFoodList(this.info.restaurantId, null, keyword)
      } else {
        // 错了
        this.$notify({
          title: '警告',
          message: '搜索词非菜品名或拼音',
          type: 'warning'
        })

        this.loading = false
        return false
      }

      requestResult.then(resp => {
        if (!resp.data['flag']) {
          this.$notify.error({
            title: '错误',
            message: '查询菜品数据失败'
          })
        } else {
          const resultList = resp.data.data
          resultList.shift()

          this.foodList = resultList
            .map(item => item.meals)
            .reduce((arr, arrNext) => arr.concat(arrNext))
          this.loading = false
        }
      })
    },
    handleSelect(id) {
      const foodItem = this.foodList.find(item => item.id === id)
      this.callback(foodItem)

      this.keyword = ''
      this.foodList = []
    }
  }
}
</script>
