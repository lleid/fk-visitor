import enquireJs from 'enquire.js'

export const MediaQueryKey = {
    XS: 'xs',
    SM: 'sm',
    MD: 'md',
    LG: 'lg',
    XL: 'xl',
    XXL: 'xxl'
}

const PRO_LAYOUT_QUERY = {
    XS: 'screen and (max-width: 575px)',
    SM: 'screen and (min-width: 576px) and (max-width: 767px)',
    MD: 'screen and (min-width: 768px) and (max-width: 992px)',
    LG: 'screen and (min-width: 992px) and (max-width: 1199px)',
    XL: 'screen and (min-width: 1200px) and (max-width: 1599px)',
    XXL: 'screen and (min-width: 1600px)'
}

const PRO_LAYOUT_QUERY_HANDLERS = {}

export const regMediaQuery = (callback) => {
    Object.keys(PRO_LAYOUT_QUERY).forEach(mq => {
        PRO_LAYOUT_QUERY_HANDLERS[mq] = {
            match: () => {
                callback && typeof callback === 'function' && callback(MediaQueryKey[mq])
            }
        }
    })
    Object.keys(PRO_LAYOUT_QUERY).forEach(mq => {
        enquireJs.register(PRO_LAYOUT_QUERY[mq], PRO_LAYOUT_QUERY_HANDLERS[mq])
    })
}

export const unregMediaQuery = () => {
    Object.keys(PRO_LAYOUT_QUERY).forEach(mq => {
        enquireJs.unregister(PRO_LAYOUT_QUERY[mq], PRO_LAYOUT_QUERY_HANDLERS[mq])
    })
}
