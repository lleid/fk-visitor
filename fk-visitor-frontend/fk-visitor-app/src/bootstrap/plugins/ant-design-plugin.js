import {
  Base,
  Anchor,
  AutoComplete,
  BackTop,
  Calendar,
  Collapse,
  ConfigProvider,
  Descriptions,
  Result,
  Layout,
  Input,
  InputNumber,
  Button,
  Switch,
  Radio,
  Checkbox,
  Select,
  Card,
  Form,
  FormModel,
  Transfer,
  TreeSelect,
  Tree,
  Affix,
  Row,
  Col,
  Modal,
  Table,
  Tabs,
  Icon,
  Badge,
  Popover,
  Dropdown,
  List,
  Avatar,
  Breadcrumb,
  Steps,
  Spin,
  Menu,
  Drawer,
  Tooltip,
  Alert,
  Tag,
  Divider,
  DatePicker,
  TimePicker,
  Upload,
  Progress,
  Skeleton,
  Popconfirm,
  Carousel,
  message,
  notification
} from 'ant-design-vue'

const components = [
  Base,
  Affix,
  Anchor,
  AutoComplete,
  Alert,
  Avatar,
  BackTop,
  Badge,
  Breadcrumb,
  Button,
  Calendar,
  Card,
  Collapse,
  Checkbox,
  Col,
  DatePicker,
  Divider,
  Dropdown,
  Form,
  FormModel,
  Transfer,
  Icon,
  Input,
  InputNumber,
  Layout,
  List,
  Menu,
  Modal,
  Popconfirm,
  Popover,
  Progress,
  Radio,
  Row,
  Select,
  Spin,
  Steps,
  Switch,
  Table,
  Tree,
  TreeSelect,
  Tabs,
  Tag,
  TimePicker,
  Tooltip,
  Upload,
  Drawer,
  Skeleton,
  Carousel,
  ConfigProvider,
  Result,
  Descriptions
]

const install = Vue => {
  components.map(function (component) {
    Vue.use(component)
  })

  Vue.prototype.$message = message
  Vue.prototype.$notification = notification
  Vue.prototype.$info = Modal.info
  Vue.prototype.$success = Modal.success
  Vue.prototype.$error = Modal.error
  Vue.prototype.$warning = Modal.warning
  Vue.prototype.$confirm = Modal.confirm
  Vue.prototype.$destroyAll = Modal.destroyAll
}

export default {
  install
}
