package com.hedjerciyakoub.companymanagementwebapp.services;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.hedjerciyakoub.companymanagementwebapp.dao.ProjectRepository;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Project;


@AllArgsConstructor
@Service
public class ProjectServiceImpl {


	private final ProjectRepository projectRepository;


	public void save(Project project) {
		projectRepository.save(project);
	}

	
	public void deleteById(int id) {
		projectRepository.deleteById(id);
	}

	
	public List<Project> findAll() {

		List<Project> projects = projectRepository.findAllByOrderByNameAsc();

		return projects;

	}

	
	public Project findById(int id) {

		Project project = projectRepository.findById(id).orElseThrow(()->new RuntimeException("There is no Department with that id = " + id));

		return project;

	}
	
	public List<Project> findByName(String name) {
		return projectRepository.findByName(name);
	}
	
	public List<Project> findByDepartment(Department department) {
		return projectRepository.findByDepartment(department);
	}
	
	public List<Project> findByLocation(String location) {
		return projectRepository.findByLocation(location);
	}


}
