package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Appointment;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AppointmentRepository extends ModelRepository<Appointment, Long> {

    Appointment findByOrderAtAndInviteCode(Date orderAt, String inviteCode);

}
