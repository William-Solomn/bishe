<template>
  <el-main>
    <div class="main">
      <el-row :gutter="16">
        <div style="float: right">
          <el-form inline>
            <el-form-item>
              <el-button type="primary" @click="displayComboAdd = true">添加套餐</el-button>
            </el-form-item>
          </el-form>
        </div>
        <el-dialog title="新建套餐" :visible.sync="displayComboAdd">
          <ComboAdd :callback="handleComboAdd" />
        </el-dialog>
      </el-row>
      <!-- package list -->
      <el-row v-for="(comboItem) in comboList" :key="comboItem.id" :gutter="16">
        <!-- package card -->
        <el-card class="box-card">
          <el-row>
            <el-col :span="13">
              <el-row>
                <el-form inline label-width="70px">
                  <el-form-item label="名称">
                    <el-input v-model="comboItem.name" />
                  </el-form-item>
                  <el-form-item label="价格">
                    <el-input v-model="comboItem.price" />
                  </el-form-item>
                </el-form>
              </el-row>
              <el-row>
                <el-form label-width="70px" inline>
                  <el-form-item label="介绍">
                    <el-input
                      v-model="comboItem.description"
                      style="min-width: 420px"
                      :autosize="{ minRows: 2, maxRows: 4}"
                      type="textarea"
                    />
                  </el-form-item>
                </el-form>
              </el-row>
              <el-row>
                <el-form label-width="70px" inline>
                  <el-form-item label="包含菜品">
                    <el-select v-model="comboItem['selectMeal']" placeholder="菜品">
                      <el-option
                        v-for="(item, i) in comboItem.items"
                        :key="i"
                        :label="item.meal['name']"
                        :value="item.meal['id']"
                      />
                    </el-select>
                    <el-button-group>
                      <el-button
                        type="primary"
                        size="small"
                        icon="el-icon-plus"
                        @click="handleMealAddClick(comboItem)"
                      />
                      <el-button
                        type="danger"
                        size="small"
                        icon="el-icon-minus"
                        @click="handleMealDelete(comboItem, comboItem['selectMeal'])"
                      />
                    </el-button-group>
                  </el-form-item>
                  <!-- Add food to combo -->

                  <el-form-item>
                    <el-button type="danger" @click="handleComboDelete(comboItem.id)">删除套餐</el-button>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="handleComboUpdate(comboItem)">保存修改</el-button>
                  </el-form-item>
                </el-form>
              </el-row>
            </el-col>
            <el-col :span="11">
              <el-upload
                action="https://bfjdkm.com:9002/file/upload/single"
                :limit="3"
                :on-success="handlePictureAdd(comboItem.id)"
                :on-remove="handlePictureRemove(comboItem.id)"
                :file-list="comboPictureList[comboItem.id]"
                list-type="picture-card"
              >
                <i class="el-icon-upload" />
              </el-upload>
            </el-col>
          </el-row>
        </el-card>
      </el-row>
      <el-dialog :visible.sync="displayFoodAdd" :modal="false" title="添加菜品">
        <el-form inline>
          <SearchBar :callback="handleMealAdd" />
        </el-form>
      </el-dialog>
    </div>
  </el-main>
</template>

<script>
import { addComboPicture, deleteCombo, getComboList, getComboPicture, updateCombo } from '@/api/food'
import { getRestaurantInfo } from '@/utils/auth'
import { handleCurrent } from '@/utils/displayUtil'
import ComboAdd from '@/views/foods/comboAdd'
import SearchBar from '@/components/Search/searchBar'
import Vue from 'vue'

export default {
  components: { ComboAdd, SearchBar },
  data: () => ({
    info: {
      restaurantId: 0
    },
    comboList: [],
    pictureAddIdList: [],
    pictureRemoveIdList: [],
    comboPictureList: {},
    comboInEdit: {},
    displayComboAdd: false,
    displayFoodAdd: false
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleComboList()
  },
  methods: {
    async handlePictureList(comboId) {
      const that = this
      const result = await getComboPicture(this.info.restaurantId, comboId)
      Vue.set(that.comboPictureList, comboId, result)
    },
    async handleComboList() {
      const response = (await getComboList(this.info.restaurantId))
      if (response['flag']) {
        this.comboList = response['data']
        for (const combo of this.comboList) {
          await this.handlePictureList(combo.id)
        }
      }
    },
    handlePrice(num) {
      return handleCurrent(num)
    },
    handlePictureRemove(comboId) {
      return (pic) => {
        const itemInAddList = this.pictureAddIdList[comboId]?.find(x => x.id === pic.id)

        if (!itemInAddList) {
          if (!this.pictureRemoveIdList[comboId]?.length) {
            this.pictureRemoveIdList[comboId] = [pic.id]
          } else {
            this.pictureRemoveIdList[comboId].push(pic.id)
          }
        } else {
          this.pictureAddIdList[comboId].splice(
            this.pictureAddIdList[comboId].indexOf(itemInAddList)
          )
        }
      }
    },
    handlePictureAdd(comboId) {
      return (resp) => {
        if (!this.pictureAddIdList[comboId]) {
          this.pictureAddIdList[comboId] = [resp['data']]
        } else {
          this.pictureAddIdList[comboId].push(resp['data'])
        }
      }
    },
    handleMealAddClick(comboItem) {
      this.displayFoodAdd = true
      this.comboInEdit = comboItem
    },
    handleMealAdd(foodItem) {
      this.comboInEdit.items.push({
        meal: {
          id: foodItem.id,
          name: foodItem.name
        },
        specification: {
          id: foodItem.specifications[0]?.id
        },
        count: 1
      })
      this.displayFoodAdd = false
      this.comboInEdit = {}
    },
    handleMealDelete(comboItem, mealId) {
      comboItem.items = comboItem.items.filter(x => x['meal'].id !== mealId)
      comboItem['selectMeal'] = ''
    },
    handleComboDelete(comboId) {
      deleteCombo(this.info.restaurantId, comboId)
        .then(resp => {
          const flag = resp.data['flag']

          this.$notify({
            title: flag ? '成功' : '错误',
            type: flag ? 'success' : 'error',
            message: resp.data['message']
          })

          if (flag) {
            this.comboList = this.comboList.filter(x => x.id !== comboId)
          }
        })
    },
    handleComboAdd() {
      this.handleComboList()
      this.displayComboAdd = false
    },
    handleComboUpdate(comboItem) {
      comboItem['items'] = comboItem['items'].map(item => ({
        id: item['meal'].id,
        specificationId: item['specification'].id,
        count: item['count']
      }))

      updateCombo(this.info.restaurantId, comboItem)
        .then(resp => {
          const flag = resp.data['flag']

          this.$notify({
            title: flag ? '成功' : '错误',
            type: flag ? 'success' : 'error',
            message: resp.data['message']
          })

          if (flag) {
            addComboPicture(this.info.restaurantId,
              comboItem.id,
              this.pictureAddIdList[comboItem.id],
              this.pictureRemoveIdList[comboItem.id])
          }
        })
    }
  }
}
</script>

<style>
.box-card {
  margin-bottom: 16px;
}
</style>
