package com.ba.dbjw.Entity.Product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    private String code;
    @Column(unique = true)
    private String name;
    private String description;
    private Long price;
    private int stock;
    private int sold;
    @Column(name = "img_url")
    private String imgUrl;
    private String category;
    private String material;
    private String size;
}


