package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.Appointment;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends ListCrudRepository<Appointment,String> {
    List<Appointment> findAllByLabId(String labId);
//    @Modifying
//    @Query("""
//delete from appointment a where a.teacher ->> '$.id'=:teacherId and a.course ->> '$.id'=:courseId
//""")
//    void deleteByTeacherIdAndCourseId(String teacherId, String courseId);
}
