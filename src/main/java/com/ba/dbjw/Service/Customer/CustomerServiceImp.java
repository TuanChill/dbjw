package com.ba.dbjw.Service.Customer;

import com.ba.dbjw.Entity.Customer.Customer;
import com.ba.dbjw.Repo.Customer.CustomerRepoImpl;

import java.util.List;

public class CustomerServiceImp implements CustomerService<Customer> {
    CustomerRepoImpl customerRepo = new CustomerRepoImpl();
    @Override
    public void createCustomer(Customer data) {
        if(validateInput(data)) {
            customerRepo.saveCustomer(data);
        }
    }

    @Override
    public void updateCustomer(Customer data) {
        if(validateInput(data)) {
            customerRepo.updateCustomer(data);
        }
    }

    @Override
    public void delCustomer(Customer data) {
        customerRepo.delCustomer(data);
    }

    @Override
    public Long getNumberOfCustomer() {
        return customerRepo.getNumberOfCustomer();
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepo.getAllCustomers();
    }

    private boolean validateInput(Customer customer) {
        return !customer.code.isEmpty() &&
                !customer.cccd.isEmpty() &&
                !customer.phoneNumber.isEmpty() &&
                customer.birthday == null &&
                !customer.address.isEmpty() &&
                customer.gender != null;
    }
}
