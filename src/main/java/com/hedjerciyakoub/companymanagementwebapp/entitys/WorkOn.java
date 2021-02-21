package com.hedjerciyakoub.companymanagementwebapp.entitys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "workon")//,schema = "company_management")
public class WorkOn {
	
	
	@Id
//	@GeneratedValue(generator = "workon")
//	@GenericGenerator(name="workon" ,
//					  strategy = "com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator",
//					  parameters = {
//							  	@Parameter(name=AsecGenerator.TABLE_NAME,value = "workon"),
//							  	@Parameter(name=AsecGenerator.ID_NAME,value = "workon_id")
//					  })
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="workon_id")
	private int workOnId;


	@ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.REFRESH})
	@JoinColumn(name = "emp_id")
	private Employee employee;


	@ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.REFRESH})
	@JoinColumn(name = "proj_id")
	private Project project;


	public WorkOn(Employee employee, Project project) {

		this.employee = employee;
		this.project = project;

	}


	@Override
	public String toString() {
		
		return "WorkOn [workOnId=" + workOnId + ", employeeId="+employee.getEmpId()+", projectId="+project.getProjId()+"]";

	}


}
