package com.fk.visitor.api.utils;

import com.fk.visitor.api.entity.Operator;
import com.fk.visitor.api.service.OperatorAccount;
import cn.kinkii.novice.security.access.KUrlAccessAuthentication;

import java.security.Principal;

public class OperatorUtils {

    public static Operator parse(Principal principal) {
        return ((OperatorAccount) (((KUrlAccessAuthentication) principal).getAccountPrincipal())).getCurrentOperator();
    }

}
