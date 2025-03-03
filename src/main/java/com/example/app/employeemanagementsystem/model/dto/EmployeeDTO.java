package com.example.app.employeemanagementsystem.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class EmployeeDTO {

    @NotEmpty
    private String firstName;

    private String lastName;

    private String email;

    private BigDecimal salary;

    @NotNull(message = "department ID cannot be null")
    private Long departmentId;

    public @NotEmpty String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotEmpty String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public @NotNull(message = "department ID cannot be null") Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(@NotNull(message = "department ID cannot be null") Long departmentId) {
        this.departmentId = departmentId;
    }

    private final Map<String, String> settings = new HashMap<>();

}
