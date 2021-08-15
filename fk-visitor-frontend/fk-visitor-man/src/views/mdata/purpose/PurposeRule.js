import * as RuleUtils from '@/utils/RuleUtils'
import * as PurposeService from '@/service/mdata/PurposeService'
const PurposeRuleBuilder = {
  build: data => {
    return {
      cnName: RuleUtils.buildRules('中文名称', [
        { required: true },
        {
          asyncValidator: (rule, value, callback) => {
            const param = { name: value }
            if (data !== undefined && data.id !== undefined) {
              param['id'] = data.id
            }
            PurposeService.checkCnName(param, {
              showLoading: false,
              showSuccess: false,
              showError: false
            }).then(res => {
              if (!res) {
                callback(new Error('中文名称已存在'))
              } else {
                callback()
              }
            })
          }
        }
      ]),
      enName: RuleUtils.buildRules('英文名称', [
        { required: true },
        {
          asyncValidator: (rule, value, callback) => {
            const param = { name: value }
            if (data !== undefined && data.id !== undefined) {
              param['id'] = data.id
            }
            PurposeService.checkEnName(param, {
              showLoading: false,
              showSuccess: false,
              showError: false
            }).then(res => {
              if (!res) {
                callback(new Error('英文名称已存在'))
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
