package com.hedjerciyakoub.companymanagementwebapp.security.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.hedjerciyakoub.companymanagementwebapp.security.entitys.TokenData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hedjerciyakoub.companymanagementwebapp.security.dao.AppUserRepository;
import com.hedjerciyakoub.companymanagementwebapp.security.entitys.Authorities;
import com.hedjerciyakoub.companymanagementwebapp.security.entitys.AppUser;

@AllArgsConstructor
@Service
public class AppUserServiceImpl implements UserDetailsService{

	private final AppUserRepository appUserRepository;
	private final AuthoritiesServiceImpl authoritiesServiceImpl;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final TokenDataServiceImpl tokenDataServiceImpl;



	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if(appUserRepository.findByEmail(email).isPresent()){
			return appUserRepository.findByEmail(email).get();
		}
		throw new UsernameNotFoundException(email+" dose not exist");
	}

	public String setTokenOfResetPasswordOfUser(AppUser user) {

		String token = createToken();
		TokenData tokenData = new TokenData(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(10));
		tokenData.setUser(user);
		tokenDataServiceImpl.save(tokenData);
		return token;

	}


	@Transactional(rollbackFor = Exception.class)
	public String signUp(AppUser user) throws Exception{


			boolean userExists = appUserRepository.findByEmail(user.getEmail()).isPresent();
			if (userExists) {
				throw new IllegalStateException("email already taken");
			}


			String password = user.getPassword();
			String passwordBCrypted = bCryptPasswordEncoder.encode(password);
			user.setPassword(passwordBCrypted);
			user.setEnabled(true);
			appUserRepository.save(user);


			List<Authorities> authorities = Arrays.asList(new Authorities("ROLE_EMPLOYEE"));
			Authorities employeeRole = authorities.get(0);
			employeeRole.setUser(user);
			authoritiesServiceImpl.save(employeeRole);



			String token = createToken();
			List<TokenData> tokensData = Arrays.asList(new TokenData(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10)));
			TokenData tokenData = tokensData.get(0);
			tokenData.setUser(user);
			tokenDataServiceImpl.save(tokenData);


		return token;
	}

	public String confirmToken(String token) throws IllegalStateException{
		TokenData tokenData = tokenDataServiceImpl.getToken(token).orElseThrow(()->new IllegalStateException("token not found"));

		LocalDateTime confirmTime = tokenData.getConfirmTime();

		if(confirmTime!=null){
			throw new IllegalStateException("email already confirmed");
		}

		LocalDateTime expireTime = tokenData.getExpireTime();

		if(expireTime.isBefore(LocalDateTime.now())){
			throw new IllegalStateException("token is expired");
		}

		tokenDataServiceImpl.setConfirmeTime(token);
		appUserRepository.enableAppUser(tokenData.getUser().getEmail());



		return tokenData.getUser().getRealUsername();
	}

	public boolean checkTokenPasswordResetAvailableAndNotConfirmed(String token) throws Exception{
		 TokenData tokenData = tokenDataServiceImpl.getToken(token).orElseThrow(()->new IllegalStateException("there is no token as "+token));

		 if(LocalDateTime.now().isBefore(tokenData.getExpireTime().plusMinutes(10)) && tokenData.getConfirmTime()==null){
		 	return true;
		 }
		 return false;
	}

	public Optional<AppUser> findByEmail(String email) {
		return appUserRepository.findByEmail(email);
	}

	public Optional<AppUser> findByUsername(String username){
		return appUserRepository.findByUsername(username);
	}

	private String createToken() {
		return UUID.randomUUID().toString();
	}


//	@Autowired
//	public AppUserServiceImpl(AppUserRepository appUserRepository, AuthoritiesServiceImpl authoritiesServiceImpl, BCryptPasswordEncoder bCryptPasswordEncoder) {
//		this.appUserRepository = appUserRepository;
//		this.authoritiesServiceImpl = authoritiesServiceImpl;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//	}

	public AppUser getUserByTokem(String token){
		TokenData tokenData = tokenDataServiceImpl.getToken(token).orElseThrow(()->new IllegalStateException("there is not token with this info"));
		AppUser user = tokenData.getUser();
		return user;
	}

	@Transactional(rollbackFor = Exception.class)
	public void resetPassword(AppUser user) throws Exception {

		AppUser userFromDatabase = appUserRepository.findByEmail(user.getEmail()).orElseThrow(()->new IllegalStateException("user not found"));

		int indexOflastToken = userFromDatabase.getTokensData().size()-1;

		String username = userFromDatabase.getRealUsername();
		user.setUsername(username);
		String password = user.getPassword();
		String passwordBCrypted = bCryptPasswordEncoder.encode(password);
		user.setPassword(passwordBCrypted);
		user.setEnabled(true);
		appUserRepository.save(user);
		tokenDataServiceImpl.setConfirmeTime(userFromDatabase.getTokensData().get(indexOflastToken).getToken());


	}
}
