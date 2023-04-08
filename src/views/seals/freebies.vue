<template>
  <el-main>
    <el-card>
      <el-form inline label-width="64px">
        <el-form-item>
          <el-button type="primary" @click="handleFreebiesAddClick">增加规则</el-button>
        </el-form-item>
      </el-form>

      <el-table max-height="480" :data="ruleList">
        <el-table-column label="启用">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.info.active"
              active-text="启用"
              @change="handleFreebiesActiveClick(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="info.consumption" label="消费金额" />
        <el-table-column label="赠送菜品">
          <template slot-scope="scope">
            {{ handleFoodDisplay(scope.row) }}
          </template>
        </el-table-column>
        <el-table-column width="160" label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="handleFreebiesDelete(scope.row.info['id'])">删除</el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-card>
    <el-dialog title="增加规则" :modal="false" :visible.sync="displayFreebiesAdd">
      <FreebiesOperate :on-save="handleFreebiesAdd()" />
    </el-dialog>
  </el-main>
</template>

<script>
import { getRestaurantInfo } from '@/utils/auth'
import {
  getFreebiesList,
  deleteFreebies, addFreebies, updateFreebiesActive
} from '@/api/seals'
import FreebiesOperate from '@/views/seals/freebiesOperate'

export default {
  name: 'Freebies',
  components: { FreebiesOperate },
  data: () => ({
    info: {
      restaurantId: 0
    },
    ruleList: [], ruleInEdit: 0,
    displayFreebiesAdd: false
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleFreebiesList()
  },
  methods: {
    handleFoodDisplay(row) {
      const list = row?.validItems.map(x => x['mealName'])

      let resultStr = ''
      if (list.length === 0) {
        resultStr = '无'
      } else if (list.length <= 2) {
        resultStr = list.join(',')
      } else {
        resultStr = list.slice(0, 3).join(',') + '...'
      }
      return resultStr
    },
    handleFreebiesList() {
      getFreebiesList(this.info.restaurantId).then(resp => {
        this.ruleList = resp.data['data']
      })
    },
    async handleFreebiesDelete(id) {
      const result = (await deleteFreebies(this.info.restaurantId, id)).data
      const flag = result['flag']
      this.$notify({
        name: `操作${flag ? '成功' : '失败'}`,
        type: flag ? 'success' : 'error',
        message: result['message']
      })

      if (flag) {
        this.ruleList = this.ruleList.filter(x => x.info.id !== id)
      }
    },
    handleFreebiesAdd() {
      const that = this
      return (price, itemList) => {
        addFreebies(this.info.restaurantId, price, itemList).then(
          resp => {
            const result = resp.data
            const flag = result['flag']
            this.$notify({
              name: `操作${flag ? '成功' : '失败'}`,
              type: flag ? 'success' : 'error',
              message: result['message']
            })

            if (flag) {
              that.displayFreebiesAdd = false
              that.handleFreebiesList()
            }
          }
        )
      }
    },
    handleFreebiesAddClick() {
      this.displayFreebiesAdd = true
    },
    async handleFreebiesActiveClick(ruleItem) {
      const result = (await updateFreebiesActive(this.info.restaurantId, ruleItem.info.id, ruleItem.info.active)).data
      if (!result['flag']) {
        this.$notify({
          name: '操作失败',
          type: 'error',
          message: result['message']
        })
      }
    }
  }
}
</script>
