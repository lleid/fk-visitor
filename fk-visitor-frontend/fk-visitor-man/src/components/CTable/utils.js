import enquireJs from 'enquire.js'

export const toggleFullScreen = (element) => {
    if (document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || document.msFullscreenElement) {
        const exitMethod = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen
        if (exitMethod) {
            exitMethod.call(document)
        } else if (window.ActiveXObject !== undefined) {
            // eslint-disable-next-line no-undef
            const wscript = new ActiveXObject('WScript.Shell')
            if (wscript !== null) {
                wscript.SendKeys('{ESCAPE}')
            }
        }
    } else {
        const requestMethod = element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen
        if (requestMethod) {
            requestMethod.call(element)
        } else if (window.ActiveXObject !== undefined) {
            // eslint-disable-next-line no-undef
            const wscript = new ActiveXObject('WScript.Shell')
            if (wscript !== null) {
                wscript.SendKeys('{F11}')
            }
        }
    }
}

export const MediaQueryKey = {
    XS: 0,
    SM: 1,
    MD: 2,
    LG: 3,
    XL: 4,
    XXL: 5
}

const CTABLE_QUERY = {
    XS: 'screen and (max-width: 575px)',
    SM: 'screen and (min-width: 576px) and (max-width: 767px)',
    MD: 'screen and (min-width: 768px) and (max-width: 992px)',
    LG: 'screen and (min-width: 992px) and (max-width: 1199px)',
    XL: 'screen and (min-width: 1200px) and (max-width: 1599px)',
    XXL: 'screen and (min-width: 1600px)'
}

const CTABLE_QUERY_HANDLERS = {}

export const regMediaQuery = (callback) => {
    Object.keys(CTABLE_QUERY).forEach(mq => {
        CTABLE_QUERY_HANDLERS[mq] = {
            match: () => {
                callback && typeof callback === 'function' && callback(MediaQueryKey[mq])
            }
        }
    })
    Object.keys(CTABLE_QUERY).forEach(mq => {
        enquireJs.register(CTABLE_QUERY[mq], CTABLE_QUERY_HANDLERS[mq])
    })
}

export const unregMediaQuery = () => {
    Object.keys(CTABLE_QUERY).forEach(mq => {
        enquireJs.unregister(CTABLE_QUERY[mq], CTABLE_QUERY_HANDLERS[mq])
    })
}
