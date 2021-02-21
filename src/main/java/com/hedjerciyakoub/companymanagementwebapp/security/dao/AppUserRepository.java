package com.hedjerciyakoub.companymanagementwebapp.security.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Optionals;

import com.hedjerciyakoub.companymanagementwebapp.security.entitys.AppUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
	public void deleteByUsername(String username);
	Optional<AppUser> findByUsername(String username);
	Optional<AppUser> findByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE AppUser a SET a.enabled = TRUE WHERE a.email = ?1")
	int enableAppUser(String email);
}
