package com.ba.dbjw.Helpers.CurrentEntity;

import com.ba.dbjw.Entity.Employee.Employee;


public class CurrentEmployee {
    private static Employee employee;

    private CurrentEmployee() {
    }

    public static Employee getCurrentEmployee() {
        return employee;
    }

    public static void setCurrentEmployee(Employee currentEmployee) {
        employee = currentEmployee;
    }
}
