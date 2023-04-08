<template>
  <el-main>
    <el-tabs class="main" type="border-card">
      <el-tab-pane label="满减">
        <Discount />
      </el-tab-pane>
      <el-tab-pane label="赠送菜">
        <Freebies />
      </el-tab-pane>
    </el-tabs>
  </el-main>
</template>

<script>
import Discount from '@/views/seals/discount'
import Freebies from '@/views/seals/freebies'

export default {
  components: { Discount, Freebies }
}
</script>
