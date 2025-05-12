package com.example.app.employeemanagementsystem.controller;

import com.example.app.employeemanagementsystem.model.PayRoll;
import com.example.app.employeemanagementsystem.model.dto.ErrorResponse;
import com.example.app.employeemanagementsystem.model.dto.PayRollDTO;
import com.example.app.employeemanagementsystem.service.PayRollService;
import com.example.app.employeemanagementsystem.utils.PayRollMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("api")
public class PayRollController {
    public final PayRollService payRollService;

    public PayRollController(PayRollService payRollService) {
        this.payRollService = payRollService;
    }

    @GetMapping("/payroll")
    public ResponseEntity<List<PayRollDTO>> getAllPayRoll(){
        List<PayRollDTO> payrolls = payRollService.getAllPayRoll()
                                                  .stream()
                                                  .map(PayRollMapper::toDTO)
                                                  .collect(Collectors.toList());
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/payroll/{id}")
    public ResponseEntity<?> getPayRollById(@PathVariable Long id){
        log.info("Get PayRoll id by {}", id);
        if(id < 1){
            return ResponseEntity.badRequest().body(
                    ErrorResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "ID should be null",
                            "Invalid ID"));

        }
        PayRoll payroll = payRollService.getPayRollById(id)
                                        .orElseThrow(() -> new RuntimeException("Payroll not found"));

        return ResponseEntity.ok(PayRollMapper.toDTO(payroll));

    }

    @PostMapping("/payroll")
    public ResponseEntity<?> createPayRoll(@RequestParam Long employeeId,
                                           @RequestParam BigDecimal salary,
                                           @RequestParam String payDay) {
        try {
            LocalDate parsedPayDay = LocalDate.parse(payDay);
            PayRoll payroll = payRollService.createPayRoll(employeeId, salary, parsedPayDay);
            return ResponseEntity.status(HttpStatus.CREATED).body(PayRollMapper.toDTO(payroll));
        } catch (Exception e) {
            log.error("Error creating payroll", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ErrorResponse.buildErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error creating payroll",
                            e.getMessage()
                    )
            );
        }
    }

    @PutMapping("/payroll/{id}")
    public ResponseEntity<?> updatePayRoll(@PathVariable Long id, @RequestParam BigDecimal salary) {
        try {
            PayRoll updated = payRollService.updatePayRoll(id, salary);
            return ResponseEntity.ok(PayRollMapper.toDTO(updated));
        } catch (Exception e) {
            log.error("Error updating payroll", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ErrorResponse.buildErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error updating payroll",
                            e.getMessage()
                    )
            );
        }
    }

    @DeleteMapping("/payroll/{id}")
    public ResponseEntity<?> deletePayRoll(@PathVariable Long id) {
        try {
            payRollService.deletePayRollById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting payroll", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ErrorResponse.buildErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error deleting payroll",
                            e.getMessage()
                    )
            );
        }
    }
}
