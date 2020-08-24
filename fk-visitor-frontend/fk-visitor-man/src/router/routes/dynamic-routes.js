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
      }
    ]
  }
]

export const DATA_ROUTES = [
  {
    path: '/data',
    name: 'data',
    component: BasicLayout,
    meta: { title: '主数据管理' },
    children: [
      {
        path: 'purpose',
        name: 'purpose-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/data/purpose/PurposeList'),
        meta: { title: '事由管理', keepAlive: false }
      },
      {
        path: 'visitarea',
        name: 'visitarea-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/data/visitarea/VisitAreaList'),
        meta: { title: '拜访区域管理', keepAlive: false }
      },
      {
        path: 'banner',
        name: 'banner-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/data/banner/BannerList'),
        meta: { title: '轮播图', keepAlive: false }
      },
      {
        path: 'protocol',
        name: 'protocol-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/data/protocol/ProtocolList'),
        meta: { title: '协议管理', keepAlive: false }
      }
    ]
  }
]

export const BACKEND_ROUTES = [
  {
    path: '/backend',
    name: 'backend',
    component: BasicLayout,
    meta: { title: '后台管理' },
    children: [
      {
        path: 'appointment',
        name: 'backend-appointment-list',
        component: () => import(/* webpackChunkName: "data" */ '@/views/backend/appointment/AppointmentList'),
        meta: { title: '预约管理', keepAlive: false }
      },
      {
        path: 'order',
        name: 'backend-order-list',
        component: () => import(/* webpackChunkName: "data" */ '@/views/backend/order/OrderList'),
        meta: { title: '访客记录', keepAlive: false }
      }
    ]
  }
]

export const QUERY_ROUTES = [
  {
    path: '/query',
    name: 'query',
    component: BasicLayout,
    meta: { title: '报表及查询' },
    children: [
      {
        path: 'customer',
        name: 'query-customer-list',
        component: () => import(/* webpackChunkName: "data" */ '@/views/query/customer/CustomerList'),
        meta: { title: '访客管理', keepAlive: true }
      },
      {
        path: 'orderquery',
        name: 'query-order-list',
        component: () => import(/* webpackChunkName: "data" */ '@/views/query/order/OrderList'),
        meta: { title: '访客记录', keepAlive: false }
      }
    ]
  }
]
export const DYNAMIC_ROUTES = SYSTEM_ROUTES.concat(DATA_ROUTES)
  .concat(BACKEND_ROUTES)
  .concat(QUERY_ROUTES)
