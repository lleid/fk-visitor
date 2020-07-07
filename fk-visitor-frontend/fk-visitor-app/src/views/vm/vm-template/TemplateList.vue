<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="32">
          <a-col :md="6" :sm="3">
            <a-form-item label="机型名称">
              <a-input v-model="queryParam.name" placeholder />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="3">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button
                style="margin-left: 8px"
                @click="() => {queryParam = {};$refs.table.refresh(true)}"
              >重置</a-button>
            </span>
          </a-col>
        </a-row>
        <a-row>
          <a-col :md="3" :sm="1">
            <div class="table-operator">
              <a-button type="primary" ghost icon="plus" @click="$refs.createModal.add()">新建机型</a-button>
            </div>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <c-table
      ref="table"
      size="default"
      :rowKey="record => record.id"
      :columns="columns"
      :data="loadData"
      :showPagination="true"
    >
      <span slot="action" slot-scope="text, record">
          <a @click="handleDel(record)">删除</a>
      </span>
    </c-table>
    <template-create ref="createModal" @ok="handleOk" @start-timer="startTimer" @stop-timer="stopTimer" />
  </a-card>
</template>
<script>
import { STable } from '@/components'
import * as TemplateService from '@/service/vm/TemplateService'
import TemplateCreate from './TemplateCreate'

export default {
  components: {
    STable,
    TemplateCreate
  },
  data () {
    return {
      rowSelection: {
        columnWidth: '1px',
        selectedRowKeys: []
      },
      queryParam: {
      },
      columns: [
        {
          title: '名称',
          width: '150px',
          dataIndex: 'name'
        },
        {
          title: 'vCPU数量',
          width: '150px',
          dataIndex: 'vCPUs'
        },
        {
          title: '内存大小',
          width: '150px',
          dataIndex: 'ram',
          customRender: function (val) {
            return val + 'MB'
          }
        },
        {
          title: '硬盘大小',
          width: '150px',
          dataIndex: 'size',
          customRender: function (val) {
            return val + 'GB'
          }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '130px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      loadData: parameter => {
        return TemplateService.queryPage(Object.assign(parameter, this.queryParam), {
          showLoading: false,
          showSuccess: false,
          showError: false
        }).then(res => {
          return res
        })
      },
      refreshTimer: '',
      refreshTimerActive: true
    }
  },
  mounted () {
    this.refreshTimer = setInterval(
      () => {
        if (this.refreshTimerActive) {
          this.$refs.table.refresh(true)
        }
      },
      30000
    )
  },
  beforeDestroy () {
    clearInterval(this.refreshTimer)
  },
  methods: {
    startTimer () {
      this.refreshTimerActive = true
    },
    stopTimer () {
      this.refreshTimerActive = false
    },
    handleOk () {
      this.$refs.table.refresh()
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认操作',
        content: '',
        onOk () {
          TemplateService.del(record.id, {
            showLoading: false,
            showSuccess: false,
            showError: false
          }).then(res => {
            that.$message.info(res.message)
            clearInterval(that.refreshTimer)
            that.refreshTimer = setInterval(
                    () => {
                      if (that.refreshTimerActive) {
                        that.$refs.table.refresh(true)
                      }
              },
              30000
            )
            that.$refs.table.refresh(true)
          }).catch(err => {
            that.$message.info(err.message)
          })
        },
        onCancel () {}
      })
    }
  }
}
</script>
