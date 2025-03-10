package com.example.app.employeemanagementsystem.controller;

import com.example.app.employeemanagementsystem.model.Employee;
import com.example.app.employeemanagementsystem.model.dto.EmployeeDTO;
import com.example.app.employeemanagementsystem.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        return ResponseEntity.ok().body(employeeService.getAllEmployee());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
       /* log.info("Get Employee id by {}", id);*/
        if(id < 1){
            throw new IllegalArgumentException("Employee ID cannot be less than 1");
        }
        return employeeService.getEmployeeById(id)
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee){
       /* log.info("Request to create employee => {}", employee);*/
        if(employee.getId() != null){
            return ResponseEntity.badRequest().body("Invalid employee id, ID should be null");
        }
        return ResponseEntity.ok().body(employeeService.createEmployee(employee));
    }

    @PostMapping("/v2/employees")
    public ResponseEntity<?> createEmployeeV2(@RequestBody EmployeeDTO createEmployeeDTO){
        /*log.info("Request to create employee v2 => {}", createEmployeeDTO);*/
        return ResponseEntity.ok().body(employeeService.createEmployeeV2(createEmployeeDTO));
    }
    @PutMapping("/employees")
    public ResponseEntity<?>updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        if(employee.getId() == null){
            return ResponseEntity.badRequest().body("Employee ID cannot be null, provide the employee ID number");
        }
        return ResponseEntity.ok().body(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        /*log.info("Request to delete employee with ID: {}", id);*/
        if (id < 1) {
            return ResponseEntity.badRequest().body("Invalid employee ID");
        }
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees/salary")
    public ResponseEntity<List<Employee>> getEmployeesBySalaryRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok().body(employeeService.getEmployeesBySalaryRange(min, max));
    }

    @GetMapping("/employees/search")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
       /* log.info("Searching employees with name: {}", name);*/
        return ResponseEntity.ok().body(employeeService.searchEmployeesByName(name));
    }

}
