import ThemeConfig from '@/config/theme.config'

export const THEME_MUTATIONS = {
    UPDATE_THEME: 'UPDATE_THEME'
}

const theme = {
    state: {
        config: ThemeConfig
    },
    mutations: {
        UPDATE_THEME: (state, config) => {
            state.config = config
        }
    }
}

export default theme
