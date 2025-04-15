package com.example.app.employeemanagementsystem.controller;

import com.example.app.employeemanagementsystem.model.LeaveRequest;
import com.example.app.employeemanagementsystem.model.dto.ErrorResponse;
import com.example.app.employeemanagementsystem.model.dto.LeaveRequestDTO;
import com.example.app.employeemanagementsystem.model.dto.LeaveStatusDTO;
import com.example.app.employeemanagementsystem.service.LeaveRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("api")
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/requests")
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequest(){
        return ResponseEntity.ok().body(leaveRequestService.getAllLeaveRequest());
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<?>getLeaveRequestById(@PathVariable Long id){
        log.info("Get LeaveRequest by ID {} ", id);
        if(id < 1){
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID cannot be less than 1",
                            "Invalid ID")
            );
        }
        return leaveRequestService.getLeaveRequestById(id)
                .map(leaveRequest -> ResponseEntity.ok().body(leaveRequest))
                .orElseThrow(() -> new RuntimeException("LeaveRequest not found"));
    }

    @GetMapping("/requests/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getRequestsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestsByEmployee(employeeId));
    }

    @PostMapping("/request")
    public ResponseEntity<?> createLeaveRequest(@RequestBody LeaveRequestDTO createLeaveRequestDTO){
        log.info("Request tp create leaveRequest => {}", createLeaveRequestDTO);
        LeaveRequest newRequest = leaveRequestService.createLeaveRequest(
                createLeaveRequestDTO.getEmployeeId(),
                createLeaveRequestDTO.getStartDate(),
                createLeaveRequestDTO.getEndDate(),
                createLeaveRequestDTO.getStatus()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newRequest);
    }
    @PutMapping("/request/{id}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequest request) {
        LeaveRequest updated = leaveRequestService.updateLeaveRequest(id, request);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/request/{id}/status")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> updateLeaveStatus(@PathVariable Long id, @RequestBody LeaveStatusDTO updateLeaveStatusDTO) {

        LeaveRequest leaveRequest = leaveRequestService.getLeaveRequestById(id)
                                                       .orElseThrow(() -> new RuntimeException("Leave Request not found with id " + id));

        leaveRequest.setStatus(updateLeaveStatusDTO.getStatus());
        LeaveRequest updated = leaveRequestService.updateLeaveRequest(id, leaveRequest);

        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/request/{id}")
    public ResponseEntity<?> deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequestById(id);
        return ResponseEntity.noContent().build();
    }
}
