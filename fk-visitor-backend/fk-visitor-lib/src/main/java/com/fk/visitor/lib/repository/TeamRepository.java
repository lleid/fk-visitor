package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Team;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends ModelRepository<Team, Long> {
}
