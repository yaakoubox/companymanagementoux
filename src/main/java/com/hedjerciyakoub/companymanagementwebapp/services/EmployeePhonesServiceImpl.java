package com.hedjerciyakoub.companymanagementwebapp.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hedjerciyakoub.companymanagementwebapp.dao.EmployeePhonesRepository;
import com.hedjerciyakoub.companymanagementwebapp.entitys.EmployeePhones;


@AllArgsConstructor
@Service
public class EmployeePhonesServiceImpl {


	private final EmployeePhonesRepository EmployeePhonesRepository;


	public void save(EmployeePhones employeePhones) {

		EmployeePhonesRepository.save(employeePhones);
		
	}

	
	public void deleteById(int id) {

		EmployeePhonesRepository.deleteById(id);

	}
	

	public List<EmployeePhones> findAll() {
		
		List<EmployeePhones> employeePhones = EmployeePhonesRepository.findAll();

		return employeePhones;

	}

	
	public EmployeePhones findById(int id) {

		EmployeePhones phones = EmployeePhonesRepository.findById(id).orElseThrow(()->new RuntimeException("There is no Department with that id = " + id ));

		return phones;

	}


	public EmployeePhones findByPhone(String phone) {

		EmployeePhones phoneNumber = EmployeePhonesRepository.findByPhone(phone).orElseThrow(()->new RuntimeException("There is no phone with that number = " + phone));

		return phoneNumber;

	}

	
	public List<EmployeePhones> findEmployeePhones(int employeeId) {
		
		List<EmployeePhones> employeePhones = EmployeePhonesRepository.findByEmployeeContainsAllIgnoreCase(employeeId);
		
		return employeePhones;
		
	}
	
	

}
