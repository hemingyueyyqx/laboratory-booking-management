package org.example.laboratorybookingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.Course;
import org.example.laboratorybookingmanagement.dto.Course1;
import org.example.laboratorybookingmanagement.exception.Code;
import org.example.laboratorybookingmanagement.exception.XException;
import org.example.laboratorybookingmanagement.repository.CourseRepository;
import org.example.laboratorybookingmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherService {
    private  final UserRepository userRepository;
    private final CourseRepository courseRepository;
    //指定老师id查全部课表
    @Transactional
    public List<Course1> getCourses(String teacherId) {
        return userRepository.findCourseByTeacherId(teacherId);
    }
    //基于老师id获取全部课程信息
    @Transactional
    public List<Course> findCoursesByTeacherId(String id) {
        return courseRepository.findCoursesByTeacherId(id);
    }
    //添加课程
    @Transactional
    public void addCourse(Course course ) {

        courseRepository.save(course);
    }
    //基于课程id删除某一门课
    @Transactional
    public void deleteCourseById(String id) {
        courseRepository.deleteById(id);
    }
    //基于多个id删除多门课
    @Transactional
    public void deleteCoursesByIds(List<String> ids) {
        courseRepository.deleteAllById(ids);
    }
    //删除指定老师id的全部课程
    @Transactional
    public void deleteAllCoursesByTeacherId(String id) {
        courseRepository.deleteCoursesByTeacherId(id);
    }
    //修改课程
    @Transactional
    public void updateCourse(Course course) {
        Course course1 = courseRepository.findById(course.getId()).orElse(null);
        if(course1 == null) {
            throw XException.builder().number(Code.ERROR).message("课程不存在").build();
        }else {
            course1.setName(course.getName());
            course1.setClazz(course.getClazz());
            course1.setQuantity(course.getQuantity());
            course1.setType(course.getType());
            course1.setSemester(course.getSemester());
            course1.setExperimentHour(course.getExperimentHour());

        }
        courseRepository.save(course1);
    }
}
