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
      }
    ]
  }
]
export const DYNAMIC_ROUTES = SYSTEM_ROUTES
