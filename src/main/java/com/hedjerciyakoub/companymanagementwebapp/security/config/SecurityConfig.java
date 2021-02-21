
	
	/*
	
	@Autowired
	private FacebookConnectionSignup facebookConnectionSignup ;
	
	@Value("${spring.social.facebook.appSecret}")
    String appSecret;
    
    @Value("${spring.social.facebook.appId}")
    String appId;
	
    
    private ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry connectionFactoryRegistry = new ConnectionFactoryRegistry();
		connectionFactoryRegistry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));
		return connectionFactoryRegistry;
	}
	
	private UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
	}
	

	@Bean
	public ProviderSignInController providerSignInController() {
		
		ConnectionFactoryLocator connectionFactoryLocator = connectionFactoryLocator();
		
		UsersConnectionRepository usersConnectionRepository = getUsersConnectionRepository(connectionFactoryLocator);
		
		((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);
		
		
		
		return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new FacebookSignInAdapter());
		
	}
	
   	*/

package com.hedjerciyakoub.companymanagementwebapp.security.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.hedjerciyakoub.companymanagementwebapp.security.services.AppUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

 

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {


	@Resource
	UserDetailsService userDetailsService;

	@Autowired
	@Qualifier("securityDataSource")// securityDataSource is name of method on the configuration class which cqre qbout secerity database connection
	private DataSource securityDataSource;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AppUserServiceImpl appUserServiceImpl;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(daoAuthenticationProvider());
		auth.jdbcAuthentication().dataSource(securityDataSource);

	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(appUserServiceImpl);
		return provider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/employee/add").hasAnyRole("MANAGER","ADMIN")
			.antMatchers("/employee/save*").hasAnyRole("MANAGER","ADMIN")
			.antMatchers("/employee/delete").hasAnyRole("MANAGER","ADMIN")
			.antMatchers("/employee/delete").hasRole("ADMIN")
			.antMatchers("/employee/**").hasAnyRole("EMPLOYEE","USER")
			.antMatchers("/department/add").hasRole("ADMIN")
			.antMatchers("/department/save*").hasRole("ADMIN")
			.antMatchers("/department/update").hasRole("ADMIN")
			.antMatchers("/department/delete").hasRole("ADMIN")
			.antMatchers("/department/**").hasAnyRole("EMPLOYEE","USER")
			.antMatchers("/project/add").hasAnyRole("MANAGER","ADMIN")
			.antMatchers("/project/update").hasRole("ADMIN")
			.antMatchers("/project/save*").hasAnyRole("ADMIN","MANAGER")
			.antMatchers("/project/delete").hasRole("ADMIN")
			.antMatchers("/project/**").hasAnyRole("EMPLOYEE","USER")
			.antMatchers("/resources/**").hasAnyRole("EMPLOYEE","USER")
			.and()
			.oauth2Login()
				.loginPage("/login")
				.defaultSuccessUrl("/main/adminstraionsOauth2")
				.failureUrl("/login")
			.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/authenticateTheUser")
				.defaultSuccessUrl("/main/adminstraions")
				.permitAll()
			.and()
			.logout()
			.permitAll()
			.and()
			.rememberMe().tokenValiditySeconds(7200);


	}
	
		
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest>  authorizationRequestRepository() {
	 
	    return new HttpSessionOAuth2AuthorizationRequestRepository();
	}
    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        return accessTokenResponseClient;
    }
	
	
    

}
