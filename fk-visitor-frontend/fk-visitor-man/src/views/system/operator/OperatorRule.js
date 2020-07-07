import * as operatorService from '@/service/system/OperatorService'
import * as RuleUtils from '@/utils/RuleUtils'

const OperatorRuleBuiler = {
    build: (data) => {
        return {
            username: RuleUtils.buildRules('用户名', [
                { required: true },
                RuleUtils.builder.moreWordCharacters(),
                {
                    asyncValidator: (rule, value, callback) => {
                        const param = { username: value }
                        if (data !== undefined && data.id !== undefined) {
                            param['id'] = data.id
                        }
                        operatorService.checkUsername(param, {
                            showLoading: false,
                            showSuccess: false,
                            showError: false }).then(res => {
                            if (!res) {
                                callback(new Error('用户名已存在'))
                            } else {
                                callback()
                            }
                        })
                    }
                }
            ]),
            name: RuleUtils.buildRules('姓名', [
                { required: true }
            ]),
            password: RuleUtils.buildRules('密码', [
                { required: !(data !== undefined && data.id !== undefined) },
                RuleUtils.builder.moreWordCharacters(),
                { min: 8 }
            ]),
            plainPassword: RuleUtils.buildRules('确认密码', [
                { required: !(data !== undefined && data.id !== undefined) },
                {
                    validator: (rule, value, callback) => {
                        if (value !== data.password) {
                            callback(new Error('密码输入不一致'))
                        } else {
                            callback()
                        }
                    }
                }
            ]),
            mobile: RuleUtils.buildRules('手机号', [
                { required: true },
                RuleUtils.builder.cnMobile(),
                {
                    asyncValidator: (rule, value, callback) => {
                        const param = { mobile: value }
                        if (data !== undefined && data.id !== undefined) {
                            param['id'] = data.id
                        }
                        operatorService.checkMobile(param, {
                            showLoading: false,
                            showSuccess: false,
                            showError: false }).then(res => {
                            if (!res) {
                                callback(new Error('手机号已存在'))
                            } else {
                                callback()
                            }
                        })
                    }
                }
            ]),
            roles: RuleUtils.buildRules('角色', [
                { required: true, type: 'array' }
            ])
        }
    }
}

export default OperatorRuleBuiler
