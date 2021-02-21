package com.hedjerciyakoub.companymanagementwebapp.entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import com.hedjerciyakoub.companymanagementwebapp.validations.AgeLimites;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.hedjerciyakoub.companymanagementwebapp.validations.OuxAnnotation;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "employee")
public class Employee {


	@Id
	@GenericGenerator(name = "employee", strategy = "com.hedjerciyakoub.companymanagementwebapp.generators.EmployeeGenerator")
	@GeneratedValue(generator = "employee")
	@Column(name = "emp_id")
	private int empId;

//
//	@NotBlank(message = "This field is required")
//	@Size(max = 30, message = "Maximum is 30 letters")
//	@Size(min = 2, message = "minimum is 2 letters ")
//	@Pattern(regexp = "^[A-Za-z]+|^[A-Za-z]+ [A-Za-z]+", message = "only letters / max is tow words") // ^(([A-Za-z]+
//																										// ([A-Za-z])+)
//																										// {1,50})$
	@OuxAnnotation(notBlankNotNullNotEmpty = true, notBlankNotNullNotEmptyMessage = "First Name is required",
				minStringLength = 2, maxStringLength = 30, minMaxStringLengthMessage="Characters number should be between 2 and 30",
				regex = "^[A-Za-z]+|^[A-Za-z]+ [A-Za-z]+", regexMessage = "Only characters and maximum is tow words")
	@Column(name = "emp_first_name")
	private String firstName;


	@OuxAnnotation(notBlankNotNullNotEmpty = true, notBlankNotNullNotEmptyMessage = "Last Name is required",
			minStringLength = 2, maxStringLength = 30, minMaxStringLengthMessage="Characters required between 2 and 30",
			regex = "^[A-Za-z]+|^[A-Za-z]+ [A-Za-z]+", regexMessage = "Only characters and maximum is tow words")
	@Column(name = "emp_last_name")
	private String lastName;


	@OuxAnnotation(notBlankNotNullNotEmpty = true, notBlankNotNullNotEmptyMessage = "Address is required",
			minStringLength = 3, maxStringLength = 60, minMaxStringLengthMessage = "Characters number should be between 3 and 60")
	@Column(name = "emp_address")
	private String address;


	@OuxAnnotation(notBlankNotNullNotEmpty = true, notBlankNotNullNotEmptyMessage = "Email is required",
				regex = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", regexMessage = "Check Email format")
	@Column(name = "emp_email")
	private String email;


	@NotNull(message = "Salary is Required")
	@Min(value = 18, message = "Salary should be between 18 and 45")
	@Max(value = 45, message = "Salary should be between 18 and 45")
	@Column(name = "emp_salary")
	private Double salary;


	@Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "must be only numbers")
	@Column(name = "emp_date_of_hire")
	private String hireDate;


	@AgeLimites(min = 18 , max = 67 , message = "Age should be between 18 and 67")
	@Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "must be only numbers")
	@Column(name = "emp_date_of_birth")
	private String birthDate;


	@NotNull(message = "Department is required")
	@ManyToOne
	@JoinColumn(name = "dept_id")
	private Department department;


	@OneToMany(mappedBy = "employee", // employee is the name of field in the EmployeePhones class which have
										// @ManyToOne
			cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.REFRESH , CascadeType.REMOVE})
	private List<EmployeePhones> employeePhones;


	@OneToMany(mappedBy = "employee", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,CascadeType.REMOVE })
	private List<WorkOn> workOns = new ArrayList<WorkOn>() ;


	public Employee(String firstName, String lastName, String address, String email, Double salary, String hireDate, String birthDate, Department department  ) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.salary = salary;
		this.hireDate = hireDate;
		this.birthDate = birthDate;
		this.department = department;


	}


	@Override
	public String toString() {

		return "Employee [empId=" + empId + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", email=" + email + ", salary=" + salary + ", hireDate=" + hireDate + ", birthDate="
				+ birthDate + ", deptId=" + department.getDeptId() + "]";

	}


}
