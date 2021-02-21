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
import org.springframework.web.bind.annotation.*;

import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import com.hedjerciyakoub.companymanagementwebapp.services.DepartmentServiceImpl;


@AllArgsConstructor
@Controller
@RequestMapping("/department")
public class DepartmentController {


	private final DepartmentServiceImpl departmentServiceImpl;
	private final SocialUsersServiceImpl socialUsersServiceImpl;

	
	@GetMapping("/list")
	public String listDepartment(Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);



		List<Department> departments = departmentServiceImpl.findAll();
		model.addAttribute("departments", departments);

		return "department-folder/list-department";

	}

	
	@GetMapping("/add")
	public String showAddForm(Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);

		Department department = new Department();
		model.addAttribute("department",department );

		return"department-folder/department-add-form";

	}

	
	@PostMapping("/save")
	public String saveDepatrment(@Valid @ModelAttribute("department") Department department , BindingResult bindingResult , Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);


		if(bindingResult.hasErrors()) {
			return"department-folder/department-add-form";
		}

		try {
			departmentServiceImpl.save(department);
		} catch (Exception e) {
			if(e.getMessage().contains("[dept_name_UNIQUE]")) {
				model.addAttribute("showError", true);
				return"department-folder/department-add-form";
			}else {
				e.printStackTrace();
			}
		}
		
		return "redirect:/department/list";

	}




	@GetMapping("update")
	public String updateDepartment(@RequestParam("departmentId")int id , Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);
		
		Department department = departmentServiceImpl.findById(id);

		model.addAttribute("department", department);
		
		return"department-folder/department-add-form";

	}


	@GetMapping("/delete")
	public String deleteDepartment(@RequestParam("departmentId")int id) {
		
		departmentServiceImpl.deleteById(id);
		
		return "redirect:/department/list";

	}


	@PostMapping("/search")
	public String searchEmployee(@ModelAttribute("searchBy") String searchBy,@ModelAttribute("searchText") String searchText , Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);

		List<Department> departments = new ArrayList<Department>();
		switch (searchBy) {
		case "Name":
			departments = departmentServiceImpl.findByName(searchText);
			break;
			
		case "Location":
			departments = departmentServiceImpl.findByLocation(searchText);
			break;
		
		}

		model.addAttribute("departments", departments);

		return "department-folder/list-department";

	}




}
