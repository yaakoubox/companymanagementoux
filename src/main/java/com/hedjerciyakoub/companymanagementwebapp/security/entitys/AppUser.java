package com.hedjerciyakoub.companymanagementwebapp.security.entitys;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.hedjerciyakoub.companymanagementwebapp.validations.OuxAnnotation;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="users" )//, schema = "company_management_security")// table must be uses for spring security can know it
public class AppUser implements UserDetails{
	
	
	@Id
	@OuxAnnotation(notBlankNotNullNotEmpty = true, notBlankNotNullNotEmptyMessage = "Username is required",
			minStringLength = 2, maxStringLength = 30, minMaxStringLengthMessage="Characters number should be between 2 and 30",
			regex = "[A-Za-z0-9\\_\\-]+", regexMessage = "Only characters and _ - and maximum is one words")
	@Column(name="username")
	private String username;

	@OuxAnnotation(notBlankNotNullNotEmpty = true, notBlankNotNullNotEmptyMessage = "Email is required",
					regex = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", regexMessage = "Check Email format")
	@Column(name="email")
	private String email;
	
	@OuxAnnotation(notBlankNotNullNotEmpty = true, notBlankNotNullNotEmptyMessage = "Password is required",
			minStringLength = 6, maxStringLength = 200, minMaxStringLengthMessage="Characters number should be between 6 and 200")
	//@Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$\"" , message = "min 1 letter" ) not readdy for now
	@Column(name="password")
	private String password;
	
	@Column(name="enabled")
	private boolean enabled = false;
	
	@Column(name="locked")
	private boolean locked = false;	
	
	@OneToMany(mappedBy = "user" , cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.REFRESH , CascadeType.REMOVE})
	private List<Authorities> authorities;

	@OneToMany(mappedBy = "user" , cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.REFRESH , CascadeType.REMOVE})
	private List<TokenData> tokensData;
	
	

	
	public AppUser(String username, String password , String email ,List<Authorities> authorities ,List<TokenData> tokensData) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
		this.tokensData = tokensData;
	}




	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}




	@Override
	public String getPassword() {
		return password;
	}




	@Override
	public String getUsername() {
		return email;
	}




	@Override
	public boolean isAccountNonExpired() {
		return true;
	}




	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}




	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}




	@Override
	public boolean isEnabled() {
		return enabled;
	}


	public String getRealUsername(){
		return username;
	}
	public void setRealUsername(String username){
		this.username = username;
	}
	


	
	
	
	
	
	
}
