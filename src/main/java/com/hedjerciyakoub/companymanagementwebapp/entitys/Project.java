package com.hedjerciyakoub.companymanagementwebapp.entitys;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.hedjerciyakoub.companymanagementwebapp.validations.OuxAnnotation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="project")//,schema = "company_management")
public class Project {
	
	
	@Id
	@GeneratedValue(generator = "project")
	@GenericGenerator(name="project" ,
					  strategy = "com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator",
					  parameters = {
							  	@Parameter(name=AsecGenerator.TABLE_NAME,value = "project"),
							  	@Parameter(name=AsecGenerator.ID_NAME,value = "proj_id")
					  })
	@Column(name = "proj_id")
	private int projId;


//	@NotBlank(message = "This field is required" )
//	@Size(max=30 , message = "Maximum is 30 letters")
//	@Size(min=2, message = "minimum is 2 letters ")
//	@Pattern(regexp = "^[A-Za-z]+|^[A-Za-z]+ [A-Za-z]+" , message = "only letters / max is tow words" )
	@OuxAnnotation(notBlankNotNullNotEmpty = true ,notBlankNotNullNotEmptyMessage = "Name is required",
				   regex = "^[A-Za-z]+|^[A-Za-z]+ [A-Za-z]+" , regexMessage = "Only characters and maximum is tow words",
		  		   minStringLength = 2 , maxStringLength = 30 , minMaxStringLengthMessage = "Characters number should be between 2 and 30"	)
	@Column(name = "proj_name")
	private String name ;


//	@NotBlank(message = "This field is required" )
//	@Size(max=60 , message = "Maximum is 60 letters")
//	@Size(min=2, message = "minimum is 2 letters ")
	@OuxAnnotation(notBlankNotNullNotEmpty = true ,notBlankNotNullNotEmptyMessage = "Location is required",
			       minStringLength = 2 , maxStringLength = 60 , minMaxStringLengthMessage = "Characters number should be between 2 and 60"	)
	@Column(name = "proj_location")
	private String location ;

	
	@NotNull(message = "Department is required")
	@ManyToOne
	@JoinColumn(name="dept_id")
	private Department department;


	@OneToMany(mappedBy = "project", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE })
	private List<WorkOn> WorkOns;


	public Project(String name, String location, Department department) {
		this.name = name;
		this.location = location;
		this.department = department;
	}


	@Override
	public String toString() {
		return "Project [projectId=" + projId + ", name=" + name + ", location=" + location + ", deptId=" + department.getDeptId()
				+ "]";
	}

	
}
