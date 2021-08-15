package com.fk.visitor.api.controller.global;

import com.fk.visitor.api.controller.global.resp.EditorResp;
import com.fk.visitor.api.controller.global.resp.UploadResp;
import com.fk.visitor.lib.entity.mdata.Banner;
import com.fk.visitor.lib.repository.mdata.BannerRepository;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
@Api(tags = {"上传图片"}, description = "上传图片")
@SuppressWarnings("all")
public class UploadController {

    @Value("${project.upload.url}")
    private String FILE_BASE_URL;
    @Value("${project.upload.path}")
    private String UPLOADED_FOLDER;

    @Autowired
    private BannerRepository bannerRepository;

    @RequestMapping(value = "/pub/banner/upload", method = RequestMethod.POST)
    @ResponseBody
    public UploadResp upload(MultipartFile file, HttpServletRequest request) {
        if (file != null) {
            try {
                byte[] bytes = file.getBytes();

                String fileName = file.getOriginalFilename();
                String url = FILE_BASE_URL + fileName;

                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);

                Banner banner = new Banner(fileName, url);

                bannerRepository.create(banner);

                return new UploadResp(banner.getId(), banner.getName(), banner.getUrl(), "done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping(value = "/pub/editor/upload", method = RequestMethod.POST)
    @ResponseBody
    public EditorResp editorUpload(HttpServletRequest request) {
        EditorResp resp = new EditorResp();

        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;

            MultiValueMap<String, MultipartFile> map = mRequest.getMultiFileMap();

            map.values();
            if (map != null && map.size() > 0) {
                List<String> data = Lists.newArrayList();
                map.forEach((key, files) -> {
                    try {
                        for (MultipartFile file : files) {
                            byte[] bytes = file.getBytes();

                            String fileName = file.getOriginalFilename();
                            String url = FILE_BASE_URL + fileName;

                            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                            Files.write(path, bytes);
                            data.add(url);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                resp.setData(data);
                return resp;
            }
        }
        return resp;
    }
}
