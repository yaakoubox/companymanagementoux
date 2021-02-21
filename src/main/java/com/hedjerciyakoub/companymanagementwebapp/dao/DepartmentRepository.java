package com.hedjerciyakoub.companymanagementwebapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	
	List<Department> findByName(String name);
	
	List<Department> findByLocation(String location);

	List<Department> findAllByOrderByNameAsc();
}
