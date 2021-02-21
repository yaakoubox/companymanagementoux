package com.hedjerciyakoub.companymanagementwebapp.security.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hedjerciyakoub.companymanagementwebapp.security.entitys.Authorities;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
	
	/*
	@Modifying
	@Query("delete from authorities a where a.username = ?1 and a.authority = ?2")
	public void deleteAuthorityByUsername(String username , String authority);
	*/
}
