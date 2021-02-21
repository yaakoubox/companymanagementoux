package com.hedjerciyakoub.companymanagementwebapp.security.services;

import com.hedjerciyakoub.companymanagementwebapp.security.dao.TokenDataRepository;
import com.hedjerciyakoub.companymanagementwebapp.security.entitys.TokenData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TokenDataServiceImpl {

    private final TokenDataRepository tokenDataRepository;

    public void save(TokenData tokenData) {
        tokenDataRepository.save(tokenData);
    }

    public Optional<TokenData> getToken(String token){
        return tokenDataRepository.findByToken(token);
    }

    public void setConfirmeTime(String token) {
        tokenDataRepository.updateConfirmTime(token, LocalDateTime.now());
    }


}
