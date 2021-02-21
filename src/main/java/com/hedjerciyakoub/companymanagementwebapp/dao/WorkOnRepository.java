package com.hedjerciyakoub.companymanagementwebapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hedjerciyakoub.companymanagementwebapp.entitys.Employee;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Project;
import com.hedjerciyakoub.companymanagementwebapp.entitys.WorkOn;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOnRepository extends JpaRepository<WorkOn, Integer>{

	List<WorkOn> findByProject(Project project);
	List<WorkOn> findByEmployee(Employee employee);
}
