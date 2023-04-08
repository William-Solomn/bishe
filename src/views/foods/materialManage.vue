<template>
  <div>
    <el-button type="primary" @click="handleMaterialsAdd">添加</el-button>
    <el-table
      :data="materialList"
      height="400px"
      width="100%"
    >
      <el-table-column prop="name" label="材料" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="handleMaterialsDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { addMaterial, deleteMaterial, getMaterial } from '@/api/food'
import { getRestaurantInfo } from '@/utils/auth'

export default {
  name: 'MaterialManage',
  data: () => ({
    materialList: [],
    info: {
      restaurantId: -1
    }
  }), mounted() {
    this.info = getRestaurantInfo()
    this.handleMaterialsList()
  }, methods: {
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
    handleMaterialsDelete(material) {
      if (material.id) {
        deleteMaterial(this.info.restaurantId, material.id)
          .then(resp => {
            const flag = resp.data['flag']
            this.$notify({
              title: flag ? '成功' : '错误',
              type: flag ? 'success' : 'error',
              message: resp.data['message']
            })

            if (flag) {
              const index = this.materialList.indexOf(material)
              this.materialList.splice(index, 1)
            }
          })
      } else {
        const index = this.materialList.indexOf(material)
        this.materialList.splice(index, 1)
      }
    },
    handleMaterialsAdd() {
      this.$prompt('请输入材料名', '添加材料', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        addMaterial(this.info.restaurantId, value)
          .then(resp => {
            const flag = resp.data['flag']
            this.$notify({
              type: flag ? 'success' : 'error',
              message: resp.data['message'],
              title: flag ? '成功' : '错误'
            })
            if (flag) {
              this.materialList.push({
                name: value
              })
            }
          })
      })
    }
  }
}
</script>
