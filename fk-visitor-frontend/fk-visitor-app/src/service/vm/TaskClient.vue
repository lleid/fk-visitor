<script>
import Vue from 'vue'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import token from '@/store/modules/token-store'

const TASK_API = {
    TASK_DEL: '/task/del',
    TASK_QUERY_PAGE: '/task/query/page'
}

export default {
    data () {
        return {
            dataList: []
        }
    },
    mounted: function () {
        this.connect()
    },
    beforeDestroy: function () {
        this.disconnect()
    },
    methods: {
        connect () {
            this.socket = new SockJS(process.env.VUE_APP_API_BASE_URL + '/ws?k-client=id:' + token.clientId + '&k-access-token=' + token.accessToken.token)
            this.stompClient = Stomp.over(this.socket)
            var headers = {}
            this.stompClient.connect(headers, (frame) => {
                this.stompClient.subscribe('/tasks/list', (msg) => {
                    console.log(msg)
                })
            }, (err) => {
                console.log(err)
            })
        },
        disconnect () {
            if (this.stompClient != null) {
                this.stompClient.disconnect()
                console.log('Check')
            }
        },
        del (id) {
            return Vue.axios.delete(TASK_API.TASK_DEL + '/' + id)
        },
        queryPage (params, config) {
            return Vue.axios.get(TASK_API.TASK_QUERY_PAGE, { params: params }, config)
        }
    }
}
</script>
