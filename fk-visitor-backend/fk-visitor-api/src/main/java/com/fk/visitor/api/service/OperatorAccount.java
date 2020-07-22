package com.fk.visitor.api.service;

import com.fk.visitor.lib.entity.Operator;
import cn.kinkii.novice.security.model.KAccount;
import lombok.Getter;

@Getter
public class OperatorAccount extends KAccount {

    private final Operator currentOperator;

    public OperatorAccount(String id, String username, String encryptedPassword, Operator currentOperator) {
        super(id, username, encryptedPassword);
        this.currentOperator = currentOperator;
    }
}
