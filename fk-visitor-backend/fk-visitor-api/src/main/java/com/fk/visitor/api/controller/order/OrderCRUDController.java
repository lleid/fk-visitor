package com.fk.visitor.api.controller.order;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.controller.BaseResult;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuerySpecification;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.order.query.OrderQuery;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.entity.*;
import com.fk.visitor.lib.repository.AppointmentRepository;
import com.fk.visitor.lib.repository.OrderRepository;
import com.fk.visitor.lib.repository.PurposeRepository;
import com.fk.visitor.lib.repository.VisitAreaRepository;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(tags = {"访客记录"})
public class OrderCRUDController extends BaseModelCRUDController<Order, Long> {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PurposeRepository purposeRepository;
    @Autowired
    private VisitAreaRepository visitAreaRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Value("${project.upload.url}")
    private String FILE_BASE_URL;

    @Value("${project.upload.path}")
    private String UPLOADED_FOLDER;

    private static final String FILE_DATE_FORMAT = "yyyyMMddHHmmss";

    private static final String suffix = ".png";

    @Override
    protected ModelRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    private static String getRandomFileName(String fileSuffix) {
        return DateFormatUtils.format(new Date(), FILE_DATE_FORMAT) + RandomStringUtils.randomAlphabetic(6) + fileSuffix;
    }

    @Override
    protected Order handleCreate(Order model, Principal principal, HttpServletRequest request) {
        Operator operator = OperatorUtils.parse(principal);

        model.setOrderType(Order.ORDER);

        String purposeId = request.getParameter("purposeId");

        if (StringUtils.isNotBlank(purposeId)) {
            Purpose purpose = purposeRepository.findById(Long.parseLong(purposeId)).orElseThrow(() -> new InvalidParamException("参数异常"));
            model.setPurpose(purpose);
        }

        String visitAreaId = request.getParameter("visitAreaId");

        if (StringUtils.isNotBlank(visitAreaId)) {
            VisitArea visitArea = visitAreaRepository.findById(Long.parseLong(visitAreaId)).orElseThrow(() -> new InvalidParamException("参数异常"));
            model.setVisitArea(visitArea);
        }

        String appointmentId = request.getParameter("appointmentId");

        if (StringUtils.isNotBlank(appointmentId)) {
            Appointment appointment = appointmentRepository.findById(Long.parseLong(appointmentId)).orElseThrow(() -> new InvalidParamException("参数异常"));
            appointment.setIsCame(true);
            appointmentRepository.update(appointment);
            model.setOrderType(Order.APPOINTMENT);
        }


        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
            try {
                if (file != null) {
                    byte[] bytes = file.getBytes();
                    String fileName = getRandomFileName(suffix);
                    String url = FILE_BASE_URL + fileName;

                    Path path = Paths.get(UPLOADED_FOLDER + fileName);
                    Files.write(path, bytes);

                    model.setAvatar(url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isBlank(model.getAvatar())) {
            model.setAvatar(FILE_BASE_URL + "avatar.png");
        }

        if (operator.getStation() != null) {
            model.setStation(operator.getStation());
        }

        return model;
    }

    @RequestMapping(value = "/signout")
    @ResponseBody
    public BaseResult signOut(@RequestParam(name = "mobile") String mobile, Principal principal) {
        OrderQuery query = new OrderQuery();
        query.setMobile(mobile);
        List<Order> list = orderRepository.findAll(new JpaQuerySpecification<>(query));

        if (list.size() > 0) {
            Order order = list.get(0);

            order.setIsSignOut(true);
            order.setSignOutAt(new Date());
            orderRepository.update(order);
        }

        return BaseResult.success("签出成功");
    }


    @RequestMapping(value = "/signout/{id}")
    @ResponseBody
    public BaseResult signOut(@PathVariable Long id, Principal principal) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new InvalidParamException("参数异常"));

        order.setIsSignOut(true);
        order.setSignOutAt(new Date());

        orderRepository.update(order);
        return BaseResult.success("操作成功");
    }


    @RequestMapping(value = "/history/{id}")
    @ResponseBody
    public BaseResult history(@PathVariable Long id, Principal principal) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new InvalidParamException("参数异常"));

        Order model = new Order();

        BeanUtils.copyProperties(order, model);

        model.setId(null);
        model.setSignOutAt(null);
        model.setIsSignOut(false);
        model.setVisitAt(new Date());

        orderRepository.create(model);
        return BaseResult.success("操作成功");
    }

    @Override
    protected Order handleUpdate(Order model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Order handlePatch(Order model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
