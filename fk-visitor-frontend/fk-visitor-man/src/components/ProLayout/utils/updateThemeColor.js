import client from 'webpack-theme-color-replacer/client'
import generate from '@ant-design/colors/lib/generate'

const themeConfig = {
    '#1890ff': 'daybreak',
    '#F5222D': 'dust',
    '#FA541C': 'volcano',
    '#FAAD14': 'sunset',
    '#13C2C2': 'cyan',
    '#52C41A': 'green',
    '#2F54EB': 'geekblue',
    '#722ED1': 'purple'
}

const invertKeyValues = (obj) =>
    Object.keys(obj).reduce((acc, key) => {
        acc[obj[key]] = key
        return acc
    }, {})

/**
 * #1890ff -> daybreak
 * @param val
 */
export function genThemeToString (val) {
    return val && themeConfig[val] ? themeConfig[val] : val
}

/**
 * daybreak-> #1890ff
 * @param val
 */
export function genStringToTheme (val) {
    const stringConfig = invertKeyValues(themeConfig)
    return val && stringConfig[val] ? stringConfig[val] : val
}

const genColorPalette = (color) => {
    // 淡化（即less的tint）
    const lightens = new Array(9).fill().map((t, i) => {
        return client.varyColor.lighten(color, i / 10)
    })
    // colorPalette变换得到颜色值
    return lightens.concat(generate(color)).concat(client.varyColor.toNum3(color.replace('#', '')).join(','))
}

const changeColor = (newColor) => {
    var options = {
        newColors: genColorPalette(newColor),
        changeUrl (cssUrl) {
            return `/${cssUrl}`
        }
    }
    return client.changer.changeColor(options, Promise)
}

const updateThemeColor = (newColor) => {
    if (newColor === undefined || newColor.trim() === '') {
        return
    }
    if (!newColor.startsWith('#')) {
        newColor = genStringToTheme(newColor)
    }
    if (!newColor.startsWith('#')) {
        console.warn('Unsupported theme - ', newColor)
        return
    }
    changeColor(newColor)
}

export default updateThemeColor
