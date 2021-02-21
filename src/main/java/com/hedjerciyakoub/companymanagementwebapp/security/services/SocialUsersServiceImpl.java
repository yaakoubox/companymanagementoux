package com.hedjerciyakoub.companymanagementwebapp.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hedjerciyakoub.companymanagementwebapp.security.dao.SocialUsersRepository;
import com.hedjerciyakoub.companymanagementwebapp.security.entitys.SocialUsers;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SocialUsersServiceImpl {
	
	SocialUsersRepository socialUsersRepository;

	@Autowired
	public SocialUsersServiceImpl(SocialUsersRepository socialUsersRepository) {
		this.socialUsersRepository = socialUsersRepository;
	}
	
	
	public void save(SocialUsers socialUser) throws Exception{
		
		socialUsersRepository.save(socialUser);
	}

	public SocialUsers findById(String userId){
		try {
			return socialUsersRepository.findById(userId).get();//orElseThrow(()->new NoSuchElementException("there is no social user as "+userId));
		}catch (Exception e){
			return null;
		}

	}

	public Optional<SocialUsers> findByEmailAndName(String email , String name){
		return socialUsersRepository.findByEmailAndName(email,name);
	}

	public void setPrincipalUsername(Model model ,Principal principal){
		String principalUsername = principal.getName();

		SocialUsers userSocial = findById(principalUsername);
		if(userSocial!=null){
			principalUsername = userSocial.getName();

		}


		model.addAttribute("principalUsername", principalUsername);
	}

	

}
