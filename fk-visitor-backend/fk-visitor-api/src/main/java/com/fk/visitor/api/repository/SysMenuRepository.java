package com.fk.visitor.api.repository;

import com.fk.visitor.api.entity.SysMenu;
import cn.kinkii.novice.framework.repository.ModelRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuRepository extends ModelRepository<SysMenu, Long> {

    SysMenu findByCode(String code);

    SysMenu findByAlias(String alias);

    List<SysMenu> findByParentCode(String parentCode);

    List<SysMenu> findByOrderByCodeAsc();

    @Modifying
    @Query("update SysMenu m set m.parentCode=:newParentCode where m.parentCode=:oldParentCode")
    void updateParentCode(@Param("oldParentCode") String oldParentCode, @Param("newParentCode") String newParentCode);

}
