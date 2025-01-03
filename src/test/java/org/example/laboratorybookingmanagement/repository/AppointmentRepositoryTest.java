package org.example.laboratorybookingmanagement.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
class AppointmentRepositoryTest {
    @Autowired
    private  AppointmentRepository appointmentRepository;
    @Test
    void findAllByLabIdAndSemester() {
        List<Appointment> appointments = appointmentRepository.findAllByLabIdAndSemester("24-1", "5");
        for (Appointment a : appointments) {
            log.debug("{}",a);
        }
    }
    @Test
    void save() {
        Appointment a = new Appointment().builder()
                .course("{\"id\":\"2\",\"name\":\"大学物理\"}")
                .teacher("{\"id\":\"01JFJ5CWY6FD4XTTHR42FBS6A4\",\"name\":\"王五\"}")
                .labId("6")
                .labName("906")
                .semester("24-2")
                .week(2)
                .dayofweek(1)
                .section(1)
                .nature("课程预约")
                .build();
        appointmentRepository.save(a);
    }

    @Test
    void deleteAllByTeacherAndCourse() {
        appointmentRepository.deleteAllByTeacherAndCourse("01JFJ5CWY6FD4XTTHR42FBS6A4","1");
    }
}