package com.ba.dbjw.Service.Customer;

import java.util.List;

public interface CustomerService<T> {
    boolean createCustomer(T data);

    boolean updateCustomer(T data);

    void delCustomer(T data);

    Long getNumberOfCustomer();

    List<T> getAllCustomer();

    T getCustomerByCode(String code);

    Boolean checkCustomerIsExist(String phoneNumber);

}
