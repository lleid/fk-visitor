package com.fk.visitor.api.controller.operatorrole.req;

import com.fk.visitor.lib.entity.OperatorRole;
import cn.kinkii.novice.framework.controller.request.GenericRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OperatorRoleReq extends GenericRequest<OperatorRole> {

    private String name;

    private String keyCode;

    private Boolean isSupervisor = false;

    private List<Long> menuIds;

    private List<String> permissionUrls;

}
