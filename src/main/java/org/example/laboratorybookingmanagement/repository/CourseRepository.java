package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.Course;
import org.example.laboratorybookingmanagement.dox.Lab;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends ListCrudRepository<Course,String> {
    @Query("select * from course c where c.teacher_id =:id and c.semester = :semester")
    List<Course> findCoursesByTeacherIdAndSemester(String semester, String id);
    void deleteCoursesByTeacherId(String id);
    @Query("""
SELECT COUNT(*) AS record_count
FROM `appointment`
WHERE teacher ->> '$.id' = :teacherId
  AND course ->> '$.id' = :courseId
""")
    int findCountByTeacherIdAndCourseId(String teacherId,String courseId);
    @Modifying
    @Query("delete from course  where course.teacher_id=:teacherId and course.id=:courseId")
     void deleteCourseByTeacherIdAndCourseId(String teacherId,String courseId);
    @Modifying
    @Query("""
DELETE FROM course c
WHERE c.teacher_id = :teacherId
  AND c.id IN (:courseIds);
""")
     void deleteCoursesByTeacherIdAndCourseIds(String teacherId,List<String> courseIds);
}
