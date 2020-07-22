package com.fk.visitor.lib.repository;

import com.fk.visitor.lib.entity.OperateLog;
import cn.kinkii.novice.framework.repository.ModelRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperateLogRepository extends ModelRepository<OperateLog, String> {

}
