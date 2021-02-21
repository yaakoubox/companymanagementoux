package com.hedjerciyakoub.companymanagementwebapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.hedjerciyakoub.companymanagementwebapp.security.entitys.SocialUsers;
import com.hedjerciyakoub.companymanagementwebapp.security.services.SocialUsersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Project;
import com.hedjerciyakoub.companymanagementwebapp.services.DepartmentServiceImpl;
import com.hedjerciyakoub.companymanagementwebapp.services.ProjectServiceImpl;


@AllArgsConstructor
@Controller
@RequestMapping("/project")
public class ProjectController {


	private final ProjectServiceImpl projectServiceImpl;
	private final DepartmentServiceImpl departmentServiceImpl;
	private final SocialUsersServiceImpl socialUsersServiceImpl;


	
	@GetMapping("/list")
	public String listProject(Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);
		
		List<Project> projects = projectServiceImpl.findAll();
		model.addAttribute("projects", projects);
		
		return "project-folder/list-project";
	}
	
	@GetMapping("/add")
	public String showAddForm(Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);

		Project project = new Project();
		model.addAttribute("project", project);
		List<Department> departments = departmentServiceImpl.findAll();
		model.addAttribute("departments", departments);
		
		return"project-folder/project-add-form";
	}
	
	@PostMapping("/save")
	public String saveProject(@Valid @ModelAttribute("project") Project project , BindingResult bindingResult , Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);
		List<Department> departments = departmentServiceImpl.findAll();
		model.addAttribute("departments", departments);

		if(bindingResult.hasErrors()) {
			return"project-folder/project-add-form";
		}
		
		try {
			projectServiceImpl.save(project);
		} catch (Exception e) {
			if(e.getMessage().contains("[proj_name]")){
				model.addAttribute("showError", true);
				return "project-folder/project-add-form";
			}else {
				e.printStackTrace();
			}
		}
		
		return "redirect:/project/list";
		
	}
	
	@GetMapping("/update")
	public String updateProject(@RequestParam("projectId") int id , Model model , Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);

		Project project = projectServiceImpl.findById(id);
		model.addAttribute("project", project);
		List<Department> departments = departmentServiceImpl.findAll();
		model.addAttribute("departments", departments);
		
		return"project-folder/project-add-form";

	}
	
	@GetMapping("/delete")
	public String deleteProject(@RequestParam("projectId")int id ) {

		projectServiceImpl.deleteById(id);

		return "redirect:/project/list";

	}
	
	@PostMapping("/search")
	public String searchEmployee(@ModelAttribute("searchBy") String searchBy,@ModelAttribute("searchText") String searchText , Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);

		List<Project> projects = new ArrayList<Project>();
		switch (searchBy) {

		case "Name":
			projects = projectServiceImpl.findByName(searchText);
			break;
			
		case "Location":
			projects = projectServiceImpl.findByLocation(searchText);
			break;
		
		case "Department Name":
			projects = projectServiceImpl.findByDepartment(departmentServiceImpl.findByName(searchText).get(0));
			break;

		case "Department Location":
			List<Department> departments = departmentServiceImpl.findByLocation(searchText);
			for (Department department : departments) {
				projects.addAll(projectServiceImpl.findByDepartment(department));
			}
			break;
		}

		model.addAttribute("projects", projects);

		 return "project-folder/list-project";

	}
	
}
