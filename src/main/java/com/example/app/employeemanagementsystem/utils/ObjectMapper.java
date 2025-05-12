package com.example.app.employeemanagementsystem.utils;

import com.example.app.employeemanagementsystem.model.Department;
import com.example.app.employeemanagementsystem.model.Employee;
import com.example.app.employeemanagementsystem.model.PayRoll;
import com.example.app.employeemanagementsystem.model.dto.EmployeeDTO;
import com.example.app.employeemanagementsystem.model.dto.PayRollDTO;

public class ObjectMapper {
    public static Employee mapCreateEmployeeDTOToEmployee(EmployeeDTO createEmployeeDTO, Department department) {
        Employee employee = new Employee();
        employee.setFirstName(createEmployeeDTO.getFirstName());
        employee.setLastName(createEmployeeDTO.getLastName());
        employee.setEmail(createEmployeeDTO.getEmail());
        employee.setSalary(createEmployeeDTO.getSalary());
        employee.setDepartment(department);
        return employee;
    }
}






