package com.hedjerciyakoub.companymanagementwebapp.security.dao;

import com.hedjerciyakoub.companymanagementwebapp.security.entitys.TokenData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenDataRepository extends JpaRepository<TokenData, Integer> {

    Optional<TokenData> findByToken(String token);


    @Transactional
    @Modifying
    @Query("UPDATE TokenData c SET c.confirmTime = ?2 WHERE c.token = ?1")
    int updateConfirmTime(String token,LocalDateTime confirmedAt);
}
