package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.Appointment;
import org.example.laboratorybookingmanagement.dto.LabCountByDayofweekDTO;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends ListCrudRepository<Appointment,String> {
    @Query("select * from appointment a where a.lab_id=:labId and a.semester=:semester")
    List<Appointment> findAllByLabIdAndSemester(String semester,String labId);
//    @Modifying
//    @Query("""
//delete from appointment a where a.teacher ->> '$.id'=:teacherId and a.course ->> '$.id'=:courseId
//""")
//    void deleteByTeacherIdAndCourseId(String teacherId, String courseId);
@Query("""
    select dayofweek,count(distinct lab_id) as quantity from appointment where week=:week group by dayofweek;
""")
List<LabCountByDayofweekDTO> countLabByDayofweek(int week);
@Modifying
@Query("delete from appointment a where a.teacher ->> '$.id'=:tid and a.course ->> '$.id' = :cid and a.semester=:semester")
void deleteAllByTeacherAndCourse(String tid, String cid, String semester);
@Modifying
    @Query("delete from appointment a where a.teacher ->> '$.id'=:tid and a.course ->> '$.id' = :cid and a.semester=:semester and a.week=:week and a.lab_id=:labId")
    void deleteAllByTeacherAndCourseAndSemesterAndWeeks(String tid,String cid,String labId,String semester,int week);
}
