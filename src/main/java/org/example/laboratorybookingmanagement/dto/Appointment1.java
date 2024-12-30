package org.example.laboratorybookingmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.laboratorybookingmanagement.dox.Appointment;
import org.example.laboratorybookingmanagement.dox.Course;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment1{
    private Appointment appointment;
    private Course course;
}
