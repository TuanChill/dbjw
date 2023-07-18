package com.ba.dbjw.Service.Empoyee;


import com.ba.dbjw.Entity.Employee.Employee;
import com.ba.dbjw.Repo.Employee.EmployeeRepo;
import com.ba.dbjw.Repo.Employee.EmployeeRepoImpl;
import com.ba.dbjw.Utils.CloudinaryUtil;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService<Employee> {
    private final EmployeeRepo<Employee> employeeRepo = new EmployeeRepoImpl();

    @Override
    public boolean createEmployee(Employee employee) {
        String imgPath = employee.getAvatar();
        if (imgPath != null) {
            String url = CloudinaryUtil.uploadImgToCloudinary(imgPath);
            employee.setAvatar(url);
        }
        return employeeRepo.saveEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.getAllEmployees();
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        return employeeRepo.delEmployee(employee);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        String imgPath = employee.getAvatar();
        if(imgPath != null && !imgPath.contains("res.cloudinary.com")) {
            String url = CloudinaryUtil.uploadImgToCloudinary(imgPath);
            employee.setAvatar(url);
        }
        return employeeRepo.updateEmployee(employee);
    }

    @Override
    public Long getNumberOfEmployee() {
        return employeeRepo.getNumberOfEmployee();
    }

    @Override
    public Employee getEmployeeByCode(String code) {
        if (code == null) {
            return null;
        } else {
            return employeeRepo.getEmployeeByCode(code);
        }
    }

    @Override
    public Boolean checkEmployeeExist(String cccd) {
        return employeeRepo.getEmployeeByCccd(cccd) != null;
    }
}
