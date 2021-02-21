package com.hedjerciyakoub.companymanagementwebapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hedjerciyakoub.companymanagementwebapp.entitys.Employee;
import com.hedjerciyakoub.companymanagementwebapp.entitys.EmployeePhones;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePhonesRepository extends JpaRepository<EmployeePhones, Integer>{

	List<EmployeePhones> findByEmployeeContainsAllIgnoreCase(int employeeId);
	Optional<EmployeePhones> findByPhone(String phone);

}
