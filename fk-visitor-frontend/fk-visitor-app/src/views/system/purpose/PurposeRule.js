import * as RuleUtils from '@/utils/RuleUtils'
import * as PurposeService from '@/service/system/PurposeService'
const PurposeRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('站点名称', [
        { required: true },
        {
          asyncValidator: (rule, value, callback) => {
            console.log('asyncValidator')
            const param = { name: value }
            if (data !== undefined && data.id !== undefined) {
              param['id'] = data.id
            }
            PurposeService
              .checkName(param, {
                showLoading: false,
                showSuccess: false,
                showError: false
              })
              .then(res => {
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

export default PurposeRuleBuilder
