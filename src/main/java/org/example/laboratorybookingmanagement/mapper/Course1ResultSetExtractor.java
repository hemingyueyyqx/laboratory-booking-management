package org.example.laboratorybookingmanagement.mapper;

import lombok.extern.slf4j.Slf4j;

import org.example.laboratorybookingmanagement.dto.Course1;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Course1ResultSetExtractor implements ResultSetExtractor<List<Course1
        >> {
    @Override
    public List<Course1> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Course1> course1s = new ArrayList<>();
        while (rs.next()) {

            Course1 c = Course1.builder()
                    .dayofweek(rs.getInt("dayofweek"))
                    .section(rs.getInt("section"))
                    .teacherName(rs.getString("teacherName"))
                    .courseName(rs.getString("courseName"))
                    .clazz(rs.getString("clazz"))
                    .weeks(rs.getString("weeks"))
                    .experimentHour(rs.getInt("experiment_hour"))
                    .labName(rs.getString("lab_name"))
                    .build();
            course1s.add(c);
        }
        return course1s;
    }
}
