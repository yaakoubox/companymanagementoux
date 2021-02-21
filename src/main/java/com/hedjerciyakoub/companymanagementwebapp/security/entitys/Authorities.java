package com.hedjerciyakoub.companymanagementwebapp.security.entitys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator;

@Entity
@Table(name = "authorities" , schema = "company_management_security")
public class Authorities {
	
	
	@Id
	@GeneratedValue(generator = "authorities")
	@GenericGenerator(name="authorities" ,
					  strategy = "com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator",
					  parameters = {
							  	@Parameter(name=AsecGenerator.TABLE_NAME,value = "authorities"),
							  	@Parameter(name=AsecGenerator.ID_NAME,value = "auth_id")
					  })
	@Column(name="auth_id")
	private int authId;
	
	@Column(name="authority")
	private String authority;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="username")
	private AppUser user;

	
	public Authorities() {
		
	}
	

	public Authorities(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}


	public AppUser getUser() {
		return user;
	}


	public void setUser(AppUser user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "Authorities [auth_id=" + authId + ", authority=" + authority + ", username=" + user.getUsername() + "]";
	}



	
	
	
}
