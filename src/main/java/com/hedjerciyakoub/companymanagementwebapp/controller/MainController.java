package com.hedjerciyakoub.companymanagementwebapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.hedjerciyakoub.companymanagementwebapp.security.entitys.SocialUsers;
import com.hedjerciyakoub.companymanagementwebapp.security.services.SocialUsersServiceImpl;

@Controller
@RequestMapping("/main")
public class MainController {


	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
	private final SocialUsersServiceImpl socialUsersServiceImpl;
	
	
	public MainController(SocialUsersServiceImpl socialUsersServiceImpl) {
		this.socialUsersServiceImpl = socialUsersServiceImpl;
	}


	@GetMapping("/adminstraionsOauth2")
	public String getMainPageWithOauth2(OAuth2AuthenticationToken authentication , @ModelAttribute("platFormName") String platFormName) {

		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
			    	        authentication.getAuthorizedClientRegistrationId(), 
			    	          authentication.getName()); 
		  String userInfoEndpointUri = client.getClientRegistration()
		            .getProviderDetails()
		            .getUserInfoEndpoint()
		            .getUri();
		  
		  if (!StringUtils.isEmpty(userInfoEndpointUri)) {
			    RestTemplate restTemplate = new RestTemplate();
			    HttpHeaders headers = new HttpHeaders();
			    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
			    HttpEntity entity = new HttpEntity("", headers);
			    ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
			    Map userAttributes = response.getBody();


			  	String email = (String)userAttributes.get("email");
			  	String name = (String)userAttributes.get("name");


			  	String id = "";
				String platform ="" ;
				if(userInfoEndpointUri.contains("facebook")){
					id = (String)userAttributes.get("id");
					platform = "facebook";
				}else if (userInfoEndpointUri.contains("google")){
					id = (String)userAttributes.get("sub");
					platform = "google";
				}else if (userInfoEndpointUri.contains("github")){
					id = userAttributes.get("id").toString();
					email = (String)userAttributes.get("html_url");
					name = (String)userAttributes.get("login");
					platform = "github";
				}


			    
			    
			    SocialUsers u = new SocialUsers(id,name, email , platform);
			    
			    try {
					if(!socialUsersServiceImpl.findByEmailAndName(email,name).isPresent()){
						socialUsersServiceImpl.save(u);
					}

				} catch (Exception e) {

				}

		  }
		
		return "main-Administration";

	}
	
	@GetMapping("/adminstraions")
	public String getMainPage() {

		return "main-Administration";


	}

	@GetMapping("/Privacy-Policy")
	public String getPolicyPage() {

		return "policy";


	}
	

}
