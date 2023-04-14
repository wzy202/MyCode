package com.wzy.studyprojectbackend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface AuthorizeService extends UserDetailsService {
    default boolean sendValidateEmail(String email, String sessionId){
        return false;
    }
}