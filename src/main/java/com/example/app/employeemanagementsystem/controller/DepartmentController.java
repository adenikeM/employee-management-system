package com.example.app.employeemanagementsystem.controller;

import com.example.app.employeemanagementsystem.model.Department;
import com.example.app.employeemanagementsystem.model.dto.DepartmentDTO;
import com.example.app.employeemanagementsystem.model.dto.ErrorResponse;
import com.example.app.employeemanagementsystem.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("api")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department")
    public ResponseEntity<List<Department>> getAllDepartment(){
        return ResponseEntity.ok().body(departmentService.getAllDepartment());
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id){
        log.info("Get Department id by {}", id);
        if (id < 1){
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "Department id cannot be less than 1",
                            "Invalid ID")
            );
        }
        return departmentService.getDepartmentById(id)
                .map(department -> ResponseEntity.ok().body(department))
//                .orElseGet(() -> ResponseEntity.notFound().build());
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @PostMapping("/department")
    public ResponseEntity<?>createDepartment(@RequestBody Department department){
        log.info("Request to create department => {}", department);
        if(department.getId()!= null){
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID should be null",
                            "Invalid ID"));
        }
        return ResponseEntity.ok().body(departmentService.createDepartment(department));
    }

    @PutMapping("/department")
    public ResponseEntity<?>updateDepartment(@RequestBody Department department){
        if(department.getId() == null){
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID cannot be null",
                            "Id must be provided for update")
            );
        }
        Optional<Department> updatedLocation = departmentService.updateDepartment(department);
        if (updatedLocation.isPresent()) {
            return ResponseEntity.ok(updatedLocation);
        } else {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "Department not found",
                            "Department with the given ID doesn't exist"
                           ));
        }
    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id){
        log.info("Request to delete department with ID: {}", id  );
        if (id < 1) {
            return ResponseEntity.badRequest().body("Invalid employee ID");
        }
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.noContent().build();
    }
}
