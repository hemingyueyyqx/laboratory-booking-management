package org.example.laboratorybookingmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dto.Course1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class TeacherServiceTest {
    @Autowired
    private TeacherService teacherService;
    @Test
    void getCourses() {
        List<Course1> course1List = teacherService.getCourses("01JFJ5CWY6FD4XTTHR42FBS6A4");
        for(Course1 course1 : course1List) {
            log.debug("{}",course1);
        }

    }
}