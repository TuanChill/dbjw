package com.ba.dbjw.Service.Customer;

import java.util.List;

public interface CustomerService<T> {
    void createCustomer(T data);

    void updateCustomer(T data);

    void delCustomer(T data);

    Long getNumberOfCustomer();

    List<T> getAllCustomer();

    Boolean checkCustomerIsExist(String phoneNumber);

}
