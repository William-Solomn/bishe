# 星流软件-餐厅管理后台

## Copyright

Code by: @JoeyYang725, @nidbCN

Fork from vue-admin-template

## 文件目录

```
D:\CODES\VUE_PROJECTS\XINGLIUADMIN\SRC
│   App.vue       // 主界面
│   settings.js
│   main.js
│
├───api
│       user.js
│       order.js              // 订单相关
│       qianting.js           // 前厅相关
│       systemInfo.js         // 系统设置相关
│       main.js
│       base.js               // 基础请求方法封装
│       food.js               // 菜品相关
│       tables.js             // 餐桌相关
│       files.js              // 文件相关
│       statistics.js         // 数据相关
│       seals.js              // 营销相关
│       about.js
│
├───assets
│   └───404_images
│           404.png
│           404_cloud.png
│
├───components
│   ├───Breadcrumb
│   │       index.vue
│   │
│   ├───Hamburger
│   │       index.vue
│   │
│   ├───SvgIcon
│   │       index.vue
│   │
│   └───Search                // 搜索组件
│           searchBar.vue     // 搜索并选择结果，回调返回选择的结果
│           searchItem.vue    // 搜索，回调返回所有匹配结果
│
├───icons
│   │   index.js
│   │   svgo.yml
│   │
│   └───svg
│           dashboard.svg
│           example.svg
│           eye-open.svg
│           eye.svg
│           form.svg
│           link.svg
│           nested.svg
│           password.svg
│           table.svg
│           tree.svg
│           user.svg
│
├───layout
│   │   index.vue
│   │
│   ├───components
│   │   │   AppMain.vue
│   │   │   index.js
│   │   │   Navbar.vue
│   │   │
│   │   └───Sidebar
│   │           FixiOSBug.js
│   │           Item.vue
│   │           Link.vue
│   │           Logo.vue
│   │           SidebarItem.vue
│   │           index.vue
│   │
│   └───mixin
│           ResizeHandler.js
│
├───router
│       index.js              // 路由
│
├───store
│   │   getters.js
│   │   index.js
│   │
│   └───modules
│           app.js
│           settings.js
│           permission.js
│           user.js
│
├───styles
│       element-ui.scss
│       index.scss
│       mixin.scss
│       sidebar.scss
│       transition.scss
│       variables.scss
│
├───utils
│       get-page-title.js
│       index.js
│       validate.js
│       displayUtil.js           // 显示金额等方法
│       auth.js                  // 认证相关
│       qrcode.js                // 二维码相关
│       request.js               // 网络请求
│
└───views
    │   404.vue
    │
    ├───main                     // 工作台
    │       credit.vue           // 挂账
    │       detail.vue           // 详情
    │       index.vue            // 工作台首页
    │
    ├───login
    │       index.vue            // 登录首页
    │
    ├───dashboard
    │       index.vue
    │
    ├───form
    │       index.vue
    │
    ├───tables                   // 餐桌
    │       operateTable.vue     // 操作餐桌组件
    │       operateArea.vue      // 操作区域组件
    │       index.vue            // 餐桌首页
    │
    ├───orders                   // 订单
    │       billing.vue          // 堂食订单
    │       credit.vue           // 挂账订单
    │
    ├───foods                    // 菜品
    │       combo.vue            // 套餐
    │       single.vue           // 单品
    │       operateCat.vue       // 操作分类组件
    │       materialManage.vue   // 材料管理组件
    │       materialSelect.vue   // 材料选择组件
    │       foodAdd.vue          // 添加实物
    │       comboAdd.vue         // 添加套餐
    │
    ├───statistics               // 数据
    │       sealData.vue         // 销售数据
    │       customerData.vue     // 顾客数据
    │       foodData.vue         // 菜品数据
    │       index.vue            // 首页
    │
    ├───seals                    // 营销
    │       index.vue            // 首页
    │       discount.vue         // 减免页面
    │       freebies.vue         // 送菜界面
    │       discountOperate.vue  // 减免操作
    │       freebiesOperate.vue  // 送菜操作
    │
    ├───qianting                 // 前厅
    │       index.vue            // 前厅页面
    │
    └───systemInfo               // 系统设置
            index.vue            // 系统设置界面
```