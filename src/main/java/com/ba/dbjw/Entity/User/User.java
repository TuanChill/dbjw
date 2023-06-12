package com.ba.dbjw.Entity.User;
import com.ba.dbjw.Models.Enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "users")
public class User  {
    @Id
    @Column(name = "user_name")
    private String userName;
    @Column(unique = true)
    private String email;

    private Date birthday;

    private Gender gender;

    @Column(unique = true)
    private String cccd;

    private Boolean status;
    private String birthplace;
    private String address;
    private String phone;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;
}
