package com.ba.dbjw.Repo.Employee;

import java.util.List;

public interface EmployeeRepo<T> {
    void saveEmployee(T data);
    T getEmployeeByCode(String code);
    List<T> getEmployeeByName(String name);

    T getEmployeeByCccd(String cccd);

    T getEmployeeByPhoneNumber(String phoneNumber);
    List<T> getAllEmployees();

    void updateEmployee(T data);
    void delEmployee(T data);

    Long getNumberOfEmployee();
}
