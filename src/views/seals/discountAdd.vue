<template>
  <el-form label-width="120px">
    <el-form-item label="消费金额">
      <el-input-number v-model="priceData" :precision="2" :step="1" />
    </el-form-item>
    <el-form-item label="减免金额">
      <el-input-number v-model="discountData" :precision="2" :step="1" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleSave()">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: 'DiscountAdd',
  props: {
    onSave: {
      type: Function,
      default: () => console.log('default')
    }
  },
  data: () => ({
    priceData: 0,
    discountData: 0
  }),
  methods: {
    handleSave() {
      this.onSave(this.priceData, this.discountData)
    }
  }

}
</script>
