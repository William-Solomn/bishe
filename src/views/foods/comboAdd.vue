<template>
  <el-main>
    <el-row :gutter="16">
      <el-col :span="12">
        <el-form label-width="80px">
          <el-form-item label="名称">
            <el-input v-model="newComboItem.name" style="width: auto" />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="newComboItem.description" style="width: auto" />
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model="newComboItem.price" style="width: auto" />
          </el-form-item>
          <SearchBar :callback="handleSearch" />
          <el-form-item>
            <el-table :data="newComboItem.items">
              <el-table-column prop="name" label="菜品" />
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="text" @click="handleItemDelete(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-circle-check" @click="handleComboAdd">提交套餐
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <el-col :span="12" />
    </el-row>
  </el-main>
</template>

<script>
import SearchBar from '@/components/Search/searchBar'
import { getRestaurantInfo } from '@/utils/auth'
import { addCombo } from '@/api/food'

export default {
  name: 'ComboAdd',
  components: { SearchBar },
  props: {
    callback: {
      type: Function,
      default: () => console.log('default')
    }
  },
  data: () => ({
    info: {
      restaurantId: 0
    },
    newComboItem: {
      name: '新建套餐',
      description: '',
      price: 0,
      items: []
    }
  }),
  mounted() {
    this.info = getRestaurantInfo()
  },
  methods: {
    handleSearch(item) {
      this.newComboItem.items.push({
        id: item.id,
        count: 1,
        name: item.name,
        specificationId: item.specifications[0]?.id
      })
    },
    handleComboAdd() {
      addCombo(this.info.restaurantId, this.newComboItem)
        .then(resp => {
          const flag = resp.data['flag']

          this.$notify({
            title: flag ? '成功' : '错误',
            type: flag ? 'success' : 'error',
            message: resp.data['message']
          })

          if (flag) {
            this.callback(this.newComboItem)
            this.newComboItem = {
              name: '新建套餐',
              description: '',
              price: 0,
              items: []
            }
          }
        })
    },
    handleItemDelete(item) {
      this.newComboItem.items.splice(
        this.newComboItem.items.indexOf(item), 1
      )
    }
  }
}
</script>

<style scoped>

</style>
