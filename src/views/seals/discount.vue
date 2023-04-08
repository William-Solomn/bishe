<template>
  <el-main>
    <el-card>
      <el-form inline label-width="64px">
        <el-form-item>
          <el-button type="primary" @click="displayDiscountAdd = true">增加规则</el-button>
        </el-form-item>
      </el-form>

      <el-table max-height="480" :data="ruleList">
        <el-table-column label="启用">
          <template slot-scope="scope">
            <el-switch v-model="scope.row['active']" active-text="启用" @change="handleFreebiesActiveClick(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column prop="consumption" label="消费金额" />
        <el-table-column prop="discount" label="减免金额" />
        <el-table-column width="160" label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="handleDiscountEditClick(scope.row)">编辑</el-button>
            <el-button type="text" @click="handleDiscountDelete(scope.row['id'])">删除</el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-card>
    <el-dialog title="增加规则" :modal="false" :visible.sync="displayDiscountAdd">
      <DiscountAdd :on-save="handleDiscountAdd" />
    </el-dialog>
    <el-dialog title="修改规则" :modal="false" :visible.sync="displayDiscountEdit">
      <el-form label-width="120px">
        <el-form-item label="消费金额">
          <el-input-number v-model="ruleInEdit.consumption" :precision="2" :step="1" />
        </el-form-item>
        <el-form-item label="减免金额">
          <el-input-number v-model="ruleInEdit.discount" :precision="2" :step="1" />
        </el-form-item>
        <el-form-item label="是否启用">
          <el-switch v-model="ruleInEdit.active" inactive-text="未启用" active-text="启用" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleDiscountEdit">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </el-main>
</template>

<script>
import { getRestaurantInfo } from '@/utils/auth'
import { addDiscount, deleteDiscount, getDiscountList, updateDiscount } from '@/api/seals'
import DiscountAdd from '@/views/seals/discountAdd'

export default {
  name: 'Discount',
  components: { DiscountAdd },
  data: () => ({
    info: {
      restaurantId: 0
    },
    ruleInEdit: 0,
    ruleList: [],
    displayDiscountAdd: false,
    displayDiscountEdit: false
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleDiscountList()
  },
  methods: {
    handleDiscountList() {
      getDiscountList(this.info.restaurantId).then(resp => {
        this.ruleList = resp.data['data']['list']
      })
    },
    async handleDiscountDelete(id) {
      const result = (await deleteDiscount(this.info.restaurantId, id)).data
      const flag = result['status'] === 204
      this.$notify({
        name: `操作${flag ? '成功' : '失败'}`,
        type: flag ? 'success' : 'error',
        message: result['msg']
      })

      if (flag) {
        this.ruleList = this.ruleList.filter(x => x.id !== id)
      }
    },
    handleDiscountEditClick(ruleItem) {
      this.displayDiscountEdit = true
      this.ruleInEdit = ruleItem
    },
    handleDiscountEdit() {
      updateDiscount(this.info.restaurantId, this.ruleInEdit.id, this.ruleInEdit)
        .then(resp => {
          const flag = resp.data['status'] === 201
          this.$notify({
            name: `操作${flag ? '成功' : '失败'}`,
            type: flag ? 'success' : 'error',
            message: resp.data['msg']
          })

          if (flag) {
            const item = this.ruleList.find(x => x['id'] === this.ruleInEdit.id)
            item['consumption'] = this.ruleInEdit['consumption']
            item['discount'] = this.ruleInEdit['discount']
            this.ruleInEdit = {}
            this.displayDiscountEdit = false
          }
        })
    },
    async handleDiscountAdd(price, discount) {
      const result = (await addDiscount(this.info.restaurantId, price, discount)).data
      const flag = result['status'] === 201
      this.$notify({
        name: `操作${flag ? '成功' : '失败'}`,
        type: flag ? 'success' : 'error',
        message: result['msg']
      })
      this.displayDiscountAdd = false
      this.handleDiscountList()
    },
    handleFreebiesActiveClick(ruleItem) {
      updateDiscount(this.info.restaurantId, ruleItem.id, ruleItem)
        .then(resp => {
          const flag = resp.data['status'] === 201

          if (!flag) {
            this.$notify({
              name: '操作成功失败',
              type: 'error',
              message: resp.data['msg']
            })
          }
        })
    }
  }
}
</script>
