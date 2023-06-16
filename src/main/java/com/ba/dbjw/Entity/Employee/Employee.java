package com.ba.dbjw.Entity.Employee;

import com.ba.dbjw.Models.Person;

import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")

public class Employee extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String position;
}
