package com.hedjerciyakoub.companymanagementwebapp.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hedjerciyakoub.companymanagementwebapp.dao.EmployeeRepository;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Department;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Employee;
import com.hedjerciyakoub.companymanagementwebapp.entitys.EmployeePhones;
import com.hedjerciyakoub.companymanagementwebapp.entitys.WorkOn;


@AllArgsConstructor
@Service
public class EmployeeServiceImpl {

	private final EmployeeRepository employeeRepository;
	private final EmployeePhonesServiceImpl employeePhonesServiceImpl;
	private final WorkOnServiceImpl workOnServiceImpl;



	public void save(Employee employee) {
		employeeRepository.save(employee);
	}



	@Transactional(rollbackFor = Exception.class)
	public void saveEmployeeWithHisPhonesAndWorkOns(Employee employee, List<EmployeePhones> employeePhones , List<WorkOn> workOns) throws Exception {

		if(employee.getEmpId()!=0 && workOnServiceImpl.findByEmployee(employee)!=null) {
			for(WorkOn wo : workOnServiceImpl.findByEmployee(employee)) {
				workOnServiceImpl.deleteById(wo.getWorkOnId());
			}
		}

		save(employee);

		if (employeePhones != null) {
			for (EmployeePhones empPhones : employeePhones) {
				empPhones.setEmployee(employee);
				employeePhonesServiceImpl.save(empPhones);
			}
		}
		
		int id = 0;
		for(int i = 0 ; i<workOns.size() ; i++) {
			
			
			workOns.get(i).setWorkOnId(id);
			workOns.get(i).setEmployee(employee);
			workOnServiceImpl.save(workOns.get(i));
			//id = workOns.get(i).getWorkOnId()+1;
			id++;
			
		}

	}

	public void deleteById(int id) {
		employeeRepository.deleteById(id);
	}

	public void deleteAll() {
		employeeRepository.deleteAll();
	}

	public List<Employee> findAll() {

		List<Employee> employees = employeeRepository.findAllByOrderByFirstNameAsc();

		return employees;

	}

	public Employee findById(int id) {

		Employee employee = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("There is no Department with that id = " + id));

		return employee;

	}


	public List<Employee> findByFirstName(String firstName) {
		return employeeRepository.findByFirstName(firstName);
	}

	public List<Employee> findByLastName(String lastName) {
		return employeeRepository.findByLastName(lastName);
	}

	public List<Employee> findByAddress(String address) {
		return employeeRepository.findByAddress(address);
	}

	public List<Employee> findByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}

	public List<Employee> findBySalary(Double salary) {
		return employeeRepository.findBySalary(salary);
	}

	public List<Employee> findByHireDate(String hireDate) {
		return employeeRepository.findByHireDate(hireDate);
	}

	public List<Employee> findByBirthDate(String birthDate) {
		return employeeRepository.findByBirthDate(birthDate);
	}

	public List<Employee> findByDepartment(Department department) {
		return employeeRepository.findByDepartment(department);
	}

	public List<Employee> findByEmpId(int deptId) {
		return employeeRepository.findByEmpId(deptId);
	}
}
