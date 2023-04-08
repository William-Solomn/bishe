<template>
  <el-main>
    <el-form label-width="80px">
      <el-form-item label="餐桌名">
        <el-input v-model="tableData.name" />
      </el-form-item>
      <el-form-item label="费用">
        <el-input-number v-model="tableData.price" controls-position="right" :min="0" />
      </el-form-item>
      <el-form-item label="容量">
        <el-input-number v-model="tableData.capacity" controls-position="right" :min="0" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="tableData.remark" type="textarea" :rows="4" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit()">{{ opName }}</el-button>
        <el-button @click="handleCancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-main>
</template>

<script>
export default {
  name: 'OperateTable',
  props: {
    onSubmit: {
      type: Function,
      default: () => {
        console.log('Undefined function')
      }
    },
    onCancel: {
      type: Function,
      default: () => {
        console.log('Undefined function')
      }
    },
    opName: {
      type: String,
      default: '确定'
    },
    areaId: {
      type: Number,
      default: -1
    },
    tableData: {
      type: Object,
      default() {
        return ({
          name: '',
          price: 0,
          remark: '',
          capacity: 0,
          tableTypeId: -1
        })
      }
    }
  }, methods: {
    handleSubmit() {
      if (this.tableData.tableTypeId === -1) {
        this.tableData.tableTypeId = this.areaId
      }
      this.onSubmit(this.tableData)
    },
    handleCancel() {
      this.onCancel()
    }
  }
}
</script>

<style scoped>

</style>
