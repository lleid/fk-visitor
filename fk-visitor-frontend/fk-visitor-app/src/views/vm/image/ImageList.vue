<template>
  <page-header-wrapper :menu-data="menuData" :content-width="themeConfig.contentWidth">
    <div slot="title-extra-content">
      <a-button type="primary" icon="plus" @click="$refs.createModal.add()">新建镜像</a-button>
    </div>
    <a-card slot="children" :bordered="false" class="list-card">
      <c-table
        ref="imageList"
        :rowKey="record => record.id"
        :columns="columns"
        :data-loader="query"
        :showPagination="true"
        :rowSelection="null"
      >
        <div slot="toolbar">
          <a-input addon-before="镜像名称">
            <div slot="addonAfter">
              <a-icon type="search" />
            </div>
          </a-input>
        </div>
        <span slot="action" slot-scope="text, record">
          <a @click="$refs.updateModal.edit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a @click="handleDel(record)">删除</a>
        </span>
      </c-table>
      <image-create
        ref="createModal"
        @ok="handleOk"
        @start-timer="startTimer"
        @stop-timer="stopTimer"
      />
      <image-update
        ref="updateModal"
        @ok="handleOk"
        @start-timer="startTimer"
        @stop-timer="stopTimer"
      />
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { mapState } from 'vuex'
import * as ImageService from '@/service/vm/ImageService'
import ImageCreate from './ImageCreate'
import ImageUpdate from './ImageUpdate'

export default {
  components: {
    ImageCreate,
    ImageUpdate
  },
  computed: {
    ...mapState({
      menuData: state => state.operator.menus,
      themeConfig: state => state.theme.config
    })
  },
  data () {
    return {
      queryParam: {
      },
      columns: [
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title: '状态',
          dataIndex: 'stateDisplay'
        },
        {
          title: '系统类型',
          dataIndex: 'systemType',
          media: 'md'
        },
        {
          title: '存储路径',
          dataIndex: 'localPath',
          media: 'xl'
        },
        {
          title: '创建于',
          width: '200px',
          dataIndex: 'createAt',
          media: 'xxl'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      query: async parameter => {
        try {
          const result = await ImageService.queryPage(Object.assign(parameter, this.queryParam), {
            showLoading: false,
            showSuccess: false,
            showError: false
          })
          return result
        } catch (e) {
        }
      },
      refreshTimer: '',
      refreshTimerActive: true
    }
  },
  mounted () {
    this.refreshTimer = setInterval(
      () => {
        if (this.refreshTimerActive) {
          this.$refs.imageList.refresh(true)
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
      this.$refs.imageList.refresh()
    },
    handleDel (record) {
      const that = this
      this.$confirm({
        title: '确认操作',
        content: '',
        onOk () {
          ImageService.del(record.id, {
            showLoading: false
          }).then(res => {
            clearInterval(that.refreshTimer)
            that.refreshTimer = setInterval(
              () => {
                if (that.refreshTimerActive) {
                  that.$refs.imageList.refresh(true)
                }
              },
              30000
            )
            that.$refs.imageList.refresh(true)
          })
        },
        onCancel () { }
      })
    }
  }
}
</script>
