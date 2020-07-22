package com.fk.visitor.lib.repository;

import com.fk.visitor.lib.entity.OperatorRole;
import cn.kinkii.novice.framework.repository.ModelRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRoleRepository extends ModelRepository<OperatorRole, Long> {

    OperatorRole findByName(String name);

    OperatorRole findByKeyCode(String keyCode);

}
