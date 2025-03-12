package com.example.app.employeemanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


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


    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "settings_key")
    @Column(name = "settings_value")
    @CollectionTable(name = "employee_settings", joinColumns = @JoinColumn(name = "employee_id"))
    private final Map<String, String> settings = new HashMap<>();

    public void addSetting(String key, String value) {
        this.settings.put(key, value);
    }

}

