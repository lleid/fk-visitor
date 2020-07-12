package com.fk.visitor.api.controller.banner;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.global.resp.UploadResp;
import com.fk.visitor.api.entity.Operator;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.entity.Banner;
import com.fk.visitor.lib.repository.BannerRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@RestController
@RequestMapping("/banner")
@Api(tags = {"轮播图"})
@SuppressWarnings("all")
public class BannerCRUDController extends BaseModelCRUDController<Banner, Long> {

    @Autowired
    private BannerRepository bannerRepository;
    
    @Value("${project.upload.url}")
    private String FILE_BASE_URL;

    @Value("${project.upload.path}")
    private String UPLOADED_FOLDER;

    @Override
    protected ModelRepository<Banner, Long> getRepository() {
        return bannerRepository;
    }

    @Override
    protected Banner handleCreate(Banner model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Banner handleUpdate(Banner model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Banner handlePatch(Banner model, Principal principal, HttpServletRequest request) {
        return model;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public UploadResp upload(MultipartFile file, Principal principal, HttpServletRequest request) {
        Operator operator = OperatorUtils.parse(principal);
        if (file != null) {
            try {
                byte[] bytes = file.getBytes();

                String fileName = file.getOriginalFilename();
                String url = FILE_BASE_URL + fileName;

                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);

                Banner banner = new Banner(fileName, url);

                if (operator.getStation() != null) {
                    banner.setStation(operator.getStation());
                }
                bannerRepository.create(banner);

                return new UploadResp(banner.getId(), banner.getName(), banner.getUrl(), "done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
