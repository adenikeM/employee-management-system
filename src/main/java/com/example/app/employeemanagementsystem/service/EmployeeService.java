package com.example.app.employeemanagementsystem.service;

import com.example.app.employeemanagementsystem.model.Employee;
import com.example.app.employeemanagementsystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> updateEmployee(Long id, Employee employee) {
        return employeeRepository.findById(id)
                                 .map(existingEmployee -> {
                                     existingEmployee.setFirstName(employee.getFirstName());
                                     existingEmployee.setLastName(employee.getLastName());
                                     existingEmployee.setEmail(employee.getEmail());
                                     existingEmployee.setDepartment(employee.getDepartment());
                                     existingEmployee.setSalary(employee.getSalary());
                                     return employeeRepository.save(existingEmployee);
                                 });
    }

    /*public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id)
                                                      .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());

        return employeeRepository.save(existingEmployee);
    }
*/
    public void deleteEmployeeById(Long id){
        employeeRepository.deleteById(id);
    }
}
