package com.example.app.employeemanagementsystem.service;

import com.example.app.employeemanagementsystem.model.Department;
import com.example.app.employeemanagementsystem.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartment(){
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id){
        return departmentRepository.findById(id);
    }

    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }


    public Optional <Department> updateDepartment( Department department){
        Department existingDepartment = departmentRepository.findById(department.getId())
                .orElseThrow(() -> new RuntimeException("Department not found with id " + department.getId()));
        existingDepartment.setName(existingDepartment.getName());
//        if (department.getEmployees() != null && !department.getEmployees().isEmpty()) {
//            existingDepartment.setEmployees(department.getEmployees());
//        }
        return Optional.of(departmentRepository.save(existingDepartment));

    }

    public void deleteDepartmentById(Long id){
         departmentRepository.deleteById(id);
    }
}
