package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Banner;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends ModelRepository<Banner, Long> {

}
