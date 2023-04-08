<template>
  <el-main>
    <el-row :gutter="16">
      <el-col :span="12">
        <el-form label-width="80px">
          <el-form-item label="名称">
            <el-input v-model="newFoodItem.name" style="width: auto" />
          </el-form-item>
          <el-form-item label="介绍">
            <el-input v-model="newFoodItem.description" style="width: auto" />
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model="newFoodItem.specifications[0].price" style="width: auto" />
          </el-form-item>
          <el-form-item label="口味">
            <el-select v-model="newFoodItem['selectedTaste']" style="width: 138px" placeholder="口味">
              <el-option
                v-for="item in newFoodItem['tastes']"
                :key="item.name"
                :label="item.name"
                :value="item.name"
              />
            </el-select>
            <el-button-group>
              <el-button
                type="primary"
                size="small"
                icon="el-icon-plus"
                @click="handleTasteAdd"
              />
              <el-button
                type="danger"
                size="small"
                icon="el-icon-minus"
                @click="handleTasteDelete(newFoodItem['tastes'], newFoodItem['selectedTaste'])"
              />
            </el-button-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-circle-check" @click="handleFoodAdd">提交菜品
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <el-col :span="12" />
    </el-row>
  </el-main>
</template>

<script>
export default {
  name: 'FoodAdd',
  props: {
    onSubmit: {
      type: Promise,
      default: () => console.log('default')
    },
    catId: {
      type: Number,
      default: null
    }
  },
  data: () => ({
    pictureList: [],
    pictureIdList: [],
    newFoodItem: {
      name: '新建菜品',
      description: '',
      active: false,
      special: false,
      specifications: [{
        name: '默认',
        price: 0
      }],
      materialList: [],
      tastes: []
    }
  }),
  methods: {
    handleFoodAdd() {
      if (this.catId !== null) {
        this.newFoodItem.categoryId = this.catId
      }
      this.onSubmit(this.newFoodItem)
        .then(() => {
          this.pictureList = []
          this.pictureIdList = []
          this.newFoodItem = {
            name: '新建菜品',
            description: '',
            active: false,
            special: false,
            specifications: [{
              name: '默认',
              price: 0
            }],
            materialList: [],
            tastes: []
          }
        })
    },
    handleTasteAdd() {
      this.$prompt('请输入自定义口味', '添加口味', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        this.newFoodItem.tastes.push({
          name: value
        })
        this.$notify({
          type: 'success',
          message: `添加口味${value}`
        })
      })
    },
    handleTasteDelete(name) {
      const list = this.newFoodItem.tastes
      this.newFoodItem.tastes = list.filter(x => x.name !== name)
    }
  }
}
</script>
