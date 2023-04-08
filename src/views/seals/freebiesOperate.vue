<template>
  <div>
    <el-form label-width="120px">
      <el-form-item label="消费金额">
        <el-input-number v-model="priceData" :precision="2" :step="1" />
      </el-form-item>
      <SearchBar :callback="handleSearch" />
      <el-form-item>
        <el-table :data="mealList">
          <el-table-column prop="name" label="菜品" />
          <el-table-column label="味型">
            <template slot-scope="scope">
              <el-select v-model="scope.row.selected">
                <el-option
                  v-for="taste in scope.row['tastes']"
                  :key="taste['id']"
                  :label="taste['name']"
                  :value="taste['id']"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="text" @click="handleMealDelete(scope.row['id'])"> 删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSave()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import SearchBar from '@/components/Search/searchBar'

export default {
  name: 'FreebiesOperate',
  components: { SearchBar },
  props: {
    onSave: {
      type: Function,
      default: () => console.log('default')
    }
  },
  data: () => ({
    displaySearch: false,
    mealList: [],
    priceData: 0
  }),
  methods: {
    handleSave() {
      const list = this.mealList
        .map(x => ({
          mealId: x.id,
          specificationId: x['specifications'][0]['id'],
          tasteId: x['selected'],
          count: 1
        }))
      this.onSave(this.priceData, list)
      this.mealList = []
      this.priceData = 0
    },
    handleSearch(foodItem) {
      this.mealList.push(foodItem)
    },
    handleMealDelete(foodId) {
      this.mealList.splice(
        this.mealList.indexOf(
          this.mealList.find(x => x.id === foodId)), 1)
    }
  }
}
</script>
