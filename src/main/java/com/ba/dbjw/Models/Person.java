package com.ba.dbjw.Models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Person {
    @Id
    @Column(length = 8)
    private String code;
    @Column(nullable = false, length = 40)
    private String name;
    private LocalDate birthday;
    private String address;
    @Column(length = 10)
    private String gender;
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;
    @Column(length = 50)
    private String email;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;
    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;
}
