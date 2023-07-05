package com.ba.dbjw.Service.Empoyee;

import java.util.List;

public interface EmployeeService<T> {
    boolean createEmployee(T data);
    List<T> getAllEmployees();
    boolean deleteEmployee(T data);
    boolean updateEmployee(T data);
    Long getNumberOfEmployee();

    T getEmployeeByCode(String code);

    Boolean checkEmployeeExist(String cccd);
}
