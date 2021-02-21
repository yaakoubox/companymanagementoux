package com.hedjerciyakoub.companymanagementwebapp.security.dao;

public interface EmailSender {
    void send(String to , String username , String link);
}
