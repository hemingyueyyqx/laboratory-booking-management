package org.example.laboratorybookingmanagement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.Appointment;
import org.example.laboratorybookingmanagement.dox.Course;
import org.example.laboratorybookingmanagement.dto.Appointment1;
import org.example.laboratorybookingmanagement.service.TeacherService;
import org.example.laboratorybookingmanagement.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/teacher/")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    //获取指定老师id，和学期的全部课表
    @GetMapping("coursetable/{semester}")
    public ResultVo getCourses(@PathVariable String semester ,@RequestAttribute("uid") String teacherId) {
        return ResultVo.success(teacherService.getCourses(semester,teacherId));
    }
    //获取老师的全部课程信息
    @GetMapping("courses/{semester}")
    public ResultVo getCoursesInfo(@PathVariable String semester, @RequestAttribute("uid") String teacherId) {
        return ResultVo.success(teacherService.findCoursesByTeacherIdAndSemaster(semester,teacherId));
    }
    //添加课程
    @PostMapping("addcourse")
    public ResultVo addCourse(@RequestAttribute("role") String role,@RequestAttribute("uid") String teacherId, @RequestBody Course course) {
        teacherService.addCourse(role,teacherId, course);
        return ResultVo.ok();
    }
    //更新课程
    @PutMapping("updatecourse")
    public ResultVo postCourse(@RequestBody Course course) {
        teacherService.updateCourse(course);
        return ResultVo.ok();
    }
    //删除指定老师指定id对应的课程,要判断是否有预约记录
    @DeleteMapping("course/{courseId}")
    public ResultVo deleteCourse(@RequestAttribute("uid")String teacherId, @PathVariable String courseId) {
        teacherService.deleteCourseById(teacherId,courseId);
        return ResultVo.ok();
    }
    //删除指定老师的所有课程
    @DeleteMapping("courses")
    public ResultVo deleteCoursesByTeacherId(@RequestAttribute("uid") String teacherId) {
        teacherService.deleteAllCoursesByTeacherId(teacherId);
        return ResultVo.ok();
    }
    //删除指定老师多个课程id对应的课程
    @DeleteMapping("courses/{courseIds}")
    public  ResultVo deleteCoursesByiIds(@RequestAttribute("uid") String teacherId,@PathVariable List<String> courseIds) {
        log.debug("{}",courseIds);
        teacherService.deleteCoursesByIds(teacherId, courseIds);
        return ResultVo.ok();
    }
    //基于老师id,课程id,获取已经选了多少学时
    @GetMapping("hours/{courseId}")
    public ResultVo getHours(@RequestAttribute("uid") String teacherId, @PathVariable String courseId) {
        return ResultVo.success(teacherService.getHours(teacherId, courseId));
    }
    ////基于老师id,课程id，获取人数可用和不可用的实验室
    @GetMapping("labs/{courseId}")
    public ResultVo getlabs(@RequestAttribute("uid") String teacherId, @PathVariable String courseId) {
        return ResultVo.success(teacherService.getLabs(teacherId,courseId ));
    }
    ////基于实验室id，查预约表
    @GetMapping("appointment/{semester}/{labId}")
    public ResultVo getAppointment(@PathVariable String semester, @PathVariable String labId) {
        return ResultVo.success(teacherService.getAppointment(semester,labId));
    }
    //预约课程
    @PostMapping("appointment")
    public ResultVo appointmentCourse(@RequestBody Appointment appointment) {
        teacherService.appointCourse(appointment);
        return ResultVo.ok();
    }
    //基于老师id,课程id移除对应的预约信息
    @PostMapping("deleteappointments/{semester}")
    public ResultVo deleteAppointment(@RequestAttribute("uid")String teacherId,@PathVariable String semester,@RequestBody List<String> courseIds) {
        teacherService.deleteAppointment(teacherId,semester,courseIds);
        return ResultVo.ok();
    }
    //获取角色是老师的所有预约表加课程信息
    @GetMapping("allteacherstable")
    public ResultVo getallteacherstable(@RequestAttribute("role") String role) {
        return ResultVo.success(teacherService.getallteacherstable(role));
    }
    //删除指定课程id,指定学期，指定周次的预约记录
    @PostMapping("deleteappointment")
    public ResultVo deleteAppointment(@RequestAttribute("uid")String tid,@RequestAttribute("role")String role,@RequestBody Appointment1 appointment1) {
        teacherService.deleteAppointment1(tid,role,appointment1);
        return ResultVo.ok();
    }
}
