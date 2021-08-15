package com.fk.visitor.api.controller.system.operatorrole.req;

import com.fk.visitor.lib.entity.system.OperatorRole;
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
