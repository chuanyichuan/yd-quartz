import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in subMenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/
export const constantRouterMap = [
  {
    path: '/',
    component: Layout,
    redirect: '/quartz/index',
    hidden: true
  },
  {
    path: '/quartz',
    component: Layout,
    name: '首页',
    meta: { title: '首页', icon: 'example' },
    children: [
      {
        path: 'index',
        name: '任务列表',
        component: () => import('@/views/quartz/index'),
        meta: { title: '任务列表', icon: 'table' }
      },
      {
        path: 'add',
        name: '新增调度任务',
        component: () => import('@/views/quartz/add'),
        meta: { title: '新增调度任务', icon: 'tree' }
      },
      {
        path: 'edit/:id(\\d+)',
        name: '编辑调度任务',
        hidden: true,
        component: () => import('@/views/quartz/edit'),
        meta: { title: '编辑调度任务', icon: 'tree' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
