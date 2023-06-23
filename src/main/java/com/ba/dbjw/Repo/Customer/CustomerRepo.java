package com.ba.dbjw.Repo.Customer;

import java.util.List;

public interface CustomerRepo<T> {
    void saveCustomer(T data);
    T getCustomerByCode(String code);
    List<T> getCustomerByName(String name);

    T getCustomerByPhoneNumber(String phoneNumber);
    List<T> getAllCustomers();

    void updateCustomer(T data);
    void delCustomer(T data);

    Long getNumberOfCustomer();
}
