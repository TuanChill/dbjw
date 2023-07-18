package com.ba.dbjw.Repo.Employee;

import java.util.List;

public interface EmployeeRepo<T> {
    boolean saveEmployee(T data);
    T getEmployeeByCode(String code);
    List<T> getEmployeeByName(String name);

    T getEmployeeByCccd(String cccd);

    T getEmployeeByPhoneNumber(String phoneNumber);
    List<T> getAllEmployees();

    boolean updateEmployee(T data);
    boolean delEmployee(T data);

    Long getNumberOfEmployee();
}
