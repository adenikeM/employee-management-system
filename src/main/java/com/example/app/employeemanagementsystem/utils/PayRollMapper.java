package com.example.app.employeemanagementsystem.utils;

import com.example.app.employeemanagementsystem.model.PayRoll;
import com.example.app.employeemanagementsystem.model.dto.PayRollDTO;

public class PayRollMapper {

    public static PayRollDTO toDTO(PayRoll payRoll) {
        return new PayRollDTO(
                payRoll.getId(),
                payRoll.getEmployee().getId(),
                payRoll.getEmployee().getFirstName() + " " + payRoll.getEmployee().getLastName(),
                payRoll.getSalary(),
                payRoll.getPayDay()
        );
    }
}