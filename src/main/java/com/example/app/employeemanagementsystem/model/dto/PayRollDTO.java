package com.example.app.employeemanagementsystem.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PayRollDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private BigDecimal salary;
    private LocalDate payDay;
}
