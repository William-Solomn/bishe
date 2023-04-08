<template>
  <el-input v-model="keyword" :onchange="handleSearch(keyword)" />
</template>

<script>
import { getFoodList } from '@/api/food'
import { getRestaurantInfo } from '@/utils/auth'

export default {
  name: 'SearchItem',
  props: {
    onSearch: {
      type: Function,
      default: () => {
        console.log('default')
      }
    },
    onCancel: {
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
    resultList: [],
    lock: true
  }), mounted() {
    this.info = getRestaurantInfo()
  }, methods: {
    handleSearch(keyword) {
      const spellRegex = new RegExp('^[a-zA-Z]+$')
      const nameRegex = new RegExp('^[\u4E00-\u9FA5]+$')

      let requestResult = null

      if (keyword === '') {
        if (!this.lock) {
          this.lock = true
          this.onCancel()
        }
        return false
      } else if (spellRegex.test(keyword)) {
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
        return false
      }

      this.lock = false
      requestResult.then(resp => {
        if (!resp.data['flag']) {
          this.$notify.error({
            title: '错误',
            message: '查询菜品数据失败'
          })
        } else {
          this.resultList = resp.data.data
            .filter(cat => cat.name !== '特色菜')
            .map(item => item.meals)
            .reduce((arr, arrNext) => arr.concat(arrNext))
          this.onSearch([{ name: '搜索结果', meals: this.resultList }])
          this.resultList = []
        }
      })
    }
  }
}
</script>
