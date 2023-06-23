package com.ba.dbjw.Entity.Customer;

import com.ba.dbjw.Helpers.UUIDGenerator;
import com.ba.dbjw.Models.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "customer")

public class Customer extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Customer() {
        super();
    }

    public Customer(String code, String name, String address, LocalDate birthdate, String gender, String phoneNumber, String email) {
        this.setCode(code);
        this.setName(name);
        this.setAddress(address);
        this.setBirthday(birthdate);
        this.setGender(gender);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
    }

    @PrePersist
    private void generateCode() {
            setCode("KH" + UUIDGenerator.shortUUID());
    }
}
