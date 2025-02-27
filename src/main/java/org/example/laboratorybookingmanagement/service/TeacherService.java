package org.example.laboratorybookingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.Appointment;
import org.example.laboratorybookingmanagement.dox.Course;
import org.example.laboratorybookingmanagement.dox.Lab;
import org.example.laboratorybookingmanagement.dox.User;
import org.example.laboratorybookingmanagement.dto.Appointment1;
import org.example.laboratorybookingmanagement.exception.Code;
import org.example.laboratorybookingmanagement.exception.XException;
import org.example.laboratorybookingmanagement.repository.AppointmentRepository;
import org.example.laboratorybookingmanagement.repository.CourseRepository;
import org.example.laboratorybookingmanagement.repository.LabRepository;
import org.example.laboratorybookingmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherService {
    private  final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LabRepository labRepository;
    private final AppointmentRepository appointmentRepository;
    //指定老师id查全部课表
    @Transactional
    public List<Appointment1> getCourses(String semester ,String teacherId) {
        User u  = userRepository.findById(teacherId).orElse(null);
        if(u == null) {
            throw XException.builder().number(Code.ERROR).message("老师不存在").build();
        }
        return userRepository.findCourseByTeacherIdAndSemester(semester,teacherId);
    }
    //基于老师id获取全部课程信息
    @Transactional
    public List<Course> findCoursesByTeacherIdAndSemaster(String semester, String id) {
        User u  = userRepository.findById(id).orElse(null);
        if(u == null) {
            throw XException.builder().number(Code.ERROR).message("老师不存在").build();
        }
        return courseRepository.findCoursesByTeacherIdAndSemester(semester,id);
    }
    //添加课程
    @Transactional
    public void addCourse(String role,String teacherId, Course course ) {
        if(!role.equals(User.Teacher)) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
        course.setTeacherId(teacherId);
        courseRepository.save(course);
    }
    //基于课程id删除某一门课
    @Transactional
    public void deleteCourseById( String teacherId, String courseId) {
        User u = userRepository.findById(teacherId).orElse(null);
        Course c = courseRepository.findById(courseId).orElse(null);
        if(u == null || c == null) {
            throw new XException().builder().number(Code.ERROR).message("老师或课程不存在").build();
        }
        int count = courseRepository.findCountByTeacherIdAndCourseId(teacherId,courseId);
        if(count>0) {
            throw new XException().builder().number(Code.ERROR).message("课程存在预约记录，请先移除预约记录").build();
        }
        courseRepository.deleteCourseByTeacherIdAndCourseId(teacherId, courseId);
    }
    //基于老师id和多个id删除多门课
        @Transactional(rollbackFor = XException.class)
        public void deleteCoursesByIds(String teacherId, List<String> ids) {
            StringBuilder errorMessage = new StringBuilder();
            boolean hasNonExistentCourse = false;
            boolean hasCourseWithAppointments = false;
            StringBuilder nonExistentCourseIds = new StringBuilder();
            StringBuilder courseIdsWithAppointments = new StringBuilder();

            for (String id : ids) {
                Course c = courseRepository.findById(id).orElse(null);
                if (c == null) {
                    hasNonExistentCourse = true;
                    if (nonExistentCourseIds.length() > 0) {
                        nonExistentCourseIds.append(" ");
                    }
                    nonExistentCourseIds.append(c.getName());
                    continue;
                }

                int count = courseRepository.findCountByTeacherIdAndCourseId(teacherId, id);
                if (count > 0) {
                    hasCourseWithAppointments = true;
                    if (courseIdsWithAppointments.length() > 0) {
                        courseIdsWithAppointments.append(", ");
                    }
                    courseIdsWithAppointments.append(c.getName());
                }
            }

            if (hasNonExistentCourse) {
                errorMessage.append("不存在的课程：");
                errorMessage.append(nonExistentCourseIds.toString());
            }
            if (hasCourseWithAppointments) {
                if (hasNonExistentCourse) {
                    errorMessage.append("；");
                }
                errorMessage.append("存在有预约记录的课程,请先移除预约记录：");
                errorMessage.append(courseIdsWithAppointments.toString());
            }

            if (hasNonExistentCourse || hasCourseWithAppointments) {
                throw XException.builder()
                        .number(Code.ERROR)
                        .message(errorMessage.toString())
                        .build();
            }

            courseRepository.deleteCoursesByTeacherIdAndCourseIds(teacherId, ids);
        }
    //删除指定老师id的全部课程
    @Transactional
    public void deleteAllCoursesByTeacherId(String id) {
        User u  = userRepository.findById(id).orElse(null);
        if(u == null) {
            throw XException.builder().number(Code.ERROR).message("老师不存在").build();
        }
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
    //基于老师id,课程id,获取已经选了多少学时
    @Transactional
    public int getHours(String teacherId, String courseId) {
        User u = userRepository.findById(teacherId).orElse(null);
        Course c = courseRepository.findById(courseId).orElse(null);
        if(u == null || c == null) {
            throw new XException().builder().number(Code.ERROR).message("老师或课程不存在").build();
        }
        int count = courseRepository.findCountByTeacherIdAndCourseId(teacherId, courseId);
        return count*2;
    }
    @Transactional
    //基于老师id,课程id，获取人数可用和不可用的实验室
    public Map<String, List<Lab>> getLabs(String teacherId, String courseId) {
        User u = userRepository.findById(teacherId).orElse(null);
        Course c = courseRepository.findById(courseId).orElse(null);
        if(u == null || c == null) {
            throw new XException().builder().number(Code.ERROR).message("老师或课程不存在").build();
        }
        Map<String, List<Lab>> map = new HashMap<>();
        List<Lab> labs1 = labRepository.findGoodLabs(teacherId,courseId);
        map.put("座位充足实验室",labs1);
        List<Lab> labs2 = labRepository.findBadLabs(teacherId,courseId);
        map.put("座位不够实验室",labs2);
        return map;
    }
    //基于实验室id，查预约表
    @Transactional
    public List<Appointment> getAppointment(String  semester, String labId) {
        Lab lab = labRepository.findById(labId).orElse(null);
        if(lab == null) {
            throw new XException().builder().number(Code.ERROR).message("实验室不存在").build();
        }
     return appointmentRepository.findAllByLabIdAndSemester(semester,labId);
    }
    //预约课程
    @Transactional
    public void appointCourse(Appointment appointment) {
        String c = appointment.getCourse();
        log.debug("{}",c);
        appointmentRepository.save(appointment);
    }

    //基于老师id,课程id移除对应的预约信息
    @Transactional
    public void deleteAppointment(Appointment appointment) {
        Appointment a = appointmentRepository.findById(appointment.getId()).orElse(null);
        if(a == null) {
            throw new XException().builder().number(Code.ERROR).message("预约记录不存在").build();
        }
        appointmentRepository.deleteById(appointment.getId());
    }
    //获取角色是老师的所有预约表加课程信息
    public List<Appointment1> getallteacherstable(String role) {
        if(!role.equals(User.Teacher)) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
        List<Appointment1> appointment1List = userRepository.getallteacherstable();
        return appointment1List;
    }
    //删除预约记录
    public  void deleteAppointment(String tid,String semester,List<String> courseIds) {
        User u = userRepository.findById(tid).orElse(null);
//        Course c = courseRepository.findById(cid).orElse(null);
        if(u == null) {
            throw new XException().builder().number(Code.ERROR).message("老师不存在").build();
        }
        for(String cid:courseIds) {
            if(cid == null) {
                throw new XException().builder().number(Code.ERROR).message("课程不存在").build();
            }
            appointmentRepository.deleteAllByTeacherAndCourse(tid,cid,semester);
        }

    }
    ////删除指定课程id,指定学期，指定周次的预约记录
    public void deleteAppointment1(String tid,String role,Appointment1 appointment1) {
        if(!role.equals(User.Teacher)) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
        User u = userRepository.findById(tid).orElse(null);
        Course c = courseRepository.findById(appointment1.getCourseId()).orElse(null);
        if(u == null || c == null) {
            throw new XException().builder().number(Code.ERROR).message("老师或课程不存在").build();
        }
        for(int i = 0;i<appointment1.getWeeks().length;i++) {
            appointmentRepository.deleteAllByTeacherAndCourseAndSemesterAndWeeks(
                    tid,appointment1.getCourseId(),
                    appointment1.getLabId(),
                    appointment1.getSemester(),
                    appointment1.getWeeks()[i]);
        }

    }

}
