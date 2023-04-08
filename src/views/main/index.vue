<template>
  <el-main>
    <el-row :gutter="16">
      <el-col :span="16">
        <el-tabs class="main" type="border-card">

              <!-- tables -->
              <el-col v-for="(tableItem,j) in carList" :key="j" :span="6">
                <div class="grid-content">
                  <!-- table card -->
                  <el-card :style="`color: ${handleStatusColor(tableItem.parking_is_useful)}`" class="box-card">
                    <!-- table card header -->
                    <div slot="header" class="clearfix">
                      <span>{{ tableItem.parking_name }}</span>
                      <el-button style="float: right; padding: 0" type="text" @click="handleDetailShow(tableItem.parking_user_id)">详情
                      </el-button>

                    </div>
                    <div style="text-align: center">
                      <span>{{
                        tableItem.parking_user_id
                      }}</span>
                      <!-- <h3> {{ handleStatus(tableItem.status) }}</h3> -->
                    </div>
                  </el-card>
                </div>
              </el-col>
        </el-tabs>
      </el-col>
      <!-- <el-col :span="8">1
        <Detail class="main" :on-save="handleTableEditSave" :alter-table-list="altTableList" :table="detailTableItem" />
      </el-col> -->
      <el-col :span="8" class="main-info">
        <el-row direction="column">
          <el-col :span="24">
            <Detail class="main" :on-save="handleTableEditSave" :alter-table-list="altTableList" :table="detailTableItem" />
          </el-col>
          <el-col :span="24">
            <el-card>
            </el-card>
          </el-col>
          <el-col :span="24">
            <div class="grid-content">3</div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
import Detail from '@/views/main/detail'
import { handleCurrent } from '@/utils/displayUtil'
import { GetOrderDetail } from '@/api/order'
import { getAreaList, getTableList } from '@/api/tables'
import { getRestaurantInfo } from '@/utils/auth'
import { getAllParkingInfo, postParkingInfo } from '@/api/parkinginfo'
import { getUserInfoByName } from '@/api/main'

export default {
  components: { Detail },
  data: () => ({
    info: {
      restaurantId: -1
    },
    id: 1,
    areaList: [],
    tabList: {
      default: []
    },
    detailTableItem: {},
    altTableList: [],
    carList:[]
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleCarList()
  },
  methods: {
    handleDateTime(tableItem) {
      return `${new Date(tableItem.order['orderTime']).getHours()}:${new Date(tableItem.order['orderTime']).getMinutes()}`
    },
    handleCarList() {
      getAllParkingInfo().then(resp => {
          this.carList = resp.data
      })
    },
    handleTableEditSave() {
      this.handleTableList()
    },
    handlePrice(num) {
      return handleCurrent(num)
    },
    handleStatus(statusNumber) {
      switch (statusNumber) {
        case 0:
          return '空闲'
        case 1:
          return '占用'
        case 2:
          return '等待'
      }
    },
    handleStatusColor(statusNumber) {
      switch (statusNumber) {
        case 0:
          return '#0000FF'
        case 1:
          return '#FF0000'
      }
    },
    handleDetailShow(user_name) {
        getUserInfoByName(user_name).then(resp => {
          this.detailTableItem = resp.data
          console.log('_+_+_+_++',resp.data)
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
