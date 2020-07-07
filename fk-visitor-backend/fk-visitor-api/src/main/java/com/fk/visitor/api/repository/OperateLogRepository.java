package com.fk.visitor.api.repository;

import com.fk.visitor.api.entity.OperateLog;
import cn.kinkii.novice.framework.repository.ModelRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperateLogRepository extends ModelRepository<OperateLog, String> {

}
