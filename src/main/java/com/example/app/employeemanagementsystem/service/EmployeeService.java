package com.example.app.employeemanagementsystem.service;

import com.example.app.employeemanagementsystem.model.Department;
import com.example.app.employeemanagementsystem.model.Employee;
import com.example.app.employeemanagementsystem.model.dto.EmployeeDTO;
import com.example.app.employeemanagementsystem.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.app.employeemanagementsystem.utils.ObjectMapper.mapCreateEmployeeDTOToEmployee;

@Service
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

   /* public Optional<Employee> updateEmployee(Long id, Employee employee) {
        return employeeRepository.findById(id)
                                 .map(existingEmployee -> {
                                     existingEmployee.setFirstName(employee.getFirstName());
                                     existingEmployee.setLastName(employee.getLastName());
                                     existingEmployee.setEmail(employee.getEmail());
                                     existingEmployee.setDepartment(employee.getDepartment());
                                     existingEmployee.setSalary(employee.getSalary());
                                     return employeeRepository.save(existingEmployee);
                                 });
    }*/

    public Employee createEmployeeV2(EmployeeDTO createEmployeeDTO){
        Department department = departmentService.getDepartmentById(createEmployeeDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Department id " + createEmployeeDTO.getDepartmentId()));
        Employee employee = mapCreateEmployeeDTOToEmployee(createEmployeeDTO, department);
        for(Map.Entry<String, String> entry : createEmployeeDTO.getSettings().entrySet()){
            employee.addSetting(entry.getKey(), entry.getValue());
        }
        return employeeRepository.save(employee);
    }

   public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id)
                                                     .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());
       return employeeRepository.save(existingEmployee);
    }
    public void deleteEmployeeById(Long id){
        employeeRepository.deleteById(id);
    }

    /*public Employee updateEmployeeSalary(Long id, BigDecimal newSalary) {
        if (newSalary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        Employee employee = getEmployeeById(id);
        employee.setSalary(newSalary);
        return employeeRepository.save(employee);
    }*/


    public List<Employee> getEmployeesBySalaryRange(BigDecimal min, BigDecimal max) {
        return employeeRepository.findBySalaryBetween(min, max);
    }

    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
}
