package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Station;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends ModelRepository<Station, Long> {

}
