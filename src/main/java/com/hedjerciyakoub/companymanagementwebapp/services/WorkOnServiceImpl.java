package com.hedjerciyakoub.companymanagementwebapp.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hedjerciyakoub.companymanagementwebapp.dao.WorkOnRepository;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Employee;
import com.hedjerciyakoub.companymanagementwebapp.entitys.Project;
import com.hedjerciyakoub.companymanagementwebapp.entitys.WorkOn;

@AllArgsConstructor
@Service
public class WorkOnServiceImpl {


	private final WorkOnRepository WorkOnRepository;


	public void save(WorkOn workOn) {
		WorkOnRepository.save(workOn);
	}


	public void deleteById(int id) {
		WorkOnRepository.deleteById(id);
	}


	public List<WorkOn> findAll() {

		List<WorkOn> workOns = WorkOnRepository.findAll();

		return workOns;

	}


	public WorkOn findById(int id) {

		WorkOn workOn =  WorkOnRepository.findById(id).orElseThrow(()->new RuntimeException("There is no employee with that id = " + id +" have work postion"));

		return workOn;

	}


	public List<WorkOn> findByProject(Project projId){

		List<WorkOn> workOns = WorkOnRepository.findByProject(projId);

		return workOns;

	}


	public List<WorkOn> findByEmployee(Employee employee){

		List<WorkOn> workOns = WorkOnRepository.findByEmployee(employee);

		return workOns ;

	}


}
