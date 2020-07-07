// import * as storageUnitService from '@/service/system/StorageUnitService'

const build = function (form) {
    const rules = {}
    rules['name'] = [
        { required: true, message: '请输入名称', trigger: 'blur' }
    ]
    return rules
}

export {
    build
}
