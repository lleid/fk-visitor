const buileRule = (fullField, rule) => {
    rule['fullField'] = fullField
    if (rule.required === true && rule.whitespace === undefined) {
        rule['whitespace'] = true
    }
    if (rule.trigger === undefined) {
        rule['trigger'] = 'blur'
    }
    if (rule.message !== undefined && rule.message.indexOf('$fullField$') > -1) {
        rule.message = rule.message.replace('$fullField$', fullField)
    }
}

export const buildRules = (fullField, rules) => {
    if (rules instanceof Array) {
        rules.forEach((rule) => {
            buileRule(fullField, rule)
        })
    } else if (rules instanceof Object) {
        buileRule(fullField, rules)
    }
    return rules
}

export const builder = {
    cnMobile: () => {
        return {
            pattern: /^1\d{10}$/,
            message: '$fullField$必须是有效的11位手机号'
        }
    },
    onlyLetters: () => {
        return {
            pattern: /^[A-Za-z]*$/,
            message: '$fullField$必须由大/小写字母组成'
        }
    },
    wordCharacters: () => {
        return {
            pattern: /^\w+$/,
            message: '$fullField$必须由数字、字母以及\'_\'组成'
        }
    },
    moreWordCharacters: () => {
        return {
            pattern: /^[\w.-]+$/,
            message: '$fullField$必须由数字、字母以及\'_\'、\'.\'、\'-\'组成'
        }
    },
    password: () => {
        return {
            pattern: /^(?=.*[a-zA-Z])(?=.*[\d~!@#$%^&*()_+`\-={}[\]:";'<>?,./]).{8,}$/,
            message: '$fullField$必须由至少1个字母以及至少1个数字或特殊符号组成'
        }
    }
}
