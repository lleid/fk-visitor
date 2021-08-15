package com.fk.visitor.lib.repository.system;

import com.fk.visitor.lib.entity.system.OperateLog;
import cn.kinkii.novice.framework.repository.ModelRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperateLogRepository extends ModelRepository<OperateLog, String> {

}
