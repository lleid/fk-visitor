package com.fk.visitor.api.repository;

import com.fk.visitor.api.entity.SysPermission;
import cn.kinkii.novice.framework.repository.ModelRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermissionRepository extends ModelRepository<SysPermission, Long> {

    Boolean existsByUrl(String url);

    SysPermission findFirstByUrlEquals(String url);

}
