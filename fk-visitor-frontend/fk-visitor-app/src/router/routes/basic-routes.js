import { PublicLayout, BlankLayout } from '@/layouts'

export const LOGIN_ROUTES = [
  {
    path: '/account',
    name: 'account',
    component: PublicLayout,
    redirect: '/account/login',
    children: [
      {
        path: 'login',
        name: 'account-login',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/account/login/Login'),
        meta: { title: '登录' }
      }
    ]
  }
]

export const APP_ROUTES = [
  {
    path: '/',
    name: 'index',
    component: BlankLayout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/Home'),
        meta: { title: '首页' }
      },
      {
        path: 'order',
        name: 'order',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/order/Order'),
        meta: { title: '临时访客' }
      },
      {
        path: 'invite',
        name: 'invite',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/order/Invite'),
        meta: { title: '受邀访客' }
      },
      {
        path: 'signout',
        name: 'signout',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/order/Signout'),
        meta: { title: '签出' }
      },
      {
        path: 'history',
        name: 'history',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/order/History'),
        meta: { title: '访客历史' }
      },
      {
        path: 'success',
        name: 'success',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/order/Success'),
        meta: { title: '登记成功' }
      },
      {
        path: 'team',
        name: 'team',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/order/Team'),
        meta: { title: '团队预约' }
      }
    ]
  }
]

/**
 * 异常路由
 */
export const ERROR_ROUTES = [
  {
    path: '/error',
    name: 'error',
    component: PublicLayout,
    children: [
      {
        path: '403',
        name: 'error-403',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/exception/403')
      },
      {
        path: '404',
        name: 'error-404',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/exception/404')
      },
      {
        path: '500',
        name: 'error-500',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/exception/500')
      }
    ]
  }
]

export const BASIC_ROUTES = LOGIN_ROUTES.concat(APP_ROUTES).concat(ERROR_ROUTES)
