package org.example.laboratorybookingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.laboratorybookingmanagement.dox.Course;
import org.example.laboratorybookingmanagement.service.TeacherService;
import org.example.laboratorybookingmanagement.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    //获取指定老师的全部课表
    @GetMapping("courses/{teacherId}")
    public ResultVo getCourses(@PathVariable String teacherId) {
        return ResultVo.success(teacherService.getCourses(teacherId));
    }
    //获取老师的全部课程信息
    @GetMapping("coursesInfo/{teacherId}")
    public ResultVo getCoursesInfo(@PathVariable String teacherId) {
        return ResultVo.success(teacherService.findCoursesByTeacherId(teacherId));
    }
    //添加课程
    @PostMapping("addCourse")
    public ResultVo addCourse(@RequestBody Course course) {
        teacherService.addCourse(course);
        return ResultVo.ok();
    }
    //更新课程
    @PostMapping("updateCourse")
    public ResultVo postCourse(@RequestBody Course course) {
        teacherService.updateCourse(course);
        return ResultVo.ok();
    }
    //删除指定id对应的课程
    @DeleteMapping("deleteCourse/{courseId}")
    public ResultVo deleteCourse(@PathVariable String courseId) {
        teacherService.deleteCourseById(courseId);
        return ResultVo.ok();
    }
    //删除指定老师的所有课程
    @DeleteMapping("deleteAllCourses/{teacherId}")
    public ResultVo deleteCoursesByTeacherId(@PathVariable String teacherId) {
        teacherService.deleteAllCoursesByTeacherId(teacherId);
        return ResultVo.ok();
    }
    //删除多个课程id对应的课程
    @DeleteMapping("deleteCourses/{courseIds}")
    public  ResultVo deleteCoursesByiIds(@PathVariable List<String> courseIds) {
        teacherService.deleteCoursesByIds(courseIds);
        return ResultVo.ok();
    }
}
