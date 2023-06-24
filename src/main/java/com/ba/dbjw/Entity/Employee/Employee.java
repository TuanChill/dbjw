package com.ba.dbjw.Entity.Employee;

import com.ba.dbjw.Helpers.UUIDGenerator;
import com.ba.dbjw.Models.Person;

import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "employee")

public class Employee extends Person {
    @Column(unique = true, length = 12)
    private String cccd;

    @Column(length = 20)
    private String position;

    private String avatar;

    public Employee() {
        super();
    }

    @PrePersist
    private void generateCode() {
        if (getCode() == null) {
            setCode("NV" + UUIDGenerator.shortUUID());
        }
    }

}
