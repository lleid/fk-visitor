package com.fk.visitor.api.controller.global;

import io.swagger.annotations.Api;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RestController
@RequestMapping("/sys/api")
@Api(tags = {"API管理"}, description = "系统API查询")
public class SysApiQueryController implements InitializingBean {

    private List<String> apis = new LinkedList<>();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<String> getAllApi() {
        return apis;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public List<String> queryApi(String keyword) {
        List<String> result;
        result = apis.stream()
                .filter((String api) -> api.contains(keyword))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        RequestMappingHandlerMapping mapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
//        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
//        Set<String> apiSet = new LinkedHashSet<>();
//        for (RequestMappingInfo info : map.keySet()) {
//            Set<String> patterns = info.getPatternsCondition().getPatterns();
//            for (String url : patterns) {
//                apiSet.addAll(parseUrl(url));
//            }
//        }
//        apis.addAll(apiSet);
//        Collections.sort(apis);
//        log.info(KJsonUtils.toJson((apis)));
    }

    private static List<String> parseUrl(String url) {
        List<String> result = new ArrayList<>();
        result.add(url);
        List<String> parsedUrl = new ArrayList<>(Arrays.asList(url.split("/")));
        int i = parsedUrl.size() - 1;
        while (i > 0) {
            parsedUrl.remove(i);
            result.add(String.join("/", parsedUrl) + "/*");
            i--;
        }
        return result;
    }
}
