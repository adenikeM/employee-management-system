package com.example.app.employeemanagementsystem.service;

import com.example.app.employeemanagementsystem.model.Employee;
import com.example.app.employeemanagementsystem.model.LeaveRequest;
import com.example.app.employeemanagementsystem.model.LeaveStatus;
import com.example.app.employeemanagementsystem.repository.EmployeeRepository;
import com.example.app.employeemanagementsystem.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LeaveRequestService {
    public final LeaveRequestRepository leaveRequestRepository;
    public final EmployeeRepository employeeRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<LeaveRequest> getAllLeaveRequest(){return leaveRequestRepository.findAll();}

    public Optional<LeaveRequest> getLeaveRequestById(Long id){return leaveRequestRepository.findById(id);}

    public LeaveRequest createLeaveRequest(Long employerId, LocalDate startDate, LocalDate endDate, LeaveStatus status){
        Employee employee = employeeRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employerId));
        LeaveRequest request = new LeaveRequest();
        request.setEmployee(employee);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setStatus(status);
        return leaveRequestRepository.save(request);
    }

    public LeaveRequest updateLeaveRequest(Long id, LeaveRequest request){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave Request not found with id " + id));
        leaveRequest.setStatus(request.getStatus());
        return leaveRequestRepository.save(leaveRequest);
    }

    public void deleteLeaveRequestById(Long id){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                                                          .orElseThrow(() -> new RuntimeException("Leave Request not found"));

        leaveRequestRepository.delete(leaveRequest);
    }

    public List<LeaveRequest> getLeaveRequestsByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                                              .orElseThrow(() -> new RuntimeException("Employee not found"));

        return leaveRequestRepository.findByEmployee(employee);
    }
}

