import VueTypes from 'vue-types'

export const SettingProps = {
    /**
     * theme for nav menu
     */
    navTheme: VueTypes.oneOf(['light', 'dark']).def('light'),
    /**
     * nav menu position: `sidemenu` or `topmenu`
     */
    layout: VueTypes.oneOf(['sidemenu', 'topmenu']).def('sidemenu'),
    /**
     * layout of content: `Fluid` or `Fixed`, only works when layout is topmenu
     */
    contentWidth: VueTypes.oneOf(['Fluid', 'Fixed']).def('Fluid'),
    /**
     * sticky header
     */
    fixedHeader: VueTypes.bool.def(true),
    /**
     * sticky siderbar
     */
    fixSiderbar: VueTypes.bool.def(true),
    menu: VueTypes.shape({
        locale: VueTypes.bool,
        defaultOpenAll: VueTypes.bool
    }).def({
        locale: true,
        defaultOpenAll: false
    }),
    title: VueTypes.string,
    // Your custom iconfont Symbol script Url
    // eg：//at.alicdn.com/t/font_1039637_btcrd5co4w.js
    // 注意：如果需要图标多色，Iconfont 图标项目里要进行批量去色处理
    // Usage: https://github.com/ant-design/ant-design-pro/pull/3517
    iconfontUrl: VueTypes.string,
    iconfontPrefix: VueTypes.string,
    primaryColor: VueTypes.string,
    colorWeak: VueTypes.bool.def(false)
}
