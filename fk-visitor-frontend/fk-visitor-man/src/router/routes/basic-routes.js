import { PublicLayout, BasicLayout } from '@/layouts'

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
    component: BasicLayout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/Home'),
        meta: { title: '首页' }
      },
      {
        path: 'account/settings',
        name: 'account-settings',
        component: () => import(/* webpackChunkName: "basic" */ '@/views/account/setting/AccountSetting'),
        redirect: '/account/settings/basic',
        meta: { title: '个人设置' },
        children: [
          {
            path: 'basic',
            name: 'account-setting-basic',
            component: () => import(/* webpackChunkName: "basic" */ '@/views/account/setting/BasicUpdate'),
            meta: { title: '基本信息' }
          },
          {
            path: 'security',
            name: 'account-setting-security',
            component: () => import(/* webpackChunkName: "basic" */ '@/views/account/setting/SecurityUpdate'),
            meta: { title: '账号密码' }
          }
        ]
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
