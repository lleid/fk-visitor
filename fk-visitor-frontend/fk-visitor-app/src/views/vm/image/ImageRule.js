import * as RuleUtils from '@/utils/RuleUtils'

const ImageRuleBuilder = {
    build: (data) => {
        return {
            name: RuleUtils.buildRules('镜像名称', [
                { required: true }
            ])
        }
    }
}

export default ImageRuleBuilder
