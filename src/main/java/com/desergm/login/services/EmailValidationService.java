package com.desergm.login.services;

import com.desergm.login.dtos.EmailValidationDto;
import com.desergm.login.models.EmailValidation;
import com.desergm.login.repositories.EmailValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailValidationService {

    @Autowired
    EmailValidationRepository emailValidationRepository;

    public void createEmailValidation(String email){
        EmailValidation emailValidation = new EmailValidation();
        emailValidation.setEmail(email);

        String token = UUID.randomUUID().toString();
        emailValidation.setToken(token);

        emailValidationRepository.save(emailValidation);
    }

    public Optional<EmailValidation> getEmailValidation(String email){
        return emailValidationRepository.findByEmail(email);
    }

    public void setEmailValidationUsed(String id){
        Optional<EmailValidation> opEmailValidation = emailValidationRepository.findById(id);
        EmailValidation emailValidation = opEmailValidation.get();
        emailValidation.setUsed(true);
        emailValidationRepository.save(emailValidation);
    }




}
