package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.User;
import org.example.laboratorybookingmanagement.dto.Appointment1;
import org.example.laboratorybookingmanagement.mapper.Appointment1ResultSetExtractor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends ListCrudRepository<User, String> {
    User findByAccount(String account);

    @Query(value = """
SELECT
    a.*,c.*
FROM
    appointment a
        JOIN
    course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
WHERE
    a.teacher ->> '$.id' = :teacherId
""" , resultSetExtractorClass = Appointment1ResultSetExtractor.class)
    List<Appointment1> findCourseByTeacherId(String teacherId);


    }