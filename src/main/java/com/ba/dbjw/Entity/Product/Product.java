package com.ba.dbjw.Entity.Product;

import com.ba.dbjw.Helpers.UUIDGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @Column(unique = true, nullable = false)
    private String code;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private Long price;
    private int stock;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String material;
    @Column(nullable = false)
    private String size;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;
    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @PrePersist
    private void generateCode() {
        if (getCode() == null) {
            setCode("KH" + UUIDGenerator.shortUUID());
        }
    }
}


