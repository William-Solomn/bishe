<template>
  <el-form inline label-width="80px">
    <el-form-item>
      <el-select v-model="selected">
        <el-option
          v-for="item in materialList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleMaterialSelect(selected)">确定</el-button>
    </el-form-item>

  </el-form>
</template>

<script>
import { getMaterial } from '@/api/food'
import { getRestaurantInfo } from '@/utils/auth'

export default {
  name: 'MaterialSelect',
  props: {
    onSelect: {
      type: Function,
      default: () => console.log('default')
    }
  },
  data: () => ({
    info: {
      restaurantId: 0
    },
    selected: '',
    materialList: []
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleMaterialsList()
  },
  methods: {
    handleMaterialsList() {
      getMaterial(this.info.restaurantId)
        .then(resp => {
          const flag = resp.data['flag']

          if (flag) {
            this.materialList = resp.data['data']
          } else {
            this.$notify({
              title: '错误',
              type: 'error',
              message: resp.data['message']
            })
          }
        })
    },
    handleMaterialSelect(materialId) {
      this.onSelect(
        this.materialList.find(element => element.id === materialId)
      )

      this.selected = ''
    }
  }
}
</script>
