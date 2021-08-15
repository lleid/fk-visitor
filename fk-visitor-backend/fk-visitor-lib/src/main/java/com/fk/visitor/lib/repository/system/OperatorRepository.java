package com.fk.visitor.lib.repository.system;

import com.fk.visitor.lib.entity.system.Operator;
import com.fk.visitor.lib.entity.system.OperatorRole;
import cn.kinkii.novice.framework.repository.ModelRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface OperatorRepository extends ModelRepository<Operator, String> {

    Operator findByUsername(String username);

    Operator findByName(String name);

    Operator findByMobile(String mobile);

    Operator findByEmail(String email);

    Boolean existsByRolesContains(OperatorRole role);

    @Query(value = "SELECT o.* FROM t_operator o, t_operator_role r, t_operator_role_map m WHERE o.id = m.operator_id AND r.id = m.role_id AND r.key_code = :roleCode ORDER BY CONVERT(o.`name` USING gbk)",
            nativeQuery = true)
    List<Operator> findOperatorByRoleCodeAndNameOrder(@Param("roleCode") String roleCode);

    @Modifying
    @Transactional
    @Query("UPDATE Operator o SET o.lastLoginAt=:lastLoginAt WHERE o.id=:id")
    void updateLastLoginAt(@Param("id") String id, @Param("lastLoginAt") Date lastLoginAt);

}
