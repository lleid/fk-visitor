<script>
import VueTypes from 'vue-types'
import omit from 'lodash.omit'

import { SettingProps } from '../ProLayoutSetting'
import { Affix, Tabs, PageHeader, Icon } from 'ant-design-vue'
import { PageHeaderProps } from 'ant-design-vue/es/page-header'

import { BaseMenuDataProps } from '../BaseMenu/BaseMenu'
import GridContent from '../GridContent/GridContent'

import * as PageHeaderWrapperUtils from './utils'

VueTypes.sensibleDefaults = false

export const PageHeaderTabProps = {
  tabList: VueTypes.arrayOf(VueTypes.shape({
    tab: VueTypes.string,
    key: VueTypes.string,
    closable: VueTypes.bool.def(true),
    disabled: VueTypes.bool.def(false),
    forceRender: VueTypes.bool.def(false)
  }).loose),
  tabProps: VueTypes.shape({
    defaultActiveKey: VueTypes.oneOfType([VueTypes.string, VueTypes.number]),
    hideAdd: VueTypes.bool.def(false),
    tabBarStyle: VueTypes.object,
    type: VueTypes.oneOf(['line', 'card', 'editable-card']).def('line'),
    tabPosition: VueTypes.oneOf(['top', 'right', 'bottom', 'left']).def('top'),
    size: VueTypes.oneOf(['default', 'small', 'large']).def('default'),
    animated: VueTypes.oneOfType([VueTypes.bool, VueTypes.object]),
    tabBarGutter: VueTypes.number
  }).loose
}

export const PageHeaderBreadcrumbProps = {
  ...BaseMenuDataProps,
  breadcrumbList: VueTypes.arrayOf(VueTypes.shape({
    title: VueTypes.string,
    href: VueTypes.string
  }).loose),
  location: VueTypes.string,
  home: VueTypes.string.def(''),
  homeIcon: VueTypes.bool.def(true),
  breadcrumbRender: VueTypes.func,
  itemRender: VueTypes.func.def(({ route, params, routes, paths, h }) => {
    if (route.home === true) {
      const breadcrumbs = []
      if (route.breadcrumbName !== undefined && route.breadcrumbName.trim() !== '') {
        breadcrumbs.push(h('span', route.breadcrumbName))
      } else if (route.homeIcon === true) {
        breadcrumbs.push(h(Icon, { props: { type: 'home' } }))
      }
      return h('router-link', { props: { to: route.path } }, breadcrumbs)
    } else {
      if (route.path === undefined || route.path.trim() === '' || route.path === routes[routes.length - 1].path) {
        return h('span', route.breadcrumbName)
      } else {
        return h('router-link', { props: { to: route.path } }, route.breadcrumbName)
      }
    }
  })
}

export const PageHeaderWrapperProps = {
  ...SettingProps,
  ...omit(PageHeaderProps, ['backIcon']),
  backIcon: VueTypes.any.def(false),
  ...PageHeaderBreadcrumbProps,
  ...PageHeaderTabProps,
  locale: VueTypes.string,
  fixed: VueTypes.bool.def(true),
  fixedTarget: VueTypes.func
}

const PageHeaderWrapper = {
  name: 'PageHeaderWrapper',
  components: {
    GridContent
  },
  props: {
    ...PageHeaderWrapperProps
  },
  data () {
    return {
      currentTabActiveKey: this.tabList !== undefined && Array.isArray(this.tabList) && this.tabList.length > 0 ? this.tabList[0].key : '',
      pageTitle: this.title,
      breadcrumbProps: {},
      fixedState: false
    }
  },
  watch: {
    tabList (val) {
      if (val !== undefined && Array.isArray(val) && val.length > 0) {
        this.currentTabActiveKey = val[0].key
      }
    },
    title (val) {
      this.pageTitle = val
    },
    breadcrumbProps (val) {
      if (this.title === undefined || this.title.toString().trim() === '') {
        this.pageTitle = val.routes !== undefined && val.routes.length > 0 ? val.routes[val.routes.length - 1].breadcrumbName : undefined
      }
    },
    $route () {
      this.breadcrumbProps = this.getBreadcrumbProps()
    }
  },
  mounted () {
    this.breadcrumbProps = this.getBreadcrumbProps()
  },
  methods: {
    onTabChange (key) {
      this.currentTabActiveKey = key
      this.$emit('tab-change', key)
    },
    onPageBack () {
      if (this.backIcon !== false) {
        this.$emit('page-back')
      }
    },
    onAffixChange (affixed) {
      this.fixedState = affixed
    },
    renderPageHeader () {
      return (
        <div class="ant-pro-page-header-wrap-page-header-warp">
          <GridContent contentWidth={this.contentWidth}>
            <PageHeader
              {
              ...{
                props: {
                  ...this.$props,
                  title: this.pageTitle,
                  breadcrumb: { props: this.breadcrumbProps }
                }
              }
              }
              onBack={this.onPageBack}
            >
              {
                this.$slots['content'] !== undefined || this.$slots['extra-content'] !== undefined
                  ? (
                    <div class="ant-pro-page-header-wrap-detail">
                      <div class="ant-pro-page-header-wrap-main">
                        <div class="ant-pro-page-header-wrap-row">
                          {
                            this.$slots['content'] !== undefined ? (
                              <div class="ant-pro-page-header-wrap-content">
                                {this.$slots['content']}
                              </div>
                            ) : undefined
                          }
                          {
                            this.$slots['extra-content'] !== undefined ? (
                              <div class="ant-pro-page-header-wrap-extra-content">
                                {this.$slots['extra-content']}
                              </div>
                            ) : undefined
                          }
                        </div>
                      </div>
                    </div>
                  ) : undefined
              }
              <template slot="extra">
                {this.$slots['title-extra-content']}
              </template>
              <template slot="footer">
                {
                  this.tabList !== undefined && this.tabList.length > 0
                    ? (

                      <Tabs
                        class="ant-pro-page-header-wrap-tabs"
                        {...{ props: { ...this.tabProps, activeKey: this.currentTabActiveKey } }}
                        onChange={this.onTabChange}
                      >
                        {this.tabList.map(item => (
                          <Tabs.TabPane key={item.key}>
                            {this.$slots['tab-' + item.key] !== undefined
                              ? (<template slot="tab">{this.$slots['tab-' + item.key]}</template>)
                              : (<span slot="tab">{item.tab}</span>)
                            }
                          </Tabs.TabPane>
                        ))}
                      </Tabs>
                    )
                    : undefined
                }
              </template>
            </PageHeader>
          </GridContent>
        </div >
      )
    },
    getBreadcrumbProps () {
      let routes
      if (this.breadcrumbList !== undefined && this.breadcrumbList.length > 0) {
        routes = PageHeaderWrapperUtils.convertFromList(this.breadcrumbList)
      } else {
        const locale = this.menu !== undefined && this.menu.locale === true ? this.locale : undefined
        if (this.location !== undefined && this.location.trim() !== '') {
          routes = PageHeaderWrapperUtils.convertFromLocation(this.location, this.menuData, locale)
        } else {
          routes = PageHeaderWrapperUtils.convertFromLocation(this.$route.fullPath, this.menuData, locale)
          if (routes.length === 0) {
            routes = PageHeaderWrapperUtils.convertFromLocation(this.$route.path, this.menuData, locale)
          }
        }
      }
      if (this.breadcrumbRender !== undefined) {
        routes = this.breadcrumbRender(routes) || routes
      }
      if (routes !== undefined && routes.length > 0) {
        const homeBreadcrumb = PageHeaderWrapperUtils.genHomeBreadcrumb(this.home, this.homeIcon)
        if (homeBreadcrumb !== undefined) {
          routes = [homeBreadcrumb].concat(routes)
        }
      }
      return {
        routes,
        itemRender: this.itemRender
      }
    }
  },
  render () {
    return (
      <div ref="headerWrapper" class="ant-pro-page-header-wrap">
        {this.fixed
          ? (
            <Affix onChange={this.onAffixChange} class={{ 'header-fixed': this.fixedState }}>
              {this.renderPageHeader()}
            </Affix>
          )
          : this.renderPageHeader()
        }
        {
          this.$slots['children'] !== undefined
            ? (
              <GridContent contentWidth={this.contentWidth}>
                <div class="ant-pro-page-header-wrap-children-content">
                  {this.$slots['children']}
                </div>
              </GridContent>
            )
            : undefined
        }
      </div >
    )
  }
}

PageHeaderWrapper.install = function (Vue) {
  Vue.use(Affix)
  Vue.use(PageHeader)
  Vue.use(Tabs)
  Vue.use(Icon)
  Vue.component(PageHeaderWrapper.name, PageHeaderWrapper)
}

export default PageHeaderWrapper

</script>

<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

@pro-layout-page-header-wrap: ~'@{ant-prefix}-pro-page-header-wrap';

.@{ant-prefix}-pro-page-header-wrap {
  .header-fixed {
    .ant-affix {
      box-shadow: 0 4px 2px -2px rgba(0, 21, 41, 0.08);
    }
  }
}

.@{pro-layout-page-header-wrap}-children-content {
  margin: 24px 24px 0;
}

.@{pro-layout-page-header-wrap}-page-header-warp {
  background-color: @component-background;
}

.@{pro-layout-page-header-wrap}-main {
  .@{pro-layout-page-header-wrap}-detail {
    display: flex;
  }

  .@{pro-layout-page-header-wrap}-row {
    display: flex;
    width: 100%;
  }

  .@{pro-layout-page-header-wrap}-title-content {
    margin-bottom: 16px;
  }

  .@{pro-layout-page-header-wrap}-title,
  .@{pro-layout-page-header-wrap}-content {
    flex: auto;
  }

  .@{pro-layout-page-header-wrap}-extra-content,
  .@{pro-layout-page-header-wrap}-main {
    flex: 0 1 auto;
  }

  .@{pro-layout-page-header-wrap}-main {
    width: 100%;
  }

  .@{pro-layout-page-header-wrap}-title {
    margin-bottom: 16px;
  }

  .@{pro-layout-page-header-wrap}-logo {
    margin-bottom: 16px;
  }

  .@{pro-layout-page-header-wrap}-extra-content {
    min-width: 242px;
    margin-left: 88px;
    text-align: right;
  }
}

@media screen and (max-width: @screen-xl) {
  .@{pro-layout-page-header-wrap}-main {
    .@{pro-layout-page-header-wrap}-extra-content {
      margin-left: 44px;
    }
  }
}

@media screen and (max-width: @screen-lg) {
  .@{pro-layout-page-header-wrap}-main {
    .@{pro-layout-page-header-wrap}-extra-content {
      margin-left: 20px;
    }
  }
}

@media screen and (max-width: @screen-md) {
  .@{pro-layout-page-header-wrap}-main {
    .@{pro-layout-page-header-wrap}-row {
      display: block;
    }

    .@{pro-layout-page-header-wrap}-action,
    .@{pro-layout-page-header-wrap}-extra-content {
      margin-left: 0;
      text-align: left;
    }
  }
}

@media screen and (max-width: @screen-sm) {
  .@{pro-layout-page-header-wrap}-detail {
    display: block;
  }
  .@{pro-layout-page-header-wrap}-extra-content {
    margin-left: 0;
  }
}
</style>
