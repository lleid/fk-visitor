import VueStorage from 'vue-ls'

const install = (Vue) => {
    let namespace = 'NS__'
    if (process.env.VUE_APP_PROJECT_CODE !== undefined && process.env.VUE_APP_PROJECT_CODE.toString().trim() !== '') {
        namespace = process.env.VUE_APP_PROJECT_CODE.toString().trim() + '__'
    }

    Vue.use(VueStorage, {
        namespace: namespace,
        name: 'ls',
        storage: 'local'
    })

    Vue.use(Object.assign({}, VueStorage), {
        namespace: namespace,
        name: 'ses',
        storage: 'session'
    })
}

export default {
    install
}
