package com.hedjerciyakoub.companymanagementwebapp.security.services;

import com.hedjerciyakoub.companymanagementwebapp.security.dao.AppUserRepository;
import com.hedjerciyakoub.companymanagementwebapp.security.entitys.AppUser;
import com.hedjerciyakoub.companymanagementwebapp.security.entitys.TokenData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hedjerciyakoub.companymanagementwebapp.security.dao.AuthoritiesRepository;
import com.hedjerciyakoub.companymanagementwebapp.security.entitys.Authorities;

@Service
@AllArgsConstructor
public class AuthoritiesServiceImpl {

	private final AuthoritiesRepository authoritiesRepository;
	private final AppUserRepository appUserServiceImpl;


	
	public void save(Authorities authorities) {
		authoritiesRepository.save(authorities);
	}

	public void setUserAsAdmin(String username){

			Authorities authoritie = new Authorities("ROLE_ADMIN");
			authoritie.setUser(appUserServiceImpl.findByUsername(username).get());
			authoritiesRepository.save(authoritie);


	}


	/*
	public void deleteByAuthorityWithUsernameCondetion(String username, String authority) {
		authoritiesRepository.deleteAuthorityByUsername(username, authority);
	}
	*/
	
	
	
	
	
}
