package com.fk.visitor.api.controller.banner;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.banner.beans.BannerQuery;
import com.fk.visitor.lib.entity.Banner;
import com.fk.visitor.lib.repository.BannerRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banner")
@Api(tags = {"轮播图"}, description = "/banner")
public class BannerQueryController extends BaseJpaQueryController<Banner, Long, BannerQuery> {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    protected ModelRepository<Banner, Long> getRepository() {
        return bannerRepository;
    }
}
