// import * as storageUnitService from '@/service/system/StorageUnitService'

const build = function (form) {
    const rules = {}
    rules['name'] = [
        { required: true, message: '请输入名称', trigger: 'blur' }
    ]
    rules['description'] = [
        { required: true, message: '请输入描述', trigger: 'blur' }
    ]
    rules['images'] = [
        { required: true, message: '请选择镜像', trigger: 'blur' }
    ]
    return rules
}

export {
    build
}
