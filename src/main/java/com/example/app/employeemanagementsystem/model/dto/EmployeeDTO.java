package com.example.app.employeemanagementsystem.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDTO {

    @NotEmpty
    private String firstName;

    private String lastName;

    private String email;

    private BigDecimal salary;

    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;

    private final Map<String, String> settings = new HashMap<>();

    public String getFirstName() {
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(@NotNull(message = "Department ID cannot be null") Long departmentId) {
        this.departmentId = departmentId;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSetting(String key, String value) {
        this.settings.put(key, value);
    }
}
