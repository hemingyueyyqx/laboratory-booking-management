package org.example.laboratorybookingmanagement.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lab {
    @Id
    @CreatedBy
    private String id;
    private String name;
    private short state;
    private short quantity;
    private String description;
    private String manager;
}
