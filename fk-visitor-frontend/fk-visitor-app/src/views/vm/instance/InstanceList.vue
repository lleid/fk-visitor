<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" ghost icon="plus" @click="$refs.createModal.add()">新建实例</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <a-form layout="inline" class="search-form">
        <a-form-item label="实例名称">
          <a-input v-model="queryParam.name" placeholder />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="$refs.operatorList.refresh()">查询</a-button>
          <a-button
            class="operate-button"
            @click="() => {queryParam = {};$refs.operatorList.refresh()}"
          >重置</a-button>
        </a-form-item>
      </a-form>
      <c-table
        ref="instanceList"
        size="default"
        :rowKey="record => record.id"
        :columns="columns"
        :data-loader="query"
        :autoRefresh="true"
        :autoRefreshInterval="30000"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleUpdate(record)">更新</a>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多
              <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleStart(record)">启动</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleStop(record)">停止</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleDel(record)">删除</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </c-table>
      <instance-create ref="createModal" @ok="handleOk" @start-timer="startTimer" @stop-timer="stopTimer" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { mapState } from 'vuex'
import * as InstanceService from '@/service/vm/InstanceService'
import InstanceCreate from './modules/InstanceCreate'

export default {
  components: {
    InstanceCreate
  },
  data () {
    return {
      queryParam: {

      },
      columns: [
        {
          title: '主机名称',
          width: '150px',
          dataIndex: 'name'
        }, {
          title: '状态',
          width: '150px',
          dataIndex: 'status'
        }, {
          title: 'vCPU数量',
          width: '150px',
          dataIndex: 'template.vCPUs'
        }, {
          title: '内存大小(MB)',
          width: '150px',
          dataIndex: 'template.ram'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '130px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      query: async (param) => {
        return InstanceService.queryPage(Object.assign(param, this.queryParam), {
          showLoading: false
        }).then(res => {
          return res
        })
      },
      refreshTimer: '',
      refreshTimerActive: true
    }
  },
  computed: {
    ...mapState({
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config
    })
  },
  beforeDestroy () {
    this.$refs.instanceList.stopAutoRefresh()
  },
  methods: {
    startTimer () {
      this.$refs.instanceList.startAutoRefresh()
    },
    stopTimer () {
      this.$refs.instanceList.stopAutoRefresh()
    },
    handleOk () {
      this.$refs.instanceList.refresh()
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认操作',
        content: '',
        onOk () {
          InstanceService.del(record.id, {
            showLoading: false,
            showSuccess: false,
            showError: false
          }).then(res => {
            that.$message.info(res.message)
            that.$refs.instanceList.stopAutoRefresh()
            that.$refs.instanceList.refresh()
            that.$refs.instanceList.startAutoRefresh()
          }).catch(err => {
            that.$message.info(err.message)
          })
        },
        onCancel () {}
      })
    },
    handleStart (record) {
      InstanceService.start(record.id, {
            showLoading: false,
            showSuccess: false,
            showError: false
          }).then(res => {
            this.$message.info(res.message)
            this.$refs.instanceList.stopAutoRefresh()
            this.$refs.instanceList.refresh()
            this.$refs.instanceList.startAutoRefresh()
          })
    },
    handleStop (record) {
      InstanceService.stop(record.id, {
            showLoading: false,
            showSuccess: false,
            showError: false
          }).then(res => {
            this.$message.info(res.message)

            this.$refs.instanceList.stopAutoRefresh()
            this.$refs.instanceList.refresh()
            this.$refs.instanceList.startAutoRefresh()
          })
    }
  }
}
</script>
<style>
.operate-button {
  margin-left: 8px;
}
.search-form {
 margin-bottom:10px;
}
</style>
