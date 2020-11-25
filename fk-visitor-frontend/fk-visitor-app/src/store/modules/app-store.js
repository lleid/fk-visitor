export const APP_MUTATIONS = {
  UPDATE_LOADING_STATE: 'UPDATE_LOADING_STATE',
  UPDATE_MOBILE_STATE: 'UPDATE_MOBILE_STATE',
  UPDATE_MEDIA_SIZE: 'UPDATE_MEDIA_SIZE',
  UPDATE_LANGUAGE: 'UPDATE_LANGUAGE',
  UPDATE_ISHOME: 'UPDATE_ISHOME'
}

const app = {
  state: {
    loading: false,
    isMobile: false,
    mediaSize: '',
    language: 'CN',
    isHome: true
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
    },
    UPDATE_ISHOME: (state, isHome) => {
      state.isHome = isHome
    }
  }
}

export default app
