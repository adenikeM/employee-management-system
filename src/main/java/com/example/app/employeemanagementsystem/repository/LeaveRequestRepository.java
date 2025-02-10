package com.example.app.employeemanagementsystem.repository;

import com.example.app.employeemanagementsystem.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
}
