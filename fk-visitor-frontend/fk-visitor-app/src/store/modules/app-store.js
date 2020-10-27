export const APP_MUTATIONS = {
  UPDATE_LOADING_STATE: 'UPDATE_LOADING_STATE',
  UPDATE_MOBILE_STATE: 'UPDATE_MOBILE_STATE',
  UPDATE_MEDIA_SIZE: 'UPDATE_MEDIA_SIZE',
  UPDATE_LANGUAGE: 'UPDATE_LANGUAGE'
}

const app = {
  state: {
    loading: false,
    isMobile: false,
    mediaSize: '',
    language: 'CN'
  },
  mutations: {
    UPDATE_LOADING_STATE: (state, loading) => {
      state.loading = loading
    },
    UPDATE_MOBILE_STATE: (state, isMobile) => {
      state.isMobile = isMobile
    },
    UPDATE_MEDIA_SIZE: (state, mediaSize) => {
      state.mediaSize = mediaSize
    },
    UPDATE_LANGUAGE: (state, language) => {
      state.language = language
    }
  }
}

export default app
