package org.example.laboratorybookingmanagement.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Slf4j
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;
    @Test
    void findAll() {
        List<Course> courseList = courseRepository.findAll();
        for(Course c : courseList) {
            log.debug("{}",c);
        }
    }
    @Test
    void deleteById() {
        courseRepository.deleteById("6");
    }
    @Test
    void deleteAllById() {
        List<String> ids = new ArrayList<>();
        ids.add("4");
        ids.add("5");
        courseRepository.deleteAllById(ids);
    }
    @Test
    void deleteAll() {
        courseRepository.deleteAll();
    }
    @Test
    void sava() {
        Course c = Course.builder()
                .name("数据库系统原理")
                .experimentHour(8)
                .type(1)
                .clazz("软件工程1-2班")
                .teacherId("01JFMF27TJ1TF9YQ240HG7P7EW")
                .semester("23-2")
                .quantity(45)
                .build();
        courseRepository.save(c);
    }
    @Test
    void updateById() {

    }

    @Test
    void findCoursesByTeacherId() {
        List<Course> courses = courseRepository.findCoursesByTeacherId("1");
        for(Course c : courses) {
            log.debug("{}",c);
        }
    }

    @Test
    void findCountByTeacherIdAndCourseId() {
        int count = courseRepository.findCountByTeacherIdAndCourseId("01JFJ951VSQA8B0DE5P1PTV5EJ","3");
        log.debug("{}",count);
    }

    @Test
    void deleteCourseByTeacherIdAndCourseId() {

        courseRepository.deleteCourseByTeacherIdAndCourseId("10","4");
    }

    @Test
    void deleteCoursesByTeacherIdAndCourseIds() {
        List<String> ids = new ArrayList<>();
        ids.add("4");
        ids.add("5");
        courseRepository.deleteCoursesByTeacherIdAndCourseIds("10",ids);
    }
}