package com.ba.dbjw.Helpers;


import com.ba.dbjw.Entity.Customer.Customer;

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

