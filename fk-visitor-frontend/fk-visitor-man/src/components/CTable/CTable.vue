<script>
import VueTypes from 'vue-types'

import { Table, Pagination, Button, Tooltip, Divider } from 'ant-design-vue'
import { initDefaultProps } from 'ant-design-vue/es/_util/props-util'

import CIcon from '../CIcon'
import * as Utils from './utils'

const FULLSCREEN_EVENTS = ['webkitfullscreenchange', 'mozfullscreenchange', 'msfullscreenchange', 'fullscreenchange']

const CTableDefaultProps = {
  size: 'middle',
  pagination: {
    position: 'bottom',
    pageSize: 20,
    pageSizeOptions: ['10', '20', '30', '50', '100'],
    showLessItem: true,
    showSizeChanger: true,
    showTotal: (total, range) => `第 ${range[0]}-${range[1]} 条 / 共 ${total} 条`
  },
  rowSelection: {
    fixed: true,
    columnWidth: 50
  }
}

export const CTableProps = {
  ...initDefaultProps(Table.props, CTableDefaultProps),
  dataLoader: VueTypes.func,
  updateURI: VueTypes.bool.def(true),
  autoRefresh: VueTypes.bool.def(false),
  autoRefreshInterval: VueTypes.number.def(-1),
  autoRefreshLoading: VueTypes.bool.def(false),
  totalParam: VueTypes.string.def('totalElements'),
  dataParam: VueTypes.string.def('content'),
  pageNoParam: VueTypes.string.def('pageNo'),
  pageSizeParam: VueTypes.string.def('pageSize'),
  sortBuilder: VueTypes.func.def(sorter => {
    if (sorter !== undefined && sorter.field !== undefined && sorter.order !== undefined && sorter !== false) {
      if (sorter.order === 'ascend') {
        return {
          sort: sorter.field + ',asc'
        }
      } else if (sorter.order === 'descend') {
        return {
          sort: sorter.field + ',desc'
        }
      }
    }
  }),
  showRefresh: VueTypes.bool.def(true),
  showFullScreen: VueTypes.bool.def(true),
  toolbarButtons: VueTypes.arrayOf(VueTypes.shape({
    name: VueTypes.string,
    icon: VueTypes.string,
    onClick: VueTypes.func
  }).loose),
  disableMobile: VueTypes.bool.def(false)
}

const CTable = {
  name: 'CTable',
  data () {
    return {
      autoRefresher: undefined,
      selectedRows: [],
      selectedRowKeys: [],

      currentLoading: false,
      currentDataSource: [...this.dataSource],
      currentPagination: this.pagination === false ? false : { ...CTableDefaultProps.pagination, ...this.pagination },
      currentColumns: [...this.columns],
      cachedSorter: undefined,
      cachedFilters: undefined,

      originalTitle: undefined,

      inited: false,
      fullscreen: false,
      media: undefined
    }
  },
  props: {
    ...CTableProps
  },
  watch: {
    media (val) {
      const _columns = this.columns.filter(column => {
        return column.media === undefined || val >= Utils.MediaQueryKey[column.media.toUpperCase()]
      })
      this.currentColumns = _columns
    },
    columns (val) {
      if (val === undefined) {
        this.currentColumns = undefined
      } else {
        const _columns = val.filter(column => {
          return column.media === undefined || this.media >= Utils.MediaQueryKey[column.media.toUpperCase()]
        })
        this.currentColumns = _columns
      }
    },
    pagination (val) {
      if (val === false) {
        this.currentPagination = false
      } else {
        this.currentPagination = { ...this.currentPagination, ...val }
      }
    },
    dataSource (val) {
      if (val === undefined) {
        this.currentDataSource = undefined
      } else {
        this.currentDataSource = [...val]
      }
    },
    'currentPagination.current' (val) {
      const queryNoParam = this.$route.query[this.pageNoParam]
      if (this.updateURI === true && queryNoParam !== undefined && parseInt(queryNoParam) !== val && queryNoParam !== 1) {
        const pageQuery = {
          ...this.$route.query
        }
        pageQuery[this.pageNoParam] = val
        this.$router.push({
          ...this.$route,
          query: pageQuery
        })
      }
    },
    'currentPagination.pageSize' (val) {
      const pageSizeParam = this.$route.query[this.pageSizeParam]
      if (this.updateURI === true && pageSizeParam !== undefined && parseInt(pageSizeParam) !== val) {
        const pageQuery = {
          ...this.$route.query
        }
        pageQuery[this.pageSizeParam] = val
        this.$router.push({
          ...this.$route,
          query: pageQuery
        })
      }
    }
  },
  computed: {
    isMobile () {
      return this.media < Utils.MediaQueryKey.MD
    },
    defaultPagination () {
      return CTableDefaultProps.pagination
    },
    showToolbar () {
      return (this.allToolbarButtons !== undefined && this.allToolbarButtons.length > 0) || this.$scopedSlots['toolbar'] !== undefined
    },
    showToolbarButtons () {
      return this.media > Utils.MediaQueryKey.MD
    },
    showToolbarPagination () {
      return this.media > Utils.MediaQueryKey.MD
    },
    allToolbarButtons () {
      const btns = this.toolbarButtons === undefined ? [] : this.toolbarButtons
      if (this.showRefresh) {
        btns.push({
          name: '刷新',
          icon: 'reload',
          onClick: this.onRefreshClick
        })
      }
      if (this.showFullScreen && (this.disableMobile || !this.isMobile)) {
        if (this.fullscreen === true) {
          btns.push({
            name: '退出全屏',
            icon: 'fullscreen-exit',
            onClick: this.toggleFullScreen
          })
        } else {
          btns.push({
            name: '全屏',
            icon: 'fullscreen',
            onClick: this.toggleFullScreen
          })
        }
      }
      return btns
    }
  },
  created () {
    if (this.currentPagination !== undefined && this.currentPagination !== false) {
      if (this.updateURI === true) {
        const currentPageNo = this.$route.query[this.pageNoParam]
        const currentPageSize = this.$route.query[this.pageSizeParam]
        this.currentPagination = {
          ...this.currentPagination,
          ...(currentPageNo !== undefined && !isNaN(parseInt(currentPageNo)) ? { current: parseInt(currentPageNo) } : { current: 1 }),
          ...(currentPageSize !== undefined &&
            !isNaN(parseInt(currentPageSize)) &&
            (this.currentPagination.pageSizeOptions === undefined ||
              this.currentPagination.pageSizeOptions.includes(currentPageSize))
            ? { pageSize: parseInt(currentPageSize) }
            : {})
        }
      } else {
        this.currentPagination = {
          ...this.currentPagination,
          current: 1
        }
      }
    }
    if (this.dataLoader !== undefined) {
      this.load(this.buildLoadParams(this.currentPagination))
    }
  },
  mounted () {
    if (this.autoRefresh && this.autoRefreshInterval > 0) {
      this.autoRefresher = setInterval(this.autoReload, this.autoRefreshInterval)
    }
    FULLSCREEN_EVENTS.forEach(event => {
      document.addEventListener(event, this.onFullScreenChange)
    })
    Utils.regMediaQuery(this.updateMedia)
  },
  destroyed () {
    if (this.autoRefresher !== undefined) {
      clearInterval(this.autoRefresher)
    }
    FULLSCREEN_EVENTS.forEach(event => {
      document.removeEventListener(event, this.onFullScreenChange)
    })
    Utils.unregMediaQuery()
  },
  methods: {
    stopAutoRefresh () {
      clearInterval(this.autoRefresher)
      this.autoRefresher = undefined
    },
    startAutoRefresh () {
      if (this.dataLoader !== undefined) {
        this.autoRefresher = setInterval(this.autoReload, this.autoRefreshInterval)
      }
    },
    autoReload () {
      this.load(
        this.buildLoadParams(this.currentPagination, this.cachedFilters, this.cachedSorter),
        this.autoRefreshLoading
      )
    },
    refresh (goFirst = false) {
      if (goFirst === true) {
        this.currentPagination = {
          ...this.currentPagination,
          current: 1
        }
      }
      if (this.dataLoader !== undefined) {
        this.load(this.buildLoadParams(this.currentPagination, this.cachedFilters, this.cachedSorter))
      }
    },
    async load (params, showLoading = true) {
      if (showLoading) {
        this.currentLoading = true
      }
      if (this.dataLoader !== undefined) {
        this.currentDataSource = []
        const data = await this.dataLoader(params)
        if (data instanceof Object) {
          if (data[this.totalParam] !== undefined && !isNaN(parseInt(data[this.totalParam]))) {
            this.currentPagination = { ...this.currentPagination, total: parseInt(data[this.totalParam]) }
          }
          if (data[this.dataParam] !== undefined && data[this.dataParam] instanceof Array) {
            this.currentDataSource = data[this.dataParam]
          }
        } else if (data instanceof Array) {
          this.currentDataSource = data
        } else {
          console.warn('Unsupported data type!', data)
        }
      }
      this.currentLoading = false
    },
    onChange (pagination, filters, sorter) {
      this.cachedFilters = filters
      this.cachedSorter = sorter
      this.currentPagination = pagination
      this.load(this.buildLoadParams(pagination, filters, sorter))
    },
    onTbPgChange (page, pageSize) {
      this.currentPagination = { ...this.currentPagination, current: page, pageSize: pageSize }
      this.onChange(this.currentPagination, this.cachedFilters, this.cachedSorter)
    },
    onTbPgShowSizeChange (current, size) {
      this.currentPagination = { ...this.currentPagination, current: current, pageSize: size }
      this.onChange(this.currentPagination, this.cachedFilters, this.cachedSorter)
    },
    buildLoadParams (pagination, filters, sorter) {
      let loadParams = {}
      if (pagination !== undefined) {
        if (pagination.current !== undefined) {
          loadParams[this.pageNoParam] = pagination.current
        }
        if (pagination.pageSize !== undefined) {
          loadParams[this.pageSizeParam] = pagination.pageSize
        }
      }
      if (sorter !== undefined && typeof this.sortBuilder === 'function') {
        const sortParam = this.sortBuilder(sorter)
        if (sortParam !== undefined) {
          loadParams = Object.assign(loadParams, sortParam)
        }
      }
      if (filters !== undefined) {
        loadParams = Object.assign(loadParams, filters)
      }
      return loadParams
    },
    onRefreshClick () {
      if (this.dataLoader !== undefined) {
        this.refresh()
      }
      this.$emit('refreshClick')
    },
    onFullScreenChange (evt) {
      this.fullscreen = (document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || document.msFullscreenElement) !== undefined
    },
    toggleFullScreen () {
      Utils.toggleFullScreen(document.documentElement)
    },
    updateMedia: function (media) {
      this.media = media
    },
    renderToolbarTitle () {
      if (this.showToolbar) {
        return (
          <div class="c-table-title">
            {
              this.originalTitle !== undefined
                ? (<div class="c-table-original-title">{this.originalTitle}</div>)
                : undefined
            }
            <div class="c-table-toolbar">
              {
                this.allToolbarButtons.length > 0 && this.showToolbarButtons
                  ? (
                    <div class="c-table-toolbar-buttons">
                      {
                        this.allToolbarButtons.map(btn => {
                          if (btn.name !== undefined && btn.name.trim() !== '') {
                            return (
                              <Tooltip placement="top">
                                <template slot="title">
                                  <span>{btn.name}</span>
                                </template>
                                {
                                  btn.icon !== undefined && btn.icon.trim() !== ''
                                    ? (<Button type="link" size={this.size === 'small' ? 'small' : undefined} class="c-table-toolbar-icon-button" onClick={btn.onClick !== undefined ? btn.onClick : () => { }}><CIcon type={btn.icon} /></Button>)
                                    : (<Button type="link" size={this.size === 'small' ? 'small' : undefined} class="c-table-toolbar-name-button" onClick={btn.onClick !== undefined ? btn.onClick : () => { }}>{btn.name}</Button>)
                                }
                              </Tooltip>
                            )
                          }
                        })
                      }
                      {
                        this.$scopedSlots['toolbar'] !== undefined
                          ? (<Divider type="vertical" />)
                          : undefined
                      }
                    </div>)
                  : undefined
              }
              <div class="c-table-toolbar-slot">
                {this.$scopedSlots['toolbar'] !== undefined ? this.$scopedSlots.toolbar() : undefined}
              </div>
              {
                (this.currentPagination.position === 'top' || this.currentPagination.position === 'both') && (this.showToolbarPagination)
                  ? (
                    <div class="c-table-toolbar-pagination">
                      <Pagination {...{
                        props: {
                          ...this.currentPagination,
                          ...(this.size !== undefined && (this.size === 'small' || this.size === 'middle') ? { size: 'small' } : {})
                        }
                      }
                      } onChange={this.onTbPgChange} onShowSizeChange={this.onTbPgShowSizeChange} />
                    </div>
                  )
                  : undefined
              }
            </div>
          </div >
        )
      } else {
        return (<div class="c-table-title">{this.originalTitle}</div>)
      }
    }
  },

  render () {
    const props = {}
    const localDataKeys = Object.keys(this.$data)
    Object.keys(Table.props).forEach(k => {
      const localDataKey = `current${k.substring(0, 1).toUpperCase()}${k.substring(1)}`
      if (localDataKeys.includes(localDataKey)) {
        if (localDataKey === 'currentPagination') {
          if (this.showToolbar) {
            if (this.currentPagination.position === 'top') {
              if (this.showToolbarPagination) {
                props['pagination'] = false
              } else {
                props['pagination'] = { ...this.currentPagination, position: 'bottom' }
              }
            } else if (this.currentPagination.position === 'both') {
              props['pagination'] = { ...this.currentPagination, position: 'bottom' }
            } else {
              props['pagination'] = this.currentPagination
            }
          } else {
            props['pagination'] = this.currentPagination
          }
          if (props['pagination'] !== false) {
            if (!this.disableMobile && this.isMobile) {
              props['pagination'].simple = true
            } else {
              props['pagination'].simple = false
            }
          }
        } else {
          props[k] = this[localDataKey]
        }
      } else {
        if (k === 'rowSelection' && this.rowSelection !== undefined && this.rowSelection !== null) {
          props['rowSelection'] = {
            ...this.rowSelection,
            selectedRows: this.selectedRows,
            selectedRowKeys: this.selectedRowKeys,
            onChange: (selectedRowKeys, selectedRows) => {
              this.selectedRows = selectedRows
              this.selectedRowKeys = selectedRowKeys
            }
          }
        } else if (this[k] !== undefined) {
          if (
            Object.keys(CTableDefaultProps).includes(k) &&
            CTableDefaultProps[k] instanceof Object &&
            this[k] instanceof Object
          ) {
            props[k] = { ...CTableDefaultProps[k], ...this[k] }
          } else {
            props[k] = this[k]
          }
        }
      }
    })
    if (!this.inited && this.$scopedSlots.title !== undefined) {
      this.originalTitle = this.$scopedSlots.title()
    }
    this.$scopedSlots.title = this.renderToolbarTitle

    this.inited = true

    return (
      <Table
        class={
          {
            'c-table': true,
            'c-table-small': this.size === 'small',
            'c-mobile-table': !this.disableMobile && this.isMobile
          }
        }
        {...{ props, scopedSlots: { ...this.$scopedSlots } }}
        onChange={this.onChange}
        onExpand={(expanded, record) => {
          this.$emit('expand', expanded, record)
        }}
        onExpandedRowsChange={expandedRows => {
          this.$emit('expandedRowsChange', expandedRows)
        }}
      >
        {Object.keys(this.$slots).map(name => (
          <template slot={name}>{this.$slots[name]}</template>
        ))}
      </Table>
    )
  }
}

CTable.install = function (Vue) {
  Vue.use(Table)
  Vue.use(Pagination)
  Vue.use(Tooltip)
  Vue.use(Button)
  Vue.use(Divider)

  Vue.use(CIcon)

  Vue.component(CTable.name, CTable)
}

export default CTable
</script>

<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

.c-table {
  &.c-mobile-table {
    text-align: center;
    .ant-table-pagination {
      float: none;
    }
  }
  .ant-table-content {
    border-top: 1px solid @border-color-split;
  }
  .ant-table-small {
    .ant-table-content {
      border-top: none;
    }
    .c-table-toolbar {
      padding: 0 8px;
    }
  }
  .ant-table-title {
    padding: 0;
  }
  &.c-table-small {
    .c-table-title {
      .c-table-toolbar {
        margin: 0;
        padding: 0;
        .c-table-toolbar-buttons {
          padding-left: 4px;
          margin: 4px 0;
          .c-table-toolbar-icon-button {
            padding: 0 4px;
          }
          .ant-divider.ant-divider-vertical {
            margin: 0 8px 0 4px;
          }
        }
        .c-table-toolbar-slot {
          margin: 4px 0;
        }
        .c-table-toolbar-pagination {
          height: 24px;
          margin: 4px 0;
        }
      }
    }
  }
  .c-table-title {
    .c-table-original-title {
      height: 32px;
      line-height: 32px;
      padding: 0 16px;
      font-size: 16px;
      margin: 16px 0;
    }
    .c-table-toolbar {
      min-height: 32px;
      margin: 8px 0;
      .c-table-toolbar-buttons {
        float: left;
        padding-left: 8px;
        margin: 8px 0;
        .c-table-toolbar-icon-button {
          padding: 0 8px;
        }
      }
      .c-table-toolbar-slot {
        float: left;
        margin: 8px 0;
      }
      .c-table-toolbar-pagination {
        height: 32px;
        float: right;
        display: flex;
        align-items: center;
        margin: 8px 0;
      }
      &::after {
        display: block;
        clear: both;
        height: 0;
        content: '';
        visibility: hidden;
        overflow: hidden;
      }
    }
  }
}
</style>
