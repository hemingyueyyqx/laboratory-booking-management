package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dto.Course1;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends ListCrudRepository<Course1,String> {


}
