
package com.hedjerciyakoub.companymanagementwebapp.controller;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.hedjerciyakoub.companymanagementwebapp.security.services.SocialUsersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Employee;
import com.hedjerciyakoub.companymanagementwebapp.entitys.EmployeePhones;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Project;
import com.hedjerciyakoub.companymanagementwebapp.entitys.WorkOn;
import com.hedjerciyakoub.companymanagementwebapp.services.DepartmentServiceImpl;
import com.hedjerciyakoub.companymanagementwebapp.services.EmployeePhonesServiceImpl;
import com.hedjerciyakoub.companymanagementwebapp.services.EmployeeServiceImpl;
import com.hedjerciyakoub.companymanagementwebapp.services.ProjectServiceImpl;
import com.hedjerciyakoub.companymanagementwebapp.services.WorkOnServiceImpl;

@AllArgsConstructor
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	private EmployeeServiceImpl EmployeeServiceImpl;
	private DepartmentServiceImpl DepartmentServiceImpl;
	private EmployeePhonesServiceImpl EmployeePhonesServiceImpl;
	private ProjectServiceImpl ProjectServiceImpl;
	private WorkOnServiceImpl WorkOnServiceImpl;
	private List<EmployeePhones> employeePhoneList ;
	private SocialUsersServiceImpl socialUsersServiceImpl;

	@GetMapping("/list")
	public String listEmployee(Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);

		employeePhoneList = new ArrayList<EmployeePhones>();
		List<Employee> employees = EmployeeServiceImpl.findAll();
		model.addAttribute("employees", employees);

		return "employee-folder/list-employee";

	}

	@GetMapping("/add")
	public String showAddForm(Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);
			List<Department> departments = DepartmentServiceImpl.findAll();
			Employee employee = new Employee();
			EmployeePhones employeePhones = null;
			model.addAttribute("employee", employee);
			model.addAttribute("departments", departments);
			model.addAttribute("employeePhones", employeePhones);

		return "employee-folder/employee-add-form";

	}


	@ModelAttribute
	public void provideWorkOn(Model model) {

		model.addAttribute("workOnListAsString", new String());

	}

	@PostMapping("/save")
	public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
							   BindingResult bindingResult,
							   @ModelAttribute("phone") String phone,
							   @ModelAttribute("selectedPhone") String selectedPhone,
							   @ModelAttribute("submitButton") String submitButton,
							   String workOnListAsString,
							   boolean show,
							   boolean phoneCheck,
							   Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);

		int theId = employee.getEmpId();

		if (employee.getEmpId() != 0 && EmployeePhonesServiceImpl.findEmployeePhones(employee.getEmpId()).size() > 0
				&& (employeePhoneList.containsAll(EmployeePhonesServiceImpl.findEmployeePhones(employee.getEmpId()))
						|| employeePhoneList.isEmpty())) {



			employeePhoneList = new ArrayList<EmployeePhones>(
					EmployeePhonesServiceImpl.findEmployeePhones(employee.getEmpId()));

		}






		if (employeePhoneList.size() > 0) {
			show = true;
		}

		if (phone.matches("[0-9\\+\\- ]+") && !submitButton.equals("deletePhone")) {

			phoneCheck = true;

			if (!submitButton.equals("updatePhone")) {

				if (phone.length() <= 18) {

					EmployeePhones employeePhone = new EmployeePhones();
					employeePhone.setEmployee(employee);
					employeePhone.setPhone(phone);


					boolean phoneExist = false;
					for (EmployeePhones e : employeePhoneList) {
						if (e.getPhone().equals(phone)) {
							phoneExist = true;
							phoneCheck = false;
							model.addAttribute("phoneCheckText", "you try add phone alredy exist in the phones list");
							break;
						}
					}

					if (!phoneExist) {

						employeePhoneList.add(employeePhone);
					}

				} else {

					phoneCheck = false;
					model.addAttribute("phoneCheckText", "max of phone is 18 caracter");
				}

			} else if (!show && submitButton.equals("updatePhone")) {

				model.addAttribute("phoneCheckText", "There is no Number to update");
				phoneCheck = false;
			}

		} else if (!submitButton.equals("deletePhone")) {

			phoneCheck = false;
			if (phone.isEmpty()) {

				if (submitButton.equals("updatePhone")) {
					model.addAttribute("phoneCheckText", "There is no Number to update");
				} else if (submitButton.equals("savePhone")) {
					model.addAttribute("phoneCheckText", "you cant add empty phone number");
				}

			} else {

				model.addAttribute("phoneCheckText", "check Phone format");
			}
		}

		model.addAttribute("phoneCheck", phoneCheck);


		if (!submitButton.equals("deletePhone") && !submitButton.equals("updatePhone")) {


			List<EmployeePhones> employeePhoneListReversed = reverseList(employeePhoneList);
			if (employeePhoneListReversed.size() > 0) {
				show = true;
			} else {
				show = false;
			}

			model.addAttribute("show", show);

			model.addAttribute("employeePhones", employeePhoneListReversed);

		}

		if (submitButton.equals("deletePhone")) {

			List<EmployeePhones> employeePhoneListReversed = new ArrayList<EmployeePhones>();
			int size = employeePhoneList.size();

			int nothingForDelete = 0;

			if (employee.getEmpId() == 0 && size > 0) {



				if (phone.isEmpty()) {

					employeePhoneList.remove(employeePhoneList.get(employeePhoneList.size() - 1));

					nothingForDelete = 1;
				} else {

					for (EmployeePhones e : employeePhoneList) {
						if (e.getPhone().equals(phone)) {

							employeePhoneList.remove(e);
							nothingForDelete = 1;

							break;
						}
					}
				}

			} else if (employee.getEmpId() != 0) {


				int z = EmployeePhonesServiceImpl.findEmployeePhones(employee.getEmpId()).size();
				int z2 = employeePhoneList.size();

				if (phone.isEmpty() && z2 != 0) {


					List<EmployeePhones> employeePhones = EmployeePhonesServiceImpl
							.findEmployeePhones(employee.getEmpId());

					EmployeePhones employeePhoneForDelete = employeePhoneList.get(employeePhoneList.size() - 1);


					if (employeePhoneList.contains(employeePhoneForDelete)) {


						employeePhoneList.remove(employeePhoneForDelete);

						for (EmployeePhones e : employeePhones) {
							if (e.getPhone().equals(employeePhoneForDelete.getPhone())) {

								EmployeePhonesServiceImpl.deleteById(employeePhoneForDelete.getPhoneId());
							}
						}


						nothingForDelete = 1;
					}


				} else if (z2 == 0) {


					if (phone.isEmpty()) {

						phoneCheck = false;
						model.addAttribute("phoneCheck", phoneCheck);
						model.addAttribute("phoneCheckText", "there is nothing to delete");
						nothingForDelete = 1;
					} else {

						for (EmployeePhones e : employeePhoneList) {
							if (e.getPhone().equals(phone)) {

								employeePhoneList.remove(e);
								nothingForDelete = 1;
								break;
							}
						}
					}

				} else {


					for (EmployeePhones e : employeePhoneList) {
						if (e.getPhone().equals(phone)) {
							employeePhoneList.remove(e);
							nothingForDelete = 1;
							break;
						}
					}

					for (EmployeePhones e : EmployeePhonesServiceImpl.findEmployeePhones(employee.getEmpId())) {
						if (e.getPhone().equals(phone)) {

							EmployeePhones employeePhoneForDelete = EmployeePhonesServiceImpl.findByPhone(phone);
							EmployeePhonesServiceImpl.deleteById(employeePhoneForDelete.getPhoneId());
							nothingForDelete = 1;
							break;
						}
					}
				}

			}

			if (nothingForDelete == 0) {

				phoneCheck = false;
				model.addAttribute("phoneCheck", phoneCheck);
				if (employeePhoneList.size() != 0) {
					model.addAttribute("phoneCheckText", "this phone dose not exist in the list");
				} else {
					model.addAttribute("phoneCheckText", "there is nothing to delete");
				}

			}

			if (employeePhoneList.size() > 0) {

				for (int i = employeePhoneList.size() - 1; i >= 0; i--) {
					employeePhoneListReversed.add(employeePhoneList.get(i));
					show = true;
				}

				model.addAttribute("show", show);

				model.addAttribute("employeePhones", employeePhoneListReversed);
			} else {

				show = false;
				model.addAttribute("show", show);
				model.addAttribute("departments", DepartmentServiceImpl.findAll());

			}

		}

		else if (submitButton.equals("updatePhone") && phoneCheck) {

			List<EmployeePhones> employeePhones;
			boolean phoneForUpdateExist = false;

			if (employee.getEmpId() == 0
					|| EmployeePhonesServiceImpl.findEmployeePhones(employee.getEmpId()).size() == 0
					|| EmployeePhonesServiceImpl.findEmployeePhones(employee.getEmpId()).size() != 0) {


				for (EmployeePhones e : employeePhoneList) {

					if (e.getPhone().equals(phone)) {
						phoneForUpdateExist = true;
						break;
					}
				}

				if (!phoneForUpdateExist) {

					for (int i = 0; i < employeePhoneList.size(); i++) {
						if (employeePhoneList.get(i).getPhone().equals(selectedPhone)) {

							employeePhoneList.get(i).setPhone(phone);

							break;
						} else if (i == employeePhoneList.size() - 1) {
							employeePhoneList.get(employeePhoneList.size() - 1).setPhone(phone);
						}
					}
				} else {

					phoneCheck = false;
					model.addAttribute("phoneCheckText", "this phone exist on the list");
				}

			}
		}

		if (bindingResult.hasErrors()) {

			if (employeePhoneList.size() > 0) {

				show = true;
				model.addAttribute("show", show);
				
				model.addAttribute("employeePhones", reverseList(employeePhoneList));
				
				
			}

			if(workOnListAsString==null){
				model.addAttribute("projectError", true);
			}

			if(phone.isEmpty()){
				model.addAttribute("phoneCheck", true);
			}
			model.addAttribute("departments", DepartmentServiceImpl.findAll());
			return "employee-folder/employee-add-form";
		}


		if (submitButton.equals("savePhone") && phoneCheck) {

			show = true;

		}

		if (submitButton.equals("saveButton") && (phoneCheck || phone.isEmpty())) {

			try {

				if (employeePhoneList.size() != 0) {
					for (int i = 0; i < employeePhoneList.size(); i++) {
						if (employee.getEmpId() == 0) {
							employeePhoneList.get(i).setEmployee(employee);
						}
					}
				}

				List<WorkOn> workOns = new ArrayList<WorkOn>();
				if (workOnListAsString != null && employee.getWorkOns() != null) {

					String[] ProjectsNameSelected = workOnListAsString.split(",");

					for (String projectName : ProjectsNameSelected) {

						for (Project project : ProjectServiceImpl.findByDepartment(employee.getDepartment())) {
							if (project.getName().equals(projectName)) {
								workOns.add(new WorkOn(employee, project));
							}
						}
					}


				} else {

					model.addAttribute("projectError", true);
					employee.setEmpId(theId);
					phoneCheck = false;
					model.addAttribute("employee", employee);
					model.addAttribute("phoneCheck", phoneCheck);
					model.addAttribute("employeePhones", reverseList(employeePhoneList));
					model.addAttribute("departments", DepartmentServiceImpl.findAll());
					show = true;
					model.addAttribute("show", show);
					return "employee-folder/employee-add-form";
				}

				EmployeeServiceImpl.saveEmployeeWithHisPhonesAndWorkOns(employee, employeePhoneList, workOns);

			} catch (Exception e) {
				e.printStackTrace();
				employee.setEmpId(theId);
				phoneCheck = false;
				model.addAttribute("employee", employee);
				model.addAttribute("phoneCheck", phoneCheck);
				model.addAttribute("phoneCheckText", "you cant , this phone alreddy exist in the phones data");
				model.addAttribute("employeePhones", reverseList(employeePhoneList));
				model.addAttribute("departments", DepartmentServiceImpl.findAll());
				show = true;
				model.addAttribute("show", show);
				return "employee-folder/employee-add-form";
			}

			model.addAttribute("show", show);

			employeePhoneList = new ArrayList<EmployeePhones>();

			return "redirect:/employee/list";
		}

		model.addAttribute("employee", employee);


		model.addAttribute("departments", DepartmentServiceImpl.findAll());

		model.addAttribute("employeePhones", reverseList(employeePhoneList));

		model.addAttribute("show", show);

		model.addAttribute("phoneCheck", phoneCheck);


		return "employee-folder/employee-add-form";

	}

	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("employeeId") int id) {

		EmployeeServiceImpl.deleteById(id);

		return "redirect:/employee/list";

	}


	@GetMapping("/update")
	public String updateEmployee(@RequestParam("employeeId") int id, Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);
		Employee employee = EmployeeServiceImpl.findById(id);
		model.addAttribute("employee", employee);
		List<Department> departments = DepartmentServiceImpl.findAll();
		List<EmployeePhones> employeePhones = EmployeePhonesServiceImpl.findEmployeePhones(id);
		Collections.reverse(employeePhones);
		model.addAttribute("employeePhones", employeePhones);
		model.addAttribute("departments", departments);

		boolean showEmployeeDropDown = false;
		if (employeePhones.size() > 0) {
			showEmployeeDropDown = true;
		}
		model.addAttribute("show", showEmployeeDropDown);

		return "employee-folder/employee-add-form";

	}


	@PostMapping("/search")
	public String searchEmployee(@ModelAttribute("searchBy") String searchBy , @ModelAttribute("searchText") String searchText , Model model, Principal principal) {
		socialUsersServiceImpl.setPrincipalUsername(model,principal);
		
		employeePhoneList = new ArrayList<EmployeePhones>();
		List<Employee> employees = new ArrayList<Employee>();
		switch (searchBy) {

		case "First Name":
			employees = EmployeeServiceImpl.findByFirstName(searchText );

			break;

		case "Last Name":
			employees = EmployeeServiceImpl.findByLastName(searchText );
			break;

		case "Address":
			employees = EmployeeServiceImpl.findByAddress(searchText );
			break;

		case "Email":
			employees = EmployeeServiceImpl.findByEmail(searchText );
			break;

		case "Salary":
			try {
				employees = EmployeeServiceImpl.findBySalary(Double.parseDouble(searchText));	
			} catch (Exception e) {
				model.addAttribute("numberFormat", true);
				employees=EmployeeServiceImpl.findAll();
			}
			break;

		case "Birth Date":
			if(searchText.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {

				employees = EmployeeServiceImpl.findByBirthDate(searchText);
			}else {
				model.addAttribute("dateFormat", true);
				employees=EmployeeServiceImpl.findAll();
			}
			break;

		case "Hire Date":
			if(searchText.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {

				employees = EmployeeServiceImpl.findByHireDate(searchText);	
			}else {
				model.addAttribute("dateFormat", true);
				employees=EmployeeServiceImpl.findAll();
			}
			break;

		case "Phone number":
			employees.add(EmployeePhonesServiceImpl.findByPhone(searchText ).getEmployee());
			break;

		case "Department Name":
			employees = EmployeeServiceImpl.findByDepartment(DepartmentServiceImpl.findByName(searchText ).get(0));
			break;

		case "Department Location":
			List<Department> departments = DepartmentServiceImpl.findByLocation(searchText );

			for (Department department : departments) {
				employees.addAll(EmployeeServiceImpl.findByDepartment(department));
			}
			break;

		case "Project Name":
			List<Project> projects = ProjectServiceImpl.findByName(searchText );
			for (Project project : projects) {
				for (WorkOn wo : project.getWorkOns()) {
					employees.add(wo.getEmployee());
				}
			}
			break;

		case "Project Location":
		    projects = ProjectServiceImpl.findByLocation(searchText);
			for (Project project : projects) {
				for (WorkOn wo : project.getWorkOns()) {
					if(!employees.contains(wo.getEmployee())) {
						employees.add(wo.getEmployee());
					}
				}
			}
			break;

		}

		model.addAttribute("employees", employees);

		return "employee-folder/list-employee";

	}


	private List<EmployeePhones> reverseList(List<EmployeePhones> employeePhones) {
		if (employeePhones != null) {
			List<EmployeePhones> employeePhoneListReversed = new ArrayList<EmployeePhones>();
			for (int i = employeePhones.size() - 1; i >= 0; i--) {
				employeePhoneListReversed.add(employeePhoneList.get(i));
			}
			return employeePhoneListReversed;
		}
		return null;
	}


}
