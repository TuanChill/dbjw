package com.ba.dbjw.Models;


import com.ba.dbjw.Models.Enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Setter
@Getter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {
    @Column(unique = true)
    public String code;
    @Column(nullable = false, length = 40)
    public String name;
    public LocalDate birthday;
    public String address;
    @Column(length = 10)
    public Gender gender;
    @Column(name = "phone_number", length = 10)
    public String phoneNumber;
    @Column(length = 50)
    public String email;
    @Column(unique = true, length = 12)
    public String cccd;

    @Column(name = "create_at")
    @CreationTimestamp
    public LocalDateTime createAt;
    @Column(name = "update_at")
    @UpdateTimestamp
    public LocalDateTime updateAt;
}
