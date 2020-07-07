import * as PersonalService from '@/service/system/PersonalService'
import * as RuleUtils from '@/utils/RuleUtils'

const BasicUpdateRule = {
    mobile: RuleUtils.buildRules('手机', [
        RuleUtils.builder.cnMobile(),
        {
            asyncValidator: (rule, value, callback) => {
                PersonalService.checkMyMobile(value, {
                    showLoading: false,
                    showSuccess: false
                }).then(res => {
                    if (!res) {
                        callback(new Error('手机号已存在'))
                    } else {
                        callback()
                    }
                }).catch((e) => {
                    callback()
                })
            }
        }
    ]),
    email: RuleUtils.buildRules('邮箱', [
        { type: 'email' },
        {
            asyncValidator: (rule, value, callback) => {
                PersonalService.checkMyEmail(value, {
                    showLoading: false,
                    showSuccess: false
                }).then(res => {
                    if (!res) {
                        callback(new Error('邮箱已存在'))
                    } else {
                        callback()
                    }
                }).catch(() => {
                    callback()
                })
            }
        }
    ]),
    gender: RuleUtils.buildRules('性别', [
        { type: 'enum', enum: ['MALE', 'FEMALE'], message: '请正确选择性别' }
    ]),
    birthday: RuleUtils.buildRules('生日', [
        { type: 'date' }
    ])
}

export default BasicUpdateRule
