package com.ba.dbjw.Entity.Invoice;

import com.ba.dbjw.Entity.Product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoice_item")
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_code")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code")
    private Product product;

    private int quantity;

    public InvoiceItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public String getCode() {
        return product.getCode();
    }

    public String getName() {
        return product.getName();
    }
}

// fetch = FetchType.LAZY :   data will be load just when query
