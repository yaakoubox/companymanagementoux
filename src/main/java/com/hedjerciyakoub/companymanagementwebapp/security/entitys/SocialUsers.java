package com.hedjerciyakoub.companymanagementwebapp.security.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "social_users" , schema = "company_management_security")
public class SocialUsers {
	
	
	@Id
//	@GeneratedValue(generator = "social_users")
//	@GenericGenerator(name="social_users" ,
//					  strategy = "com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator",
//					  parameters = {
//							  	@Parameter(name=AsecGenerator.TABLE_NAME,value = "social_users"),
//							  	@Parameter(name=AsecGenerator.ID_NAME,value = "user_id")
//					  })
	@Column(name = "user_id")
	String userId ;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "email")
	String email;

	@Column(name = "platform")
	String platform;


}
