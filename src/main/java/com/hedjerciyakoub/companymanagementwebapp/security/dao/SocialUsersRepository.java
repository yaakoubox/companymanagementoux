package com.hedjerciyakoub.companymanagementwebapp.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hedjerciyakoub.companymanagementwebapp.security.entitys.SocialUsers;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface SocialUsersRepository extends JpaRepository<SocialUsers, String> {


    Optional<SocialUsers> findByEmailAndName(String email , String name);
}
