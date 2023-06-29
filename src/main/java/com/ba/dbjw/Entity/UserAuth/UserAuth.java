package com.ba.dbjw.Entity.UserAuth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_auth")
public class UserAuth {
    @Id
    @Column(name = "user_name", length = 40)
    private String userName;

    private String password;

    @Column(length = 10)
    private String role;

}