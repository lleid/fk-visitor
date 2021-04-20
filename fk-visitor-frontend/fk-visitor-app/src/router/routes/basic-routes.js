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
        meta: { title: '首页', keepAlive: true }
      },
      {
        path: 'order',
        name: 'order',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/Order'),
        meta: { title: '临时访客', keepAlive: false }
      },
      {
        path: 'invite',
        name: 'invite',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/Invite'),
        meta: { title: '受邀访客', keepAlive: true }
      },
      {
        path: 'signout',
        name: 'signout',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/Signout'),
        meta: { title: '签出', keepAlive: true }
      },
      {
        path: 'history',
        name: 'history',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/History'),
        meta: { title: '访客历史', keepAlive: true }
      },
      {
        path: 'success',
        name: 'success',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/order/Success'),
        meta: { title: '登记成功', keepAlive: false }
      },
      {
        path: 'team',
        name: 'team',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/order/Team'),
        meta: { title: '团队预约', keepAlive: false }
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
