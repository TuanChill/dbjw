package com.ba.dbjw.Models;


import com.ba.dbjw.Models.Enums.Gender;
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
@Table(indexes = @Index(name = "idx_code", columnList = "code"))
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {
    @Column(unique = true)
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
