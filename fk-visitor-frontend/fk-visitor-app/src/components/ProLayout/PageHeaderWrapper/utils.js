import { findMenusByPath } from '../BaseMenu/utils'

export const convertFromList = (breadcrumbList) => {
    return breadcrumbList
        .map((item) => {
            const { title, href } = item
            return {
                path: href,
                breadcrumbName: title
            }
        })
}

export const convertFromLocation = (location, menuData, locale) => {
    const menusFromLocation = findMenusByPath(menuData, location)
    if (menusFromLocation !== undefined && menusFromLocation.length > 0) {
        return menusFromLocation.map((item) => {
            return {
                path: item.path,
                breadcrumbName: getMenuLocalName(item, locale)
            }
        })
    }
    return []
}
export const genHomeBreadcrumb = (home, homeIcon) => {
    if ((home !== undefined && home.trim() !== '') || homeIcon === true) {
        return {
            home: true,
            homeIcon: homeIcon,
            path: '/',
            breadcrumbName: home
        }
    }
}

const getMenuLocalName = (menuItem, locale) => {
    if (locale !== undefined && menuItem.localeName !== undefined) {
        return menuItem.localeName[locale] === undefined ? menuItem.name : menuItem.localeName[locale]
    } else {
        return menuItem.name
    }
}
