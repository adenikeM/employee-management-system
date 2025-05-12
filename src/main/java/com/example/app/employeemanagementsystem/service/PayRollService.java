package com.example.app.employeemanagementsystem.service;

import com.example.app.employeemanagementsystem.model.Employee;
import com.example.app.employeemanagementsystem.model.PayRoll;
import com.example.app.employeemanagementsystem.model.dto.PayRollDTO;
import com.example.app.employeemanagementsystem.repository.EmployeeRepository;
import com.example.app.employeemanagementsystem.repository.PayRollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PayRollService {
    public final PayRollRepository payRollRepository;
    public final EmployeeRepository employeeRepository;

    public PayRollService(PayRollRepository payRollRepository, EmployeeRepository employeeRepository) {
        this.payRollRepository = payRollRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<PayRoll> getAllPayRoll(){return payRollRepository.findAll();}

    public Optional<PayRoll> getPayRollById(Long id){return payRollRepository.findById(id);}

    public PayRoll createPayRoll(Long employeeId, BigDecimal salary, LocalDate payDay){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        PayRoll payRoll = new PayRoll();
        payRoll.setEmployee(employee);
        payRoll.setPayDay(payDay);
        payRoll.setSalary(salary);

        return payRollRepository.save(payRoll);
    }

    public PayRoll updatePayRoll(Long id, BigDecimal newSalary){
        PayRoll existingPayRoll = payRollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PayRoll not found"));

        existingPayRoll.setSalary(newSalary);
        return payRollRepository.save(existingPayRoll);
    }

    public void deletePayRollById(Long id){
        PayRoll payRoll = payRollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PayRoll not found!"));
        payRollRepository.delete(payRoll);
    }

    private PayRollDTO mapToDTO(PayRoll payRoll) {
        return new PayRollDTO(
                payRoll.getId(),
                payRoll.getEmployee().getId(),
                payRoll.getEmployee().getFirstName() + " " + payRoll.getEmployee().getLastName(),
                payRoll.getSalary(),
                payRoll.getPayDay()
        );
    }
}
