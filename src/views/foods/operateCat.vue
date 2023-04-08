<template>
  <div>
    <el-button-group>
      <el-button type="primary" @click="handleAdd()"><i class="el-icon-plus" />添加分类</el-button>
      <el-button type="danger" @click="handleDelete(catId)">
        <i class="el-icon-delete" />删除当前分类
      </el-button>
    </el-button-group>
  </div>
</template>

<script>
import { getRestaurantInfo } from '@/utils/auth'
import { addCat, deleteCat } from '@/api/food'

export default {
  name: 'OperateCat',
  props: {
    catId: {
      type: Number,
      default: -1
    },
    onChange: {
      type: Function,
      default: () => (console.log('default'))
    }
  },
  data: () => ({
    info: {
      restaurantId: Number
    }
  }), mounted() {
    this.info = getRestaurantInfo()
  },
  methods: {
    handleAdd() {
      this.$prompt('请输入分类名称', '添加分类', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        addCat(this.info.restaurantId, value)
          .then(resp => {
            const flag = resp.data['flag']

            this.$notify({
              title: flag ? '成功' : '错误',
              message: resp.data['message'],
              type: flag ? 'success' : 'error'
            })

            if (flag) {
              this.onChange()
            }
          })
      })
    },
    handleDelete(catId) {
      deleteCat(this.info.restaurantId, catId)
        .then(resp => {
          const flag = resp.data['flag']

          this.$notify({
            title: flag ? '成功' : '错误',
            message: resp.data['message'],
            type: flag ? 'success' : 'error'
          })

          if (flag) {
            this.onChange()
          }
        })
    }
  }
}
</script>

