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
@Table(name = "employee_phones")
public class EmployeePhones {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(generator = "employee_phones")
//	@GenericGenerator(name="employee_phones" ,
//					  strategy = "com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator",
//					  parameters = {
//							  	@Parameter(name=AsecGenerator.TABLE_NAME,value = "employee_phones"),
//							  	@Parameter(name=AsecGenerator.ID_NAME,value = "phone_id")
//					  })
	@Column(name = "phone_id")
	private int phoneId;


	@Column(name = "phone")
	private String phone;


	@ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.REFRESH })
	@JoinColumn(name = "emp_id")
	private Employee employee; 


	public EmployeePhones(String phone) {
		this.phone = phone;
	}


	@Override
	public String toString() {
		return "EmployeePhones [phoneId=" + phoneId + ", phone=" + phone + ", employeeId = " + employee.getEmpId() + "]";
	}

	
}
