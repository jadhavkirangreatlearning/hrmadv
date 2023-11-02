package com.csi.controller;

import com.csi.entity.Employee;
import com.csi.exception.RecordNotFoundException;
import com.csi.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


    @PostMapping("/signup")
    public ResponseEntity<Employee> signUp(@Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeServiceImpl.signUp(employee), HttpStatus.CREATED);
    }

    @PostMapping("/saveall")
    public ResponseEntity<List<Employee>> saveAll(@Valid @RequestBody List<Employee> employeeList) {
        return new ResponseEntity<>(employeeServiceImpl.saveAll(employeeList), HttpStatus.CREATED);

    }

    @GetMapping("/signin/{empEmailId}/{empPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId, @PathVariable String empPassword) {
        return ResponseEntity.ok(employeeServiceImpl.signIn(empEmailId, empPassword));
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeServiceImpl.findById(empId));
    }

    @GetMapping("/findbyfirstname/{empFirstName}")
    public ResponseEntity<List<Employee>> findByName(@PathVariable String empFirstName) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpFirstName().equals(empFirstName)).collect(Collectors.toList()));
    }

    @GetMapping("/findbycontactnumber/{empContactNumber}")
    public ResponseEntity<Employee> findByContactNumber(@PathVariable long empContactNumber) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpContactNumber() == empContactNumber).toList().get(0));
    }

    @GetMapping("/findbyuid/{empUID}")
    public ResponseEntity<Employee> findByUID(@PathVariable long empUID) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpUID() == empUID).toList().get(0));
    }

    @GetMapping("/findbydob/{empDOB}")
    public ResponseEntity<List<Employee>> findByDOB(@PathVariable String empDOB) {


        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> simpleDateFormat.format(emp.getEmpDOB()).equals(empDOB)).toList());
    }

    @GetMapping("/anyinput/{input}")
    public ResponseEntity<List<Employee>> findByAnyInput(@PathVariable String input) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> simpleDateFormat.format(emp.getEmpDOB()).equals(input)
                || String.valueOf(emp.getEmpId()).equals(input)
                || emp.getEmpFirstName().equals(input)
                || String.valueOf(emp.getEmpContactNumber()).equals(input)
                || emp.getEmpEmailId().equals(input)).toList());
    }

    @GetMapping("/findbyemail/{empEmailId}")
    public ResponseEntity<Employee> findByEmail(@PathVariable String empEmailId) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpEmailId().equals(empEmailId)).toList().get(0));

    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeServiceImpl.findAll());
    }

    @GetMapping("/sortbyid")
    public ResponseEntity<List<Employee>> sortById() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparingInt(Employee::getEmpId)).toList());

    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparing(Employee::getEmpFirstName)).toList());

    }

    @GetMapping("/sortbysalary")
    public ResponseEntity<List<Employee>> sortBySalary() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparingDouble(Employee::getEmpSalary)).toList());
    }

    @GetMapping("/sortbydob")
    public ResponseEntity<List<Employee>> sortByDOB() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparing(Employee::getEmpDOB)).toList());
    }


    @GetMapping("/filterbysalary/{empSalary}")
    public ResponseEntity<List<Employee>> filterBySalary(@PathVariable double empSalary) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpSalary() >= empSalary).toList());
    }

    @GetMapping("/checkloaneligibility/{empId}")
    public ResponseEntity<String> checkLoanEligibility(@PathVariable int empId) {
        String msg = "";

        Employee employee = employeeServiceImpl.findById(empId).orElseThrow(() -> new RecordNotFoundException("ID Does Not Exist"));

        if (employee.getEmpSalary() >= 50000.00) {
            msg = "Eligible for loan";
        } else {
            msg = "NOT Eligible for loan";
        }

        return ResponseEntity.ok(msg);
    }

    @PutMapping("/update/{empId}")
    public ResponseEntity<Employee> update(@PathVariable int empId, @Valid @RequestBody Employee employee) {
        Employee employee1 = employeeServiceImpl.findById(empId).orElseThrow(() -> new RecordNotFoundException("ID Does Not Exist"));

        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpDOB(employee.getEmpDOB());
        employee1.setEmpPassword(employee.getEmpPassword());
        employee1.setEmpFirstName(employee.getEmpFirstName());
        employee1.setEmpLastName(employee.getEmpLastName());
        employee1.setEmpUID(employee.getEmpUID());
        employee1.setEmpPanCardNumber(employee.getEmpPanCardNumber());

        return new ResponseEntity<>(employeeServiceImpl.update(employee1), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId) {
        employeeServiceImpl.deleteById(empId);
        return ResponseEntity.ok("DATA DELETED Successfully");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        employeeServiceImpl.deleteAll();
        return ResponseEntity.ok("ALl Data Deleted Successfully");
    }

    @GetMapping("/fetchsecondlargestsalary")
    public ResponseEntity<Employee> fetchSecondLargestSalaryRecord() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparingDouble(Employee::getEmpSalary).reversed()).toList().get(1));
    }
}
