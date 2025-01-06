package org.example.laboratorybookingmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.Appointment;
import org.example.laboratorybookingmanagement.dox.Course;
import org.example.laboratorybookingmanagement.dox.Lab;
import org.example.laboratorybookingmanagement.dto.Appointment1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class TeacherServiceTest {
    @Autowired
    private TeacherService teacherService;
    @Test
    void getCourses() {
        List<Appointment1> appointment1s = teacherService.getCourses("24-1","01JFJ5CWY6FD4XTTHR42FBS6A4");
        for(Appointment1 appointment1 : appointment1s) {
            log.debug("{}",appointment1);
        }

    }

    @Test
    void getHours() {
        int hours = teacherService.getHours("01JFJ5CWY6FD4XTTHR42FBS6A4", "1");
        log.debug("{}",hours);
    }

    @Test
    void deleteCoursesByIds() {
        List<String> ids = new ArrayList<>();
        ids.add("1");
        teacherService.deleteCoursesByIds("01JFJ5CWY6FD4XTTHR42FBS6A4",ids);
    }

    @Test
    void getLabs() {
        Map<String, List<Lab>> map= teacherService.getLabs("01JFJ5CWY6FD4XTTHR42FBS6A4","1");
        for (Map.Entry<String, List<Lab>> entry : map.entrySet()) {
            log.debug("键: " + entry.getKey());
            log.debug("对应的值列表: ");
            for (Lab lab : entry.getValue()) {
                log.debug("{}",lab);
            }
            System.out.println("------------------------");
        }


    }

    @Test
    void appointCourse() {
        Appointment a = new Appointment().builder()
                .course("{\"id\":\"1\",\"name\":\"高等数学\"}")
                .teacher("{\"id\":\"01JFJ5CWY6FD4XTTHR42FBS6A4\",\"name\":\"王五\"}")
                .labId("6")
                .labName("906")
                .semester("24-2")
                .week(1)
                .dayofweek(1)
                .section(1)
                .nature("课程预约")
                .build();
        teacherService.appointCourse(a);
    }

    @Test
    void findCoursesByTeacherIdAndSemaster() {
        List<Course> courses = teacherService.findCoursesByTeacherIdAndSemaster("24-2","01JFJ5CWY6FD4XTTHR42FBS6A4");
        for (Course c: courses) {
            log.debug("{}",c);
        }
    }

    @Test
    void getallteacherstable() {
        List<Appointment1> appointment1List = teacherService.getallteacherstable("Js09");
        for(Appointment1 a:appointment1List) {
            log.debug("{}",a);
        }
    }
}