package com.hedjerciyakoub.companymanagementwebapp.entitys;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.hedjerciyakoub.companymanagementwebapp.validations.OuxAnnotation;
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
@Table(name="department")//,schema = "company_management")
public class Department {
	
	
	@Id
	@GeneratedValue(generator = "department")
	@GenericGenerator(name="department" ,
					  strategy = "com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator",
					  parameters = {
							  	@Parameter(name=AsecGenerator.TABLE_NAME,value = "department"),
							  	@Parameter(name=AsecGenerator.ID_NAME,value = "dept_id")
					  })
	@Column(name="dept_id")
	private int deptId;
	

//	@NotBlank(message = "This field is required" )
//	@Size(max=30 , message = "Maximum is 30 letters")
//	@Size(min=2, message = "minimum is 2 letters ")
//	@Pattern(regexp = "^[A-Za-z]+|^[A-Za-z]+ [A-Za-z]+" , message = "only letters / max is tow words" )
	@OuxAnnotation(notBlankNotNullNotEmpty = true ,notBlankNotNullNotEmptyMessage = "Name is required",
				   regex = "^[A-Za-z]+|^[A-Za-z]+ [A-Za-z]+" , regexMessage = "Only characters and maximum is tow words",
					minStringLength = 2 , maxStringLength = 30 , minMaxStringLengthMessage = "Characters number should be between 2 and 30"	)
	@Column(name = "dept_name")
	private String name;
	

//	@NotBlank(message = "This field is required" )
//	@Size(max=60 , message = "Maximum is 60 letters")
//	@Size(min=2, message = "minimum is 2 letters ")
	@OuxAnnotation(notBlankNotNullNotEmpty = true ,notBlankNotNullNotEmptyMessage = "Location is required",
					minStringLength = 2 , maxStringLength = 60 , minMaxStringLengthMessage = "Characters number should be between 2 and 60"	)
	@Column(name = "dept_location")
	private String location;


	@OneToMany(mappedBy = "department" , cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	private List<Employee> employees;


	@OneToMany(mappedBy = "department" , cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	private List<Project> projects;	


	public Department(String name, String location) {
		this.name = name;
		this.location = location;
	}


	@Override
	public String toString() {
		return "Department [deptId=" + deptId + ", deptName=" + name + ", location=" + location + "]";
	}
	

}
