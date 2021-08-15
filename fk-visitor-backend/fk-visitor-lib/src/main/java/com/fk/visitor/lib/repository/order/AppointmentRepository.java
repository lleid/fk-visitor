package com.fk.visitor.lib.repository.order;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.order.Appointment;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends ModelRepository<Appointment, Long> {

    List<Appointment> findByOrderAtAndInviteCode(Date orderAt, String inviteCode);

}
