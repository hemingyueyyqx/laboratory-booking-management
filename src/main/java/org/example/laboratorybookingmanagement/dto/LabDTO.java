package org.example.laboratorybookingmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabDTO {
    private String id;
    private String name;
    private int state;
    private int quantity;
    private String description;
    private String manager;
}