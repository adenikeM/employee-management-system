package com.example.app.employeemanagementsystem.repository;

import com.example.app.employeemanagementsystem.model.PayRoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRollRepository extends JpaRepository<PayRoll, Long> {
}
