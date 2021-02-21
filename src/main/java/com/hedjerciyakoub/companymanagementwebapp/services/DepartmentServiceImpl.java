package com.hedjerciyakoub.companymanagementwebapp.services;


import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.hedjerciyakoub.companymanagementwebapp.dao.DepartmentRepository;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;


@AllArgsConstructor
@Service
public class DepartmentServiceImpl  {

	
	private final DepartmentRepository departmentRepository;


	public void save(Department department) {
		departmentRepository.save(department);
	}


	public void deleteById(int id) {
		departmentRepository.deleteById(id);
	}

	
	public List<Department> findAll() {

		List<Department> departments = departmentRepository.findAllByOrderByNameAsc();

		return departments;

	}

	
	public Department findById(int id) {

		Department department = departmentRepository.findById(id).orElseThrow(()-> new RuntimeException("There is no Department with that id = " + id ));

		return department;

	}
	
	public List<Department> findByName(String name){

		List<Department> departments = departmentRepository.findByName(name);

		return departments;

	}
	
	public List<Department> findByLocation(String location){

		List<Department> departments = departmentRepository.findByLocation(location);

		return departments;

	}


}
