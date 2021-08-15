package com.fk.visitor.lib.repository.system;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.system.Station;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends ModelRepository<Station, Long> {
    Station findByName(String name);
}
