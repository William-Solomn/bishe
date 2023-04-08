<template>
  <div>
    <el-button-group>
      <el-button type="primary" @click="handleAdd()"><i class="el-icon-plus" />添加区域</el-button>
      <el-button type="danger" @click="handleDelete(areaId)">
        <i class="el-icon-delete" />删除当前区域
      </el-button>
    </el-button-group>
  </div>
</template>

<script>
import { addArea, deleteArea } from '@/api/tables'
import { getRestaurantInfo } from '@/utils/auth'

export default {
  name: 'OperateArea',
  props: {
    areaId: {
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
      this.$prompt('请输入区域名称', '添加区域', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        addArea(this.info.restaurantId, value)
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
    handleDelete(areaId) {
      deleteArea(this.info.restaurantId, areaId)
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

