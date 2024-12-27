package org.example.laboratorybookingmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course1 {
    private String courseName;
    private String TeacherName;
    private String clazz;
    private String weeks;
    private int experimentHour;
    private String labName;

}
