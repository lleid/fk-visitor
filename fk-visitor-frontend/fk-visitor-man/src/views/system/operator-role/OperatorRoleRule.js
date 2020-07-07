import * as OperatorRoleService from '@/service/system/OperatorRoleService'
import * as RuleUtils from '@/utils/RuleUtils'

const OperatorRoleRuleBuilder = {
    build: (data) => {
        return {
            name: RuleUtils.buildRules('角色名称', [
                { required: true },
                {
                    asyncValidator: (rule, value, callback) => {
                        const params = { keyCode: value }
                        if (data.id !== undefined) {
                            params['id'] = data.id
                        }
                        OperatorRoleService.checkName(params, {
                            showLoading: false,
                            showSuccess: false
                        }).then(res => {
                            if (!res) {
                                callback(new Error('角色名称已存在'))
                            }
                            callback()
                        })
                    },
                    trigger: 'blur'
                }
            ]),
            keyCode: RuleUtils.buildRules('别名', [
                { required: true },
                { pattern: /^[\w.-]+$/m, message: `别名只能包括字母, 数字, '.'以及'-'` },
                {
                    asyncValidator: (rule, value, callback) => {
                        const params = { keyCode: value }
                        if (data.id !== undefined) {
                            params['id'] = data.id
                        }
                        OperatorRoleService.checkKeyCode(params, {
                            showLoading: false,
                            showSuccess: false
                        }).then(res => {
                            if (!res) {
                                callback(new Error('角色别名已存在'))
                            }
                            callback()
                        })
                    },
                    trigger: 'blur'
                }
            ])
        }
    }
}

export {
    OperatorRoleRuleBuilder
}
