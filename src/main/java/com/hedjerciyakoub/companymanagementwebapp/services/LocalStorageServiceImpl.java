package com.hedjerciyakoub.companymanagementwebapp.services;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Project;

@Service
public class LocalStorageServiceImpl {
	
	
	public void upToDateDepartment(List<Department> AllDepartmentsFromDatabase) throws Exception{

			ObjectMapper mapper = new ObjectMapper();

			Map<String, List<String>> departmentsWithThierProjectName = new HashMap<String, List<String>>();

			for(Department department : AllDepartmentsFromDatabase) {
				List<String> projectName = new ArrayList<String>();


				for(Project project : department.getProjects()) {
					projectName.add(project.getName());
				}

				departmentsWithThierProjectName.put(department.getName(),projectName);
			}


			mapper.writeValue(new File("src/main/resources/static/json-files/departmentfile.json"), departmentsWithThierProjectName);

	}

	public Map<String, List<String>> upToDateDepartmentApi(List<Department> AllDepartmentsFromDatabase) throws Exception {

		Map<String, List<String>> departmentsWithThierProjectName = new HashMap<String, List<String>>();

		for (Department department : AllDepartmentsFromDatabase) {
			List<String> projectName = new ArrayList<String>();


			for (Project project : department.getProjects()) {
				projectName.add(project.getName());
			}

			departmentsWithThierProjectName.put(department.getName(), projectName);
		}


		return departmentsWithThierProjectName;

	}
}
