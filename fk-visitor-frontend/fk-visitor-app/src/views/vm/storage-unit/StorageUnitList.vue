<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="32">
          <a-col :md="6" :sm="3">
            <a-form-item label="云存储名称">
              <a-input v-model="queryParam.name" placeholder />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="3">
            <a-form-item label="镜像名称">
              <a-input v-model="queryParam.imageName" placeholder />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="3">
            <a-form-item label="类别">
              <a-radio-group defaultValue="both" @change="handleTypeSel">
                <a-radio-button value="isSys">系统盘</a-radio-button>
                <a-radio-button value="nonSys">存储盘</a-radio-button>
                <a-radio-button value="both">全部</a-radio-button>
              </a-radio-group>
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
              <a-button type="primary" ghost icon="plus" @click="$refs.createModal.add()">新建存储盘</a-button>
            </div>
          </a-col>
          <a-col :md="3" :sm="1">
            <div class="table-operator">
              <a-button type="primary" ghost icon="plus" @click="$refs.createModalSys.add()">新建系统盘</a-button>
            </div>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <s-table
      ref="table"
      size="default"
      :rowKey="record => record.id"
      :columns="columns"
      :data="loadData"
      :showPagination="true"
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
              <a @click="handleAttach(record)" v-if="!record.attachedTo">挂载</a>
              <a @click="handleExtend(record)" v-if="!record.attachedTo">扩容</a>
              <a @click="handleDetach(record)" v-if="record.attachedTo">解除挂载</a>
            </a-menu-item>
            <a-menu-item>
              <a @click="handleDel(record)">删除</a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span>
    </s-table>
    <storage-unit-extend ref="extendModal" @ok="handleOk" @start-timer="startTimer" @stop-timer="stopTimer" />
    <storage-unit-update ref="updateModal" @ok="handleOk" @start-timer="startTimer" @stop-timer="stopTimer" />
    <storage-unit-attach ref="attachModal" @ok="handleOk" @start-timer="startTimer" @stop-timer="stopTimer" />
    <storage-unit-create ref="createModal" @ok="handleOk" @start-timer="startTimer" @stop-timer="stopTimer" />
    <storage-unit-create-sys ref="createModalSys" @ok="handleOk" @start-timer="startTimer" @stop-timer="stopTimer" />
  </a-card>
</template>
<script>
import { STable } from '@/components'
import * as StorageUnitService from '@/service/vm/StorageUnitService'
import StorageUnitCreate from './StorageUnitCreate'
import StorageUnitCreateSys from './StorageUnitCreateSys'
import StorageUnitAttach from './StorageUnitAttach'
import StorageUnitUpdate from './StorageUnitUpdate'
import StorageUnitExtend from './StorageUnitExtend'

export default {
  components: {
    STable,
    StorageUnitCreate,
    StorageUnitCreateSys,
    StorageUnitAttach,
    StorageUnitUpdate,
    StorageUnitExtend
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
          title: '容量',
          width: '150px',
          dataIndex: 'size',
          customRender: function (val) {
            return val + 'GB'
          }
        },
        {
          title: '描述',
          width: '150px',
          dataIndex: 'description'
        },
        {
          title: '所使用镜像',
          width: '150px',
          dataIndex: 'image.name'
        },
        {
          title: '状态',
          width: '75px',
          dataIndex: 'statusZh'
        },
        {
          title: '挂载于',
          width: '150px',
          dataIndex: 'attachedTo.name'
        },
        {
          title: '可启动',
          width: '75px',
          dataIndex: 'bootable',
          customRender: (text) => text ? '是' : '否'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '130px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      loadData: parameter => {
        return StorageUnitService.queryPage(Object.assign(parameter, this.queryParam), {
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
    handleTypeSel (e) {
      this.queryParam['bootable'] = null
      if (e.target.value === 'isSys') {
        this.queryParam['bootable'] = true
      }
      if (e.target.value === 'nonSys') {
        this.queryParam['bootable'] = false
      }
    },
    startTimer () {
      this.refreshTimerActive = true
    },
    stopTimer () {
      this.refreshTimerActive = false
    },
    handleOk () {
      this.$refs.table.refresh()
    },
    handleExtend (record) {
      var extendReq = {}
      extendReq['blockStorageUnit'] = { id: record.id, size: record.size }
      extendReq['newSize'] = record.size
      this.$refs.extendModal.extend(extendReq)
    },
    handleAttach (record) {
      var attachReq = {}
      attachReq['vmInstance'] = { id: '' }
      attachReq['mountPoint'] = '/dev/vdb'
      attachReq['blockStorageUnit'] = { id: record.id }
      this.$refs.attachModal.attach(attachReq)
    },
    handleDetach (record) {
      const that = this
      this.$confirm({
        title: '确认操作',
        content: '',
        onOk () {
          StorageUnitService.detach(record.id, {
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
          })
        },
        onCancel () {}
      })
    },
    handleUpdate (record) {
      this.$refs.updateModal.update(record)
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认操作',
        content: '',
        onOk () {
          StorageUnitService.del(record.id, {
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
