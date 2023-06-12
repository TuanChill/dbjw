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
    @Column(name = "user_name")
    private String userName;

    private String password;

    private String role;

}