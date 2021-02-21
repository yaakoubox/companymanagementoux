package com.hedjerciyakoub.companymanagementwebapp.dao;

import java.util.List;

import com.hedjerciyakoub.companymanagementwebapp.entitys.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

	List<Project> findByName(String Name);
	
	List<Project> findByDepartment(Department department);
	
	List<Project> findByLocation(String location);

	List<Project> findAllByOrderByNameAsc();
}
