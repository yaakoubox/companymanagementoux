package com.hedjerciyakoub.companymanagementwebapp.security.controller;

import javax.validation.Valid;

import com.hedjerciyakoub.companymanagementwebapp.security.dao.EmailSender;
import com.hedjerciyakoub.companymanagementwebapp.security.services.AuthoritiesServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hedjerciyakoub.companymanagementwebapp.security.entitys.AppUser;
import com.hedjerciyakoub.companymanagementwebapp.security.services.AppUserServiceImpl;

import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class SecurityController {


	private final AppUserServiceImpl appUserServiceImpl;
	private final EmailSender emailSender;
	private final AuthoritiesServiceImpl authoritiesServiceImpl;


	//private final BCryptPasswordEncoder bCryptPasswordEncoder;
	


	@GetMapping("/CreateAccount")
	public String createUser(Model model) {

		AppUser user = new AppUser();

		model.addAttribute("user", user);

		return "security-folder/user-add-form";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult,@ModelAttribute("confirmPassword")String confirmPassword, Model model) {

		boolean passwordMatch = confirmPassword.equals(user.getPassword());

		boolean emailExist = appUserServiceImpl.findByEmail(user.getEmail()).isPresent();

		boolean usernameExsit = appUserServiceImpl.findByUsername(user.getUsername()).isPresent();

		if (bindingResult.hasErrors()||!passwordMatch||usernameExsit||emailExist) {

			model.addAttribute("passwordMatch", passwordMatch);
			model.addAttribute("emailExist", emailExist);
			model.addAttribute("usernameExsit",usernameExsit);
			return "security-folder/user-add-form";

		} else {

			try{

				String token = appUserServiceImpl.signUp(user);
				String linkOfConfirmation = "https://www.companymanagementoux.com/user/confirm?token="+token ;

				emailSender.send(user.getEmail(),user.getRealUsername(),linkOfConfirmation);


			} catch (Exception e) {
				e.printStackTrace();
			}

			return "email-registration";

		}

	}

	@GetMapping("/confirm")
	public String confirm(@RequestParam("token") String token , Model model)  {
		try {
			String username = appUserServiceImpl.confirmToken(token);
			authoritiesServiceImpl.setUserAsAdmin(username);
		}catch (IllegalStateException e){

			if(e.getMessage().equals("token not found")){
				model.addAttribute("errorNumber",404);
				return"error";
			}else if(e.getMessage().equals("token is expired")){
				model.addAttribute("tokenStatus",false);
				model.addAttribute("description","The link already expired");
				model.addAttribute("errorNumber",404);
				return"error";
			}else if(e.getMessage().equals("email already confirmed")){
				model.addAttribute("tokenStatus",false);
				model.addAttribute("description","Your account already confirmed");
				model.addAttribute("errorNumber",404);
				return"error";
			}

		}


		return "confirm";
	}


	@GetMapping("/resetepassword")
	public String resetePassword(@RequestParam("token") String token , Model model)  {

		try {
			if(appUserServiceImpl.checkTokenPasswordResetAvailableAndNotConfirmed(token)){
				AppUser user = appUserServiceImpl.getUserByTokem(token);
				model.addAttribute("user" ,user);
				model.addAttribute("passwordMatched" ,true);
				model.addAttribute("token",token);

				return "change-password";

			}


		}catch (Exception e){
			System.out.println(e.getMessage());
		}

		model.addAttribute("tokenStatus",false);
		model.addAttribute("description","The link already expired");
		model.addAttribute("errorNumber",404);
		return"error";



	}





	@GetMapping("retrivePassword")
	public String retrivePassword(){
		return "retrieve-password";
	}



	@PostMapping("/setNewPassword")
	public String setNewPassword(@ModelAttribute("email")String email , Model model){
		AppUser user = null;
		try {
			user = appUserServiceImpl.findByEmail(email).get();
		}catch (NoSuchElementException e){
			System.out.println(e.getMessage());
		}


		if(user!=null){
			String token = appUserServiceImpl.setTokenOfResetPasswordOfUser(user);

			String linkOfPasswordReseet = "http://localhost:8080/user/resetepassword?token="+token ;
			emailSender.send(email,user.getUsername() ,linkOfPasswordReseet);
			model.addAttribute("confirmation","Check your email for reset the password");
			model.addAttribute("divId","confirmationdiv");
		}else{
			model.addAttribute("email",email);
			model.addAttribute("confirmation","Invalid Email , Try again .");
			model.addAttribute("divId","invaliddiv");
		}
		return "retrieve-password";
	}

	@PostMapping("/changePassword")
	public String changePassword(@ModelAttribute("user") AppUser user ,@ModelAttribute("confirmPassword") String confirmPassword ,@ModelAttribute("token") String token , Model model){

		boolean passwordMatch = confirmPassword.equals(user.getPassword());
		if(!passwordMatch){
			user = appUserServiceImpl.getUserByTokem(token);
			model.addAttribute("passwordMatched",passwordMatch);
			model.addAttribute("user",user);
			return "change-password";
		}else{

		try {
			appUserServiceImpl.resetPassword(user);
		}catch (Exception e){
			e.printStackTrace();
		}

			return "password-reset-success";
		}

	}



}
