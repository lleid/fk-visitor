import * as RuleUtils from '@/utils/RuleUtils'
import * as SignOutReasonService from '@/service/system/SignOutReasonService'
const SignOutReasonRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('名称', [
        { required: true },
        {
          asyncValidator: (rule, value, callback) => {
            const param = { name: value }
            if (data !== undefined && data.id !== undefined) {
              param['id'] = data.id
            }
            SignOutReasonService.checkName(param, {
              showLoading: false,
              showSuccess: false,
              showError: false
            }).then(res => {
              if (!res) {
                callback(new Error('名称已存在'))
              } else {
                callback()
              }
            })
          }
        }
      ])
    }
  }
}

export default SignOutReasonRuleBuilder
