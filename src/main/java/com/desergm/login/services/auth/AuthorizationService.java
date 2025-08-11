package com.desergm.login.services.auth;


import com.desergm.login.models.EmailValidation;
import com.desergm.login.repositories.EmailValidationRepository;
import com.desergm.login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    EmailValidationRepository emailValidationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }


    public Optional<EmailValidation> getEmailValidationById(String email){
        return emailValidationRepository.findByEmail(email.toLowerCase());
    }


    public void createEmailValidation(String email){
        EmailValidation emailValidation = new EmailValidation();
        emailValidation.setEmail(email.toLowerCase());
        emailValidation.token = java.util.UUID.randomUUID().toString();
        emailValidationRepository.save(emailValidation);
    }
}