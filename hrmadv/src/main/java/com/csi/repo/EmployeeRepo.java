package com.csi.repo;

import com.csi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    public Employee findByEmpEmailIdAndEmpPassword(String empEmailId, String empPassword);
}
