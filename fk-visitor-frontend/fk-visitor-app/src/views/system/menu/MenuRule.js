import * as SysMenuService from '@/service/system/SysMenuService'
import * as RegexUtils from '@/utils/RegexUtils'
import * as RuleUtils from '@/utils/RuleUtils'

const MenuRuleBuilder = {
    build: (data) => {
        return {
            name: RuleUtils.buildRules('菜单名称', [
                { required: true }
            ]),
            alias: RuleUtils.buildRules('菜单别名', [
                { required: true },
                RuleUtils.builder.onlyLetters(),
                {
                    asyncValidator: (rule, value, callback) => {
                        const params = { alias: value }
                        if (data !== undefined && data.id !== undefined) {
                            params['id'] = data.id
                        }
                        SysMenuService.checkAlias(params, {
                            showLoading: false,
                            showSuccess: false
                        }).then(res => {
                            if (!res) {
                                callback(new Error('菜单别名已存在'))
                            }
                            callback()
                        })
                    }
                }
            ]),
            url: RuleUtils.buildRules('菜单路径', [
                { required: true },
                {
                    validator (rule, value, callback) {
                        return RegexUtils.isUrl(value) || RegexUtils.isPath(value)
                    },
                    message: '菜单路径必须是有效的路由或网址'
                }
            ]),
            relatedUrlList: RuleUtils.buildRules('关联路径', [
                {
                    validator (rule, value, callback) {
                        if (value instanceof Array) {
                            for (let i = 0; i < value.length; i++) {
                                if (!RegexUtils.isPath(value)) {
                                    return false
                                }
                            }
                            return true
                        } else if (value instanceof String) {
                            return RegexUtils.isPath(value)
                        }
                        return true
                    },
                    message: '关联路径必须是有效的路由',
                    trigger: 'change'
                }
            ]),
            code: RuleUtils.buildRules('菜单路径', [
                { required: true },
                {
                    asyncValidator: (rule, value, callback) => {
                        const params = { code: value }
                        if (data !== undefined && data.id !== undefined) {
                            params['id'] = data.id
                        }
                        SysMenuService.checkCode(params, {
                            showLoading: false,
                            showSuccess: false
                        }).then(res => {
                            if (!res) {
                                callback(new Error('菜单序号已存在'))
                            }
                            callback()
                        })
                    }
                }
            ])
        }
    }
}

export default MenuRuleBuilder
