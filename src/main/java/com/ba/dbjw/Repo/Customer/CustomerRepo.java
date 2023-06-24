package com.ba.dbjw.Repo.Customer;

import java.util.List;

public interface CustomerRepo<T> {
    boolean saveCustomer(T data);
    T getCustomerByCode(String code);
    List<T> getCustomerByName(String name);

    T getCustomerByPhoneNumber(String phoneNumber);
    List<T> getAllCustomers();

    boolean updateCustomer(T data);
    void delCustomer(T data);

    Long getNumberOfCustomer();
}
