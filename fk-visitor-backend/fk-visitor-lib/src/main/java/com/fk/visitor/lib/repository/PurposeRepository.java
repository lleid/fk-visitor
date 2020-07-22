package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Purpose;
import org.springframework.stereotype.Repository;

@Repository
public interface PurposeRepository extends ModelRepository<Purpose, Long> {
    Purpose findByCnName(String cnName);
    Purpose findByEnName(String enName);
}
