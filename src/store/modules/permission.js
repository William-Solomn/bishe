// // store/permission.js
import { asyncRouterMap0, asyncRouterMap1, constantRouterMap } from '@/router/index.js'

function hasPermission(roles, route) {
  if (route.meta && route.meta.role) {
    return roles.some(role => route.meta.role.indexOf(role) >= 0)
  } else {
    return true
  }
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        const { roles } = data
        if (roles === 1) {
          const accessedRouters = asyncRouterMap1.filter(v => {
            if (roles.indexOf('admin') >= 0) return true
            if (hasPermission(roles, v)) {
              if (v.children && v.children.length > 0) {
                v.children = v.children.filter(child => {
                  if (hasPermission(roles, child)) {
                    return child
                  }
                  return false
                })
                return v
              } else {
                return v
              }
            }
            return false
          })
          commit('SET_ROUTERS', accessedRouters)
          resolve()
        } else if (roles === 0) {
          const accessedRouters = asyncRouterMap0.filter(v => {
            if (roles.indexOf('admin') >= 0) return true
            if (hasPermission(roles, v)) {
              if (v.children && v.children.length > 0) {
                v.children = v.children.filter(child => {
                  if (hasPermission(roles, child)) {
                    return child
                  }
                  return false
                })
                return v
              } else {
                return v
              }
            }
            return false
          })
          commit('SET_ROUTERS', accessedRouters)
          resolve()
        }
      })
    }
  }
}

export default permission

