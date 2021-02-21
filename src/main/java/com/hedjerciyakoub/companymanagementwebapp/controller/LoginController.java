package com.hedjerciyakoub.companymanagementwebapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	
	private static String authorizationRequestBaseUri = "oauth2/authorization";
    private Map<String, String> oauth2AuthenticationUrls= new HashMap<>();
    
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

	@GetMapping("/login")
	public String showLoginPage(Model model){
		
		Iterable<ClientRegistration> clientRegistrations = null;
		
		ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
		
		if(type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
			clientRegistrations = (Iterable<ClientRegistration>)clientRegistrationRepository;
		}


		for(ClientRegistration register : clientRegistrations) {
			oauth2AuthenticationUrls.put(register.getClientName(), authorizationRequestBaseUri+"/"+register.getRegistrationId());
		}
		model.addAttribute("urls",oauth2AuthenticationUrls);
		
		return "login";

	}


	@GetMapping("dontHaveAccess")
	public String showAccessDeniedPage() {

		return"acces-denied";
	}


}
