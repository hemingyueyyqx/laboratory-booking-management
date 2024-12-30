package org.example.laboratorybookingmanagement.mapper;

import lombok.extern.slf4j.Slf4j;
//import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.laboratorybookingmanagement.dox.Appointment;
import org.example.laboratorybookingmanagement.dox.Course;
import org.example.laboratorybookingmanagement.dto.Appointment1;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Appointment1ResultSetExtractor implements ResultSetExtractor<List<Appointment1
        >> {
    //private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public List<Appointment1> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Appointment1> appointment1s = new ArrayList<>();
        while (rs.next()) {
           /*  读取 weeks 列的 JSON 字符串
            String weeksJson = rs.getString("weeks");
             将 JSON 字符串转换为 List<Integer>
            List<Integer> weeks = null;
            try {
                weeks = objectMapper.readValue(weeksJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Integer.class));
            } catch (Exception e) {
                log.error("Error parsing weeks JSON: " + weeksJson, e);
            }*/
            Course c = Course.builder()
                    .id(rs.getString("c.id"))
                    .name(rs.getString("name"))
                    .teacherId(rs.getString("teacher_id"))
                    .clazz(rs.getString("clazz"))
                    .experimentHour(rs.getInt("experiment_hour"))
                    .quantity(rs.getInt("quantity"))
                    .type(rs.getInt("type"))
                    .semester(rs.getString("c.semester"))
                    .build();
            Appointment appointment = Appointment.builder()
                    .id(rs.getString("a.id"))
                    .teacher(rs.getString("teacher"))
                    .course(rs.getString("course"))
                    .labId(rs.getString("lab_id"))
                    .labName(rs.getString("lab_name"))
                    .semester(rs.getString("a.semester"))
                    .nature(rs.getString("nature"))
                    .week(rs.getInt("week"))
                    .dayofweek(rs.getInt("dayofweek"))
                    .section(rs.getInt("section"))
                    .build();
            Appointment1 appointment1 = new Appointment1().builder()
                    .appointment(appointment)
                    .course(c)
                    .build();
            appointment1s.add(appointment1);
        }
        return appointment1s;
    }
}
