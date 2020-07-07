package com.fk.visitor.api.repository;

import com.fk.visitor.api.entity.OperatorRole;
import cn.kinkii.novice.framework.repository.ModelRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRoleRepository extends ModelRepository<OperatorRole, Long> {

    OperatorRole findByName(String name);

    OperatorRole findByKeyCode(String keyCode);

}
