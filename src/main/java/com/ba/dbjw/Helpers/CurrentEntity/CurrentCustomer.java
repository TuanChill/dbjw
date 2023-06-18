package com.ba.dbjw.Helpers.CurrentEntity;

import com.ba.dbjw.Entity.Customer.Customer;
import com.ba.dbjw.Entity.UserAuth.UserAuth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CurrentCustomer {
    private static Customer customer;
    private CurrentCustomer() {
    }

    public static Customer getCurrentCustomer() {
        return customer;
    }

    public static void setCurrentCustomer(Customer currentCustomer) {
        customer = currentCustomer;
    }
}
