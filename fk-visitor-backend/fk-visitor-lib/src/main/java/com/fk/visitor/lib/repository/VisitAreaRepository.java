package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.VisitArea;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitAreaRepository extends ModelRepository<VisitArea, Long> {
    VisitArea findByCnName(String cnName);

    VisitArea findByEnName(String enName);
}
