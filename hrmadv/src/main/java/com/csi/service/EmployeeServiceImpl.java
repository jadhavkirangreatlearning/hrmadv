package com.csi.service;

import com.csi.entity.Employee;
import com.csi.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl {

    @Autowired
    private EmployeeRepo employeeRepoImpl;

    public Employee signUp(Employee employee) {
        return employeeRepoImpl.save(employee);
    }

    public List<Employee> saveAll(List<Employee> employeeList) {
        return employeeRepoImpl.saveAll(employeeList);
    }

    public boolean signIn(String email, String password) {
        Employee employee = employeeRepoImpl.findByEmpEmailIdAndEmpPassword(email, password);

        boolean status = false;
        if (null != employee
                && employee.getEmpEmailId().equals(email)
                && employee.getEmpPassword().equals(password)) {
            status = true;
        }

        return status;
    }

    @Cacheable(value = "empId")
    public Optional<Employee> findById(int empId) {
        return employeeRepoImpl.findById(empId);
    }

    public List<Employee> findAll() {
        return employeeRepoImpl.findAll();
    }

    public Employee update(Employee employee) {
        return employeeRepoImpl.save(employee);
    }

    public void deleteById(int empId) {
        employeeRepoImpl.deleteById(empId);
    }

    public void deleteAll() {
        employeeRepoImpl.deleteAll();
    }

}
