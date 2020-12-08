package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.SignOutReason;
import org.springframework.stereotype.Repository;

@Repository
public interface SignOutReasonRepository extends ModelRepository<SignOutReason, Long> {
    SignOutReason findByName(String name);
}
