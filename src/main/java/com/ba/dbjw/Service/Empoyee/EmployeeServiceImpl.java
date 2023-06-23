package com.ba.dbjw.Service.Empoyee;


import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Entity.Product.Product;
import com.ba.dbjw.Repo.Employee.EmployeeRepo;
import com.ba.dbjw.Repo.Employee.EmployeeRepoImpl;
import com.ba.dbjw.Repo.Product.ProductRepo;
import com.ba.dbjw.Repo.Product.ProductRepoImpl;
import com.ba.dbjw.Utils.CloudinaryUtil;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService<Employee> {
    private final EmployeeRepo<Employee> employeeRepo = new EmployeeRepoImpl();

    @Override
    public void createEmployee(Employee employee) {
        String imgPath = employee.getAvatar();
        if(imgPath != null) {
            String url = CloudinaryUtil.uploadImgToCloudinary(imgPath);
            employee.setAvatar(url);
        }
            employeeRepo.saveEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.getAllEmployees();
    }

    @Override
    public void deleteEmployee(Employee product) {
        employeeRepo.delEmployee(product);
    }

    @Override
    public void updateEmployee(Employee product) {
        employeeRepo.updateEmployee(product);
    }

    @Override
    public Long getNumberOfEmployee() {
        return employeeRepo.getNumberOfEmployee();
    }

    @Override
    public Boolean checkEmployeeExist(String cccd) {
        return employeeRepo.getEmployeeByCccd(cccd) != null;
    }
}
