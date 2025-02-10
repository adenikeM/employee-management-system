package com.example.app.employeemanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "First name cannot be null")
    @Column(length = 20, nullable = false)
    private String firstName;

    @NotEmpty(message = "Last name cannot be null")
    @Column(length = 20, nullable = false)
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    private BigDecimal salary;

}
