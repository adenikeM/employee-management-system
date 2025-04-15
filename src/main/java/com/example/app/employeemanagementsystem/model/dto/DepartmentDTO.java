package com.example.app.employeemanagementsystem.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class DepartmentDTO {
    @NotEmpty
    @Length(max = 20)
    private String name;
}
