<template>
  <el-card>
    <div>
      <el-row :model="parkingCounts">
        <el-col :span="12">
          <el-row>
            <el-col :span="24" class="info-section">
              <div class="info-item">
                <h5>空闲车位共计</h5>
                <h2 class="value one">{{parkingCounts[0]}}</h2>
              </div>
              <div class="info-item">
                <h5>正在使用车位</h5>
                <h2 class="value two">{{parkingCounts[1]}}</h2>
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row>
            <el-col :span="24" class="info-section">
              <div class="info-item">
                <h5>业主使用数量</h5>
                <h2 class="value three">{{parkingCounts[2]}}</h2>
              </div>
              <div class="info-item">
                <h5>严重超时车辆</h5>
                <h2 class="value four">1</h2>
              </div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
    <el-divider />
      <el-button @click="handleUpRailing()">
        抬起栏杆
      </el-button>
    <el-divider />
    <div v-if="!table.user_id">
      <div slot="header">该车位暂无主人</div>
    </div>
    <div v-else>
      <!-- <div slot="header" class="clearfix">{{ table.user_name }} 信息</div> -->

      <el-row>
        <el-col :span="24">
          <el-descriptions :column="1">
            <el-descriptions-item label="用户ID">{{ table['user_id'].substring(0,6)+'...' }}</el-descriptions-item>
            <el-descriptions-item label="用户的姓名">{{ table['user_name'] }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ table['user_phone'] }}</el-descriptions-item>
            <el-descriptions-item label="车牌号">{{ table['user_car_number'] }}</el-descriptions-item>
            <el-descriptions-item label="到期时间">{{ table['remain_date'].substring(0,16) }}</el-descriptions-item>
            <!-- <el-descriptions-item label="">{{ handlePrice(table['price']) }}</el-descriptions-item> -->
          </el-descriptions>
        </el-col>

      </el-row>
    </div>

  </el-card>
</template>

<script>
  import {
    handleCurrent
  } from '@/utils/displayUtil'
  import SearchBar from '@/components/Search/searchBar'
  import {
    getRestaurantInfo
  } from '@/utils/auth'

  import {
    getParkingCounts,
    postUpRailing
  } from '@/api/main'
  import Credit from '@/views/main/credit'

  export default {
    name: 'Detail',
    components: {
      Credit,
      SearchBar
    },
    props: {
      table: {
        type: Object,
        default: () => ({})
      },
      alterTableList: {
        type: Array,
        default: () => ([])
      },
      onSave: {
        type: Function,
        default: () => console.log('default')
      }
    },
    data: () => ({
      info: {
        restaurantId: Number
      },
      newTable: '',
      payInfo: {},
      showPayment: false,
      showEdit: false,
      paymentType: String,
      parkingCounts: []
    }),
    mounted() {
      this.handleParkingCounts()
    },
    methods: {
      handleParkingCounts(){
        getParkingCounts().then(resp => {
          this.parkingCounts = resp.data
        })
        console.log(resp)
      },
      handleOrderUpdate() {
        GetOrderDetail(this.info.restaurantId, this.table.order.orderNo)
          .then(oResp => {
            const oFlag = oResp.data['flag']
            if (oFlag) {
              this.table.order = oResp.data['data'].order
              this.table.order.meals = oResp.data['data'].meals
              this.table.order.combos = oResp.data['data'].combos
            }
          })
      },
      handleEdit() {
        // 占用中
        if (this.table.status !== 0) {
          this.showEdit = true
        } else {
          this.$notify.info({
            title: '消息',
            message: '当前餐桌无订单'
          })
        }
      },
      handleEditSave() {
        if (this.newTable !== '') {
          UpdateTable(this.info.restaurantId, this.table.order.id, this.newTable)
            .then(resp => {
              const flag = resp.data['flag']
              this.$notify({
                name: `操作${flag ? '成功' : '失败'}`,
                type: flag ? 'success' : 'error',
                message: resp.data['message']
              })

              if (flag) {
                this.table.id = 0
                this.handleOrderUpdate()
                this.onSave()
              }
            })
        }

        this.showEdit = false
      },

      handleUpRailing(){
        postUpRailing().then((resp)=>{
          console.log('11111',resp)
        })
      }
    }
  }

</script>
<style scoped>
  .info-section {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
  }

  .info-item {
    text-align: center;
    margin: 5px;
  }
  .value {
    font-weight: bold;
    font-size: 2em;
     margin: 10px;
  }

  .one {
    color: rgb(0, 148, 25);
  }

  .two {
    color: black;
  }

  .three {
    color: rgb(0, 73, 209);
  }

  .four {
    color: rgb(168, 0, 0);
  }

</style>
