package com.fk.visitor.lib.repository.mdata;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.mdata.Protocol;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolRepository extends ModelRepository<Protocol, Long> {
    Protocol findByName(String name);
}
