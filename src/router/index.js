import Vue from 'vue'
import Router from 'vue-router'
import { getRole } from '@/utils/auth'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

// 公用路由（所有人都可查看）
export const constantRoutes = [
  // {
  //   path: '/login',
  //   component: () => import('@/views/login/index'),
  //   hidden: true
  // },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  // 工作台
  {
    path: '/',
    redirect: '/main'
  },
  //测试页
  {
    path: '/test',
    component: Layout,
    children: [{
      path: 'main',
      name: 'Main',
      component: () => import('@/views/test/index'),
      meta: { title: '测试', icon: 'dashboard' }
    }]
  },
  // 用户信息页
  {
    path: '/userinfo',
    component: Layout,
    children: [{
      path: 'main',
      name: 'main',
      component: () => import('@/views/userinfo/index'),
      meta: { title: '用户信息管理', icon: 'user' }
    }]
  },
  // 停车场信息管理
  {
    path: '/parkinginfo',
    component: Layout,
    children: [{
      path: 'main',
      name: 'main',
      component: () => import('@/views/parkinginfo/index'),
      meta: { title: '停车位信息', icon: 'dashboard' }
    }]
  },
  {
    path: '/main',
    component: Layout,
    children: [{
      path: 'main',
      name: 'Main',
      component: () => import('@/views/main/index'),
      meta: { title: '工作台', icon: 'dashboard' }
    }]
  },

  // // 订单
  // {
  //   path: '/orders',
  //   component: Layout,
  //   meta: { title: '订单', icon: 'el-icon-s-order' },
  //   children: [
  //     {
  //       path: 'billing',
  //       name: 'Billing',
  //       component: () => import('@/views/orders/billing'),
  //       meta: { title: '堂食订单', icon: 'el-icon-s-shop' }
  //     },
  //     {
  //       path: 'credit',
  //       name: 'Credit',
  //       component: () => import('@/views/orders/credit'),
  //       meta: { title: '挂账订单', icon: 'el-icon-document-checked' }
  //     },
  //     {
  //       path: 'scheduled',
  //       name: 'Scheduled',
  //       component: () => import('@/views/orders/scheduled'),
  //       meta: { title: '预定订单', icon: 'el-icon-s-check' }
  //     }
  //   ]
  // },
  // // 餐桌
  // {
  //   path: '/tables',
  //   component: Layout,
  //   meta: { role: [0, 1] }, // 0是老板，1是管理员，2是服务员
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Tables',
  //       component: () => import('@/views/tables/index'),
  //       meta: { title: '餐桌', icon: 'el-icon-s-shop' }
  //     }
  //   ]
  // },
  // // 菜品
  // {
  //   path: '/foods',
  //   component: Layout,
  //   meta: { role: [0, 1], title: '菜品', icon: 'el-icon-knife-fork' }, // 0是老板，1是管理员，2是服务员
  //   children: [
  //     {
  //       path: 'single',
  //       name: 'Single',
  //       component: () => import('@/views/foods/single'),
  //       meta: { title: '单品', icon: 'el-icon-tableware' }
  //     },
  //     {
  //       path: 'combo',
  //       name: 'Combo',
  //       component: () => import('@/views/foods/combo'),
  //       meta: { title: '套餐', icon: 'el-icon-dish' }
  //     }
  //   ]
  // },
  // // 营销
  // {
  //   path: '/seals',
  //   component: Layout,
  //   meta: { role: [0, 1] }, // 0是老板，1是管理员，2是服务员
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Index',
  //       component: () => import('@/views/seals/index'),
  //       meta: { title: '营销', icon: 'el-icon-goods' }
  //     }
  //   ]
  // },
// 数据
  {
    path: '/statistics',
    meta: { role: [0] }, // 0是老板，1是管理员，2是服务员
    component: Layout,
    children: [{
      path: 'index',
      name: 'Statistics',
      component: () => import('@/views/statistics/index'),
      meta: { title: '数据', icon: 'el-icon-s-data' }
    }]
  },
  // {
  //   path: '/qianting',
  //   component: Layout,
  //   children: [{
  //     path: 'index',
  //     name: 'qianting',
  //     component: () => import('@/views/qianting/index'),
  //     meta: { title: '前厅', icon: 'dashboard' }
  //   }]
  // },
  {
    path: '/systeminfo',
    component: Layout,
    children: [{
      path: 'index',
      name: 'systeminfo',
      component: () => import('@/views/systemInfo/index'),
      meta: { title: '系统信息', icon: 'dashboard' }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
  // 404 page must be placed at the end !!!
]
// 1的意思是老板和管理员能看到
export const asyncRouterMap1 = [

]
// 0的意思是老板才能看到
export const asyncRouterMap0 = [
  
]
const createRouter = () => {
  const role = getRole()
  if (role === '0') {
    console.log('create0', role)
    return new Router({
      // mode: 'history', // require service support
      scrollBehavior: () => ({ y: 0 }),
      routes: [...constantRoutes, ...asyncRouterMap1, ...asyncRouterMap0]
    })
  } else if (role === '1') {
    console.log('create1', role)
    return new Router({
      // mode: 'history', // require service support
      scrollBehavior: () => ({ y: 0 }),
      routes: [...constantRoutes, ...asyncRouterMap1]
    })
  } else {
    console.log('create2', role)
    return new Router({
      // mode: 'history', // require service support
      scrollBehavior: () => ({ y: 0 }),
      routes: [...constantRoutes]
    })
  }
}

export function resetRouter(newRoutes) {
  console.log('resetROutes')
  router.options.routes = [...constantRoutes, ...newRoutes]
  router.addRoutes(newRoutes)
  // router = createRouter(newRoutes)
  // const newRouter = createRouter()
  // router.matcher = newRouter.matcher // 新路由实例matcer，赋值给旧路由实例的matcher，（相当于replaceRouter）
}

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465

export default router
