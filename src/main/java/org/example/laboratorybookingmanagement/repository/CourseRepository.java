package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.Course;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends ListCrudRepository<Course,String> {

    List<Course> findCoursesByTeacherId(String id);
    void deleteCoursesByTeacherId(String id);
}
