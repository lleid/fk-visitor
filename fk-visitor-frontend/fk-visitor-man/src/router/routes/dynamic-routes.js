import { BasicLayout } from '@/layouts'

export const SYSTEM_ROUTES = [
  {
    path: '/system',
    name: 'system',
    component: BasicLayout,
    meta: { title: '系统管理' },
    children: [
      {
        path: 'operator',
        name: 'operator-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/system/operator/OperatorList'),
        meta: { title: '用户管理', keepAlive: true }
      },
      {
        path: 'role',
        name: 'role-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/system/operator-role/OperatorRoleList'),
        meta: { title: '角色管理', keepAlive: false }
      },
      {
        path: 'menu',
        name: 'menu-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/system/menu/MenuList'),
        meta: { title: '菜单管理', keepAlive: false }
      },
      {
        path: 'station',
        name: 'station-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/system/station/StationList'),
        meta: { title: '站点管理', keepAlive: false }
      },
      {
        path: 'purpose',
        name: 'purpose-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/system/purpose/PurposeList'),
        meta: { title: '事由管理', keepAlive: false }
      },
      {
        path: 'banner',
        name: 'banner-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/system/banner/BannerList'),
        meta: { title: '轮播图', keepAlive: false }
      },
      {
        path: 'protocol',
        name: 'protocol-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/system/protocol/ProtocolList'),
        meta: { title: '协议管理', keepAlive: false }
      }
    ]
  }
]

export const DATA_ROUTES = [
  {
    path: '/data',
    name: 'data',
    component: BasicLayout,
    meta: { title: '订单管理' },
    children: [
      {
        path: 'customer',
        name: 'customer-list',
        component: () => import(/* webpackChunkName: "data" */ '@/views/data/customer/CustomerList'),
        meta: { title: '访客管理', keepAlive: true }
      },
      {
        path: 'appointment',
        name: 'appointment-list',
        component: () => import(/* webpackChunkName: "data" */ '@/views/data/appointment/AppointmentList'),
        meta: { title: '预约管理', keepAlive: false }
      },
      {
        path: 'order',
        name: 'order-list',
        component: () => import(/* webpackChunkName: "data" */ '@/views/data/order/OrderList'),
        meta: { title: '访客记录', keepAlive: false }
      }
    ]
  }
]
export const DYNAMIC_ROUTES = SYSTEM_ROUTES.concat(DATA_ROUTES)
