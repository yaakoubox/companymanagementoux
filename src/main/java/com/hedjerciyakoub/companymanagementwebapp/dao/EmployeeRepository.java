package com.hedjerciyakoub.companymanagementwebapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Employee;
import org.springframework.stereotype.Repository;

@Repository

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	
	List<Employee> findByFirstName(String firstName);
	List<Employee> findByLastName(String lastName);
	List<Employee> findByAddress(String address);
	List<Employee> findByEmail(String email);
	List<Employee> findBySalary(Double salary);
	List<Employee> findByHireDate(String hireDate);
	List<Employee> findByBirthDate(String birthDate);
	List<Employee> findByDepartment(Department department);
	List<Employee> findByEmpId(int deptId);
	List<Employee> findAllByOrderByFirstNameAsc();



	
	
}
