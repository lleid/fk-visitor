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
    path: '/mdata',
    name: 'mdata',
    component: BasicLayout,
    meta: { title: '主数据管理' },
    children: [
      {
        path: 'purpose',
        name: 'purpose-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/mdata/purpose/PurposeList'),
        meta: { title: '事由管理', keepAlive: false }
      },
      {
        path: 'visitarea',
        name: 'visitarea-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/mdata/visitarea/VisitAreaList'),
        meta: { title: '参观区域管理', keepAlive: false }
      },
      {
        path: 'banner',
        name: 'banner-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/mdata/banner/BannerList'),
        meta: { title: '轮播图', keepAlive: false }
      },
      {
        path: 'protocol',
        name: 'protocol-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/mdata/protocol/ProtocolList'),
        meta: { title: '协议管理', keepAlive: false }
      },
      {
        path: 'employee',
        name: 'employee-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/mdata/employee/EmployeeList'),
        meta: { title: '员工管理', keepAlive: false }
      },
      {
        path: 'signoutreason',
        name: 'signoutreason-list',
        component: () => import(/* webpackChunkName: "system" */ '@/views/mdata/signoutreason/SignOutReasonList'),
        meta: { title: '签出原因管理', keepAlive: false }
      }
    ]
  }
]

export const BACKEND_ROUTES = [
  {
    path: '/order',
    name: 'order',
    component: BasicLayout,
    meta: { title: '预约访客' },
    children: [
      {
        path: 'appointment',
        name: 'order-appointment-list',
        component: () => import(/* webpackChunkName: "mdata" */ '@/views/order/appointment/AppointmentList'),
        meta: { title: '个人预约', keepAlive: false }
      },
      {
        path: 'team',
        name: 'order-team-list',
        component: () => import(/* webpackChunkName: "mdata" */ '@/views/order/team/TeamList'),
        meta: { title: '团队预约', keepAlive: false }
      },
      {
        path: 'order-inprocess',
        name: 'order-inprocess-list',
        component: () => import(/* webpackChunkName: "mdata" */ '@/views/order/order-inprocess/OrderList'),
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
        path: 'order-list',
        name: 'order-list',
        component: () => import(/* webpackChunkName: "mdata" */ '@/views/order/order-list/OrderList'),
        meta: { title: '访客记录', keepAlive: false }
      }
    ]
  }
]
export const DYNAMIC_ROUTES = SYSTEM_ROUTES.concat(DATA_ROUTES)
  .concat(BACKEND_ROUTES)
  .concat(QUERY_ROUTES)
