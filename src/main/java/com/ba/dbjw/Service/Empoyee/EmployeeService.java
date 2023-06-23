package com.ba.dbjw.Service.Empoyee;

import java.util.List;

public interface EmployeeService<T> {
    void createEmployee(T data);
    List<T> getAllEmployees();
    void deleteEmployee(T data);
    void updateEmployee(T data);
    Long getNumberOfEmployee();

    Boolean checkEmployeeExist(String cccd);
}
