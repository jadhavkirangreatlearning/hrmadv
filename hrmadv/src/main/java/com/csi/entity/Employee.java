package com.csi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private int empId;

    @Size(min = 2, message = "First Name should be atleast 2 character")
    private String empFirstName;

    @Pattern(regexp="[A-Za-z]*", message="Last name should not contain space and special characters")
    private String empLastName;

    private String empAddress;

    private double empSalary;

    @Column(unique = true)
    @Range(min = 1000000000, max = 9999999999L, message = "Employee Contact Number must be 10 digit")
    private long empContactNumber;

    @Column(unique = true)
    private long empUID;

    @Column(unique = true)
    private String empPanCardNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date empDOB;

    @Email(message = "Email ID must be valid")
    @Column(unique = true)
    private String empEmailId;

    @Size(min = 4, message = "Password should be atleast 4 character")
    private String empPassword;


}
