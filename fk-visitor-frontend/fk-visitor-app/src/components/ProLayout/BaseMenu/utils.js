export const filterMenuData = (menuData) => {
    if (menuData === undefined || !Array.isArray(menuData) || menuData.length === 0) {
        return
    }
    return menuData
        .filter((item) => {
            return item && item.name && !item.hideInMenu
        })
        .map((item) => {
            if (
                item.children !== undefined &&
                Array.isArray(item.children) &&
                !item.hideChildrenInMenu
            ) {
                const children = filterMenuData(item.children)
                if (children.length) return { ...item, children }
            }
            return { ...item, children: undefined }
        })
        .filter((item) => item)
}

export const findOpenKeysByPath = (menuData, path) => {
    if (menuData === undefined) {
        return []
    }
    const keys = _findOpenKeysByPath(filterMenuData(menuData), path)
    // 去除叶子菜单的key
    keys.pop()
    return keys
}

const _findOpenKeysByPath = (renderMenuData, path) => {
    let _openKeys = []
    for (const item of renderMenuData) {
        if (item.children === undefined) {
            if (item.path === path) {
                _openKeys = [(item.key === undefined ? item.path : item.key)]
                break
            }
        } else {
            const childKeys = _findOpenKeysByPath(item.children, path)
            if (childKeys.length > 0) {
                _openKeys.push(item.key === undefined ? item.path : item.key)
                _openKeys = _openKeys.concat(childKeys)
                break
            }
        }
    }
    return _openKeys
}

export const findSelectedKeysByPath = (menuData, path) => {
    if (menuData !== undefined) {
        const key = _findSelectedKeysByPath(filterMenuData(menuData), path)
        return key === undefined ? [] : [key]
    }
}

const _findSelectedKeysByPath = (renderMenuData, path) => {
    for (const item of renderMenuData) {
        if (item.children === undefined) {
            if (item.path === path) {
                return item.key === undefined ? item.path : item.key
            }
        } else {
            const childrenSelected = _findSelectedKeysByPath(item.children, path)
            if (childrenSelected !== undefined) {
                return childrenSelected
            }
        }
    }
}

export const findMenusByPath = (menuData, path) => {
    if (menuData !== undefined) {
        const _menus = _findMenusByPath(filterMenuData(menuData), path)
        return _menus === undefined ? [] : _menus
    }
}

const _findMenusByPath = (renderMenuData, path) => {
    let _menus = []
    for (const item of renderMenuData) {
        if (item.children === undefined) {
            if (item.path === path) {
                _menus = [item]
                break
            }
        } else {
            const childMenus = _findMenusByPath(item.children, path)
            if (childMenus.length > 0) {
                _menus.push(item)
                _menus = _menus.concat(childMenus)
                break
            }
        }
    }
    return _menus
}
