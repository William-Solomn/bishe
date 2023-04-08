<template>
  <el-main>
    <el-dialog :visible.sync="displayMaterial">
      <MaterialManage />
    </el-dialog>
    <el-form inline label-width="80px">
      <el-form-item label="名称/拼音">
        <SearchItem :on-search="handleSearch" :on-cancel="handleSearchCancel" />
      </el-form-item>
      <el-form-item label="筛选">
        <el-select v-model="filterInEdit" @change="handleFilter">
          <el-option value="0" label="全部" />
          <el-option value="1" label="售卖中" />
          <el-option value="2" label="已下架" />
        </el-select>
      </el-form-item>
      <div style="float: right">
        <el-form-item>
          <el-button type="primary" @click="displayMaterial = true">菜品材料管理</el-button>
        </el-form-item>
      </div>
    </el-form>
    <el-tabs v-model="foodCatIndex" class="main" type="border-card">
      <!-- cat tabs -->
      <el-tab-pane v-for="(catItem,i) in foodDisplayList" :key="i" :label="catItem.name">
        <!-- form line -->
        <el-row :gutter="16">
          <el-form inline>
            <div style="float: right">
              <el-form-item>
                <el-button type="primary" @click="handleFoodAddClick(catItem)"><i class="el-icon-dish" />添加菜品</el-button>
              </el-form-item>
              <el-form-item>
                <OperateCat :on-change="handleCatChanged" :cat-id="catItem['categoryId']" />
              </el-form-item>
            </div>
          </el-form>
        </el-row>
        <!-- food list -->
        <el-row v-for="(foodItem) in catItem.meals" :key="foodItem.id" :gutter="16" :span="6">
          <!-- food card -->
          <el-card class="box-card">
            <div class="text item">
              <el-row :gutter="16">
                <el-col :span="13">
                  <el-row>
                    <el-form inline label-width="64px">
                      <el-form-item label="名称">
                        <el-input v-model="foodItem.name" />
                      </el-form-item>
                      <el-form-item label="介绍">
                        <el-input v-model="foodItem.description" style="width: auto" />
                      </el-form-item>
                    </el-form>
                  </el-row>
                  <el-row>
                    <el-form inline label-width="64px">
                      <el-form-item label="口味">
                        <el-select v-model="foodItem['selectedTaste']" style="width: 138px" placeholder="口味">
                          <el-option
                            v-for="item in foodItem['tastes']"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                          />
                        </el-select>
                        <el-button-group>
                          <el-button type="primary" size="small" icon="el-icon-plus" @click="handleTasteAdd(foodItem)" />
                          <el-button
                            type="danger"
                            size="small"
                            icon="el-icon-minus"
                            @click="handleTasteDelete(foodItem, foodItem['selectedTaste'])"
                          />
                        </el-button-group>
                      </el-form-item>
                      <el-form-item label="食材">
                        <el-select v-model="foodItem['selectedMaterial']" style="width: 138px" placeholder="食材">
                          <el-option
                            v-for="item in foodItem['materials']"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                          />
                        </el-select>
                        <el-button-group>
                          <el-button
                            type="primary"
                            size="small"
                            icon="el-icon-plus"
                            @click="handleFoodMaterialsAddClick(foodItem)"
                          />
                          <el-button
                            type="danger"
                            size="small"
                            icon="el-icon-minus"
                            @click="handleFoodMaterialsDelete(foodItem, foodItem['selectedMaterial'])"
                          />
                        </el-button-group>
                      </el-form-item>
                    </el-form>
                  </el-row>
                  <el-row>
                    <el-form inline label-width="64px">
                      <el-form-item label="价格">
                        <el-input v-model="foodItem.specifications[0].price" />
                      </el-form-item>

                      <el-form-item label="">
                        <el-switch
                          v-model="foodItem.active"
                          inactive-text="下架"
                          active-text="上架"
                          @change="handleFoodUpdate(foodItem)"
                        />
                      </el-form-item>
                    </el-form>
                  </el-row>
                  <el-row>
                    <el-form inline label-width="64px">
                      <el-form-item>
                        <el-switch
                          v-model="foodItem.special"
                          active-text="特色菜"
                          @change="handleFoodActiveClick(foodItem)"
                        />
                      </el-form-item>
                      <el-form-item>
                        <el-button type="success" @click="changeAbout(foodItem)">设关联菜</el-button>
                      </el-form-item>
                      <el-divider direction="vertical" />
                      <el-form-item>
                        <el-button type="primary" icon="el-icon-circle-check" @click="handleFoodUpdate(foodItem)">
                          保存修改
                        </el-button>
                      </el-form-item>
                      <el-form-item>
                        <el-popconfirm title="确定要删除菜品吗？" @confirm="handleFoodDelete(foodItem.id)">
                          <el-button slot="reference" icon="el-icon-delete" type="danger">
                            删除菜品
                          </el-button>
                        </el-popconfirm>
                      </el-form-item>
                    </el-form>
                  </el-row>
                </el-col>
                <el-col :span="11">
                  <el-upload
                    action="https://bfjdkm.com:9002/file/upload/single"
                    :limit="3"
                    :on-success="handlePictureAdd(foodItem.id)"
                    :on-remove="handlePictureRemove(foodItem.id)"
                    :file-list="foodPictureList[foodItem.id]"
                    list-type="picture-card"
                  >
                    <i class="el-icon-upload" />
                  </el-upload>

                </el-col>
              </el-row>
            </div>
          </el-card>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <el-dialog :modal="false" title="选择食材" :visible.sync="displayMaterialSelect">
      <MaterialSelect :on-select="handleMaterialsAdd" />
    </el-dialog>

    <!-- New food dialog -->
    <el-dialog :modal="false" title="新建菜品" :visible.sync="displayFoodAdd">
      <FoodAdd :cat-id="catIdInEdit" :on-submit="handleFoodAdd()" />
    </el-dialog>
    <el-dialog title="设置关联菜" :visible.sync="dialogFormVisible">
      <h3>{{ changingAboutFood.name }}</h3>
      <el-form :model="changingAboutFood">
        <el-form-item label="搜索菜品">
          <SearchBar :callback="showAboutWord" :on-search="handleAboutSearch" />
        </el-form-item>
        <!-- <el-form-item label="搜索结果">
          <el-table :data="searchAboutResult" border style="width: 100%">
            <el-table-column prop="name" label="菜名" width="120" />
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" @click="showAboutWord(scope.$index, scope.row)">添加</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item> -->

        <el-form-item label="关联菜">
          <el-table :data="changingAboutFood.about" border style="width: 100%">
            <el-table-column prop="info.name" label="菜名" width="120" />
            <el-table-column prop="str" label="推荐语" width="120" />
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini" type="danger" @click="deleteAbout(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>

<script>
import { getRestaurantInfo } from '@/utils/auth'
import { handleCurrent } from '@/utils/displayUtil'
import { addFood, addFoodPicture, deleteFood, getFoodDetail, getFoodList, getFoodPicture, updateFood } from '@/api/food'
import SearchBar from '@/components/Search/searchBar'
import OperateCat from '@/views/foods/operateCat'
import MaterialManage from '@/views/foods/materialManage'
import MaterialSelect from '@/views/foods/materialSelect'
import FoodAdd from '@/views/foods/foodAdd'
import Vue from 'vue'
import { getAbout, setAbout } from '@/api/about'
import SearchItem from '@/components/Search/searchItem'

export default {
  components: { SearchItem, FoodAdd, MaterialSelect, MaterialManage, OperateCat, SearchBar },
  data: () => ({
    info: {
      restaurantId: Number
    },
    searchAboutResult: [],
    changingAboutFood: {},
    foodInEdit: {},
    catIdInEdit: 0,
    filterInEdit: '0',
    searchInput: '',
    loading: true,
    displayFoodAdd: false,
    displayMaterial: false,
    displayMaterialSelect: false,
    foodList: [],
    foodDisplayList: [],
    foodCatIndex: 0,
    foodPictureList: {},
    pictureAddIdList: [],
    pictureRemoveIdList: [],
    tempFoodList: [],
    searchDisplayLock: false,
    dialogFormVisible: false
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleFoodList()
  },
  methods: {
    showAboutWord(info) {
      console.log(info)
      this.$prompt('请输入推荐语', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        this.addAbout({ ...info, str: value })
      })
    },
    addAbout(info) {
      if (this.changingAboutFood.about.length >= 2) {
        this.$alert('关联菜已达上限', '', {
          confirmButtonText: '确定'
        })
      } else {
        const that = this
        setAbout({
          relateId: this.changingAboutFood.relateId,
          removeList: null,
          modifyList: null,
          addList: [{
            id: info.id,
            str: info.str,
            tasteId: info.tastes[0].id,
            specificationId: info.specifications[0].id
          }]
        }).then(() => {
          getAbout(that.info.restaurantId, that.changingAboutFood.id)
            .then((res) => {
              that.changingAboutFood.relateId = res.data[0].id
              that.changingAboutFood.about = res.data[0]['validItems']
              that.loading = false
              that.dialogFormVisible = false
              that.dialogFormVisible = true
            })
        })
      }
    },
    deleteAbout(index, info) {
      const that = this
      setAbout({
        relateId: this.changingAboutFood.relateId,
        removeList: [info.id],
        addList: null,
        modifyList: null
      }).then(() => {
        getAbout(that.info.restaurantId, that.changingAboutFood.id)
          .then((res) => {
            that.changingAboutFood.relateId = res.data[0].id
            that.changingAboutFood.about = res.data[0]['validItems']
            that.loading = false
            that.dialogFormVisible = false
            that.dialogFormVisible = true
          })
      })
    },
    handleAboutSearch(res) {
      this.searchAboutResult = res[0].meals
      this.dialogFormVisible = false
      this.dialogFormVisible = true
    },
    changeAbout(foodItem) {
      this.changingAboutFood = foodItem
      getAbout(this.info.restaurantId, foodItem.id).then((res) => {
        this.changingAboutFood.relateId = res.data[0].id
        this.changingAboutFood.about = res.data[0]['validItems']
        this.loading = false
        this.dialogFormVisible = true
      })
    },
    async handlePictureList(foodId) {
      const result = await getFoodPicture(this.info.restaurantId, foodId)
      Vue.set(this.foodPictureList, foodId, result)
    },
    handlePictureRemove(foodId) {
      return (pic) => {
        const itemInAddList = this.pictureAddIdList[foodId]?.find(x => x.id === pic.id)

        if (!itemInAddList) {
          if (!this.pictureRemoveIdList[foodId]?.length) {
            this.pictureRemoveIdList[foodId] = [pic.id]
          } else {
            this.pictureRemoveIdList[foodId].push(pic.id)
          }
        } else {
          this.pictureAddIdList[foodId].splice(
            this.pictureAddIdList[foodId].indexOf(itemInAddList)
          )
        }
      }
    },
    handlePictureAdd(mealId) {
      return (resp) => {
        if (!this.pictureAddIdList[mealId]) {
          this.pictureAddIdList[mealId] = [resp['data']]
        } else {
          this.pictureAddIdList[mealId].push(resp['data'])
        }
      }
    },
    handleFoodList() {
      getFoodList(this.info.restaurantId)
        .then(resp => {
          const flag = resp.data['flag']

          if (flag) {
            this.foodDisplayList = resp.data['data']
            this.foodDisplayList.forEach(cat => {
              cat['originMeals'] = Array.from(cat.meals)
            })
            this.changingAboutFood = resp.data.data[0]

            const idList = this.foodDisplayList
              .map(cat => cat.meals)
              .reduce((arr, arrNext) => arr.concat(arrNext))
              .map(meal => meal.id)

            const sentList = []
            idList.forEach(async id => {
              if (sentList.indexOf(id) === -1) {
                // 没有请求过
                sentList.push(id)
                await this.handlePictureList(id)
              }
            })
          } else {
            this.$notify.error({
              title: '错误',
              message: resp.data['message']
            })
          }
        })
    },
    handlePrice: (num) => handleCurrent(num),
    handleCatChanged() {
      return this.handleFoodList()
    },
    handleFoodAdd() {
      const that = this
      return (foodItem) => {
        foodItem['categoryId'] = that.catIdInEdit
        return addFood(this.info.restaurantId, foodItem)
          .then(resp => {
            const flag = resp.data['flag']

            this.$notify({
              title: flag ? '成功' : '错误',
              type: flag ? 'success' : 'error',
              message: resp.data['message']
            })

            if (flag) {
              that.handleFoodList()
              that.displayFoodAdd = false
            }
          })
      }
    },
    handleFoodAddClick(catItem) {
      this.catIdInEdit = catItem.categoryId
      this.displayFoodAdd = true
    },
    // handleFoodSetSpecial(foodItem) {
    //   this.handleFoodUpdate(foodItem)
    // },
    handleFoodDelete(foodId) {
      deleteFood(this.info.restaurantId, foodId)
        .then(resp => {
          const flag = resp.data['flag']
          this.$notify({
            title: flag ? '成功' : '错误',
            type: flag ? 'success' : 'error',
            message: resp.data['message']
          })

          if (flag) {
            this.foodDisplayList.forEach(cat => {
              cat.meals = cat['originMeals'].filter(x => x.id !== foodId)
            })
          }
        })
    },
    handleFoodActiveClick(foodItem) {
      const that = this
      this.handleFoodUpdate(foodItem)
        .then(() => {
          if (foodItem.special) {
            // 设置为特色菜
            that.handleFoodList()
          } else {
            const list = that.foodDisplayList.find(x => x.name === '特色菜')['originMeals']
            const item = list.find(x => x.id === foodItem.id)
            const index = list.indexOf(item)
            list.splice(index, 1)
          }
        })
    },
    handleFoodUpdate(foodItem) {
      const that = this
      return updateFood(this.info.restaurantId, foodItem)
        .then(resp => {
          const flag = resp.data['flag']

          this.$notify({
            title: flag ? '成功' : '错误',
            type: flag ? 'success' : 'error',
            message: resp.data['message']
          })

          if (flag) {
            if (
              (!that.pictureAddIdList[foodItem.id] ||
                !that.pictureRemoveIdList[foodItem.id])) {
              addFoodPicture(
                that.info.restaurantId,
                foodItem.id,
                that.pictureAddIdList[foodItem.id],
                that.pictureRemoveIdList[foodItem.id])
            }

            getFoodDetail(that.info.restaurantId, foodItem.id)
              .then(resp => {
                const flag = resp.data['flag']
                if (flag) {
                  const updatedItem = resp.data['data']
                  for (const key in updatedItem) {
                    foodItem[key] = updatedItem[key]
                  }
                } else {
                  this.$notify({
                    type: 'error',
                    title: '更新菜品失败',
                    message: resp.data['message']
                  })
                }
              })

            foodItem['addTasteList'] = []
            foodItem['removeTastes'] = []
            foodItem['addMaterialList'] = []
            foodItem['removeMaterials'] = []
          }
        })
    },
    handleTasteAdd(foodItem) {
      this.$prompt('请输入自定义口味', '添加口味', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        const item = { name: value, id: `T${value}` }
        if (foodItem['tastes'].find(x => x.name === value)) {
          // 重复添加
          this.$notify({
            type: 'error',
            message: `无法添加，重复口味`
          })
        } else {
          // 不重复
          if (!foodItem['addTasteList'] || foodItem['addTasteList'].length === 0) {
            foodItem['addTasteList'] = [item]
          } else {
            foodItem['addTasteList'].push(item)
          }
          foodItem['tastes'].push(item)
        }

        this.$notify({
          type: 'success',
          message: `添加口味${value}，保存后生效`
        })
      })
    },
    handleTasteDelete(foodItem, id) {
      if (isNaN(id)) {
        foodItem['addTasteList'] = foodItem['addTasteList'].filter(x => x.id !== id)
      } else {
        if (!foodItem['removeTastes'] || foodItem['removeTastes'].length === 0) {
          foodItem['removeTastes'] = [id]
        } else {
          foodItem['removeTastes'].push(id)
        }
      }

      foodItem['tastes'] = foodItem['tastes'].filter(x => x.id !== id)
      foodItem['selectedTaste'] = null
    },
    handleFoodMaterialsAddClick(foodItem) {
      this.foodInEdit = foodItem
      this.displayMaterialSelect = true
    },
    handleMaterialsAdd(material) {
      if (this.foodInEdit['materials'].find(x => x.id === material.id)) {
        // 重复添加
        this.$notify({
          type: 'error',
          message: '无法添加，重复材料'
        })
      } else {
        // 数据库内不重复
        if (!this.foodInEdit['addMaterialList'] || this.foodInEdit['addMaterialList'].length === 0) {
          // 无列表
          this.foodInEdit['addMaterialList'] = [material.id]
        } else {
          this.foodInEdit['addMaterialList'].push(material.id)
        }
        this.foodInEdit['materials'].push(material)
      }

      this.$notify({
        type: 'success',
        message: `添加材料${material.name}，保存后生效`
      })
      this.displayMaterialSelect = false
      this.foodInEdit = {}
    },
    handleFoodMaterialsDelete(foodItem, materialId) {
      const indexOfAddList = foodItem['addMaterialList']?.indexOf(materialId)
      if (!foodItem['addMaterialList'] || foodItem['addMaterialList'].length === 0 || indexOfAddList === -1) {
        // 根本没添加
        if (!foodItem['removeMaterials'] || foodItem['removeMaterials'].length === 0) {
          foodItem['removeMaterials'] = [materialId]
        } else {
          foodItem['removeMaterials'].push(materialId)
        }
      } else {
        // 使用魔法判断是否找到
        foodItem['addMaterialList'].splice(indexOfAddList, 1)
      }
      foodItem['materials'] = foodItem['materials'].filter(x => x.id !== materialId)
      foodItem['selectedMaterial'] = null
    },
    handleSearch(result) {
      if (!this.searchDisplayLock) {
        this.tempFoodList = this.foodDisplayList
        this.searchDisplayLock = true
      }
      this.foodDisplayList = result
      document.getElementById('tab-0').click()
    },
    handleSearchCancel() {
      this.foodDisplayList = this.tempFoodList
      this.tempFoodList = []
      this.searchDisplayLock = false
    },
    handleFilter(option) {
      this.foodDisplayList.forEach(cat => {
        cat.meals = Array.from(cat['originMeals'])
      })

      if (option === '1') {
        this.foodDisplayList.forEach(cat => {
          cat.meals = cat['originMeals'].filter(m => m.active === true)
        })
      } else if (option === '2') {
        this.foodDisplayList.forEach(cat => {
          cat.meals = cat['originMeals'].filter(m => m.active === false)
        })
      }
    }
  }
}
</script>

<style>
.box-card {
  margin-bottom: 16px;
}
</style>
