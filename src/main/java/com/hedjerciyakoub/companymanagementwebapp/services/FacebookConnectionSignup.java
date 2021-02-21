package com.hedjerciyakoub.companymanagementwebapp.services;



import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import com.hedjerciyakoub.companymanagementwebapp.security.entitys.AppUser;
import com.hedjerciyakoub.companymanagementwebapp.security.services.AppUserServiceImpl;


@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    private AppUserServiceImpl UserServiceImpl;    
    
    @Override
    public String execute(Connection<?> connection) {
        AppUser user = new com.hedjerciyakoub.companymanagementwebapp.security.entitys.AppUser();
        user.setUsername(connection.getDisplayName());
        user.setPassword("");
        return user.getUsername();
    }
}
