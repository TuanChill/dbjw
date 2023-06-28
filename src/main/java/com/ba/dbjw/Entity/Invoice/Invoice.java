package com.ba.dbjw.Entity.Invoice;

import com.ba.dbjw.Entity.Customer.Customer;
import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Helpers.UUIDGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    private String code;

    private String note;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_code")
    private List<InvoiceItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_code")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_code")
    private Customer customer;

    @Column(name = "total_money")
    private BigDecimal totalMoney;

    public void addItem(Product product, int quantity) {
        InvoiceItem item = new InvoiceItem(product, quantity);
        items.add(item);
    }

    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (InvoiceItem item : items) {
            BigDecimal price = BigDecimal.valueOf(item.getProduct().getPrice());
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            BigDecimal itemAmount = price.multiply(quantity);
            totalAmount = totalAmount.add(itemAmount);
        }
        return totalAmount;
    }

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @PrePersist
    private void generateCode() {
        if (getCode() == null) {
            setCode("HD" + UUIDGenerator.shortUUID());
        }

        setTotalMoney(getTotalAmount());
    }
}
