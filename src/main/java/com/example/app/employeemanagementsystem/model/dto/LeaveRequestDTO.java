package com.example.app.employeemanagementsystem.model.dto;

import com.example.app.employeemanagementsystem.model.LeaveStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDTO {
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;
}
