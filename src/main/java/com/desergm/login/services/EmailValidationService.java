package com.desergm.login.services;

import com.desergm.login.dtos.EmailValidationDto;
import com.desergm.login.dtos.VerifyAcountDto;
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
    @Autowired
    RabbitMqService rabbitMqService;

    public void createEmailValidation(String email) {
        // Busca token ativo para o email (não usado ainda)
        Optional<EmailValidation> existing = emailValidationRepository.findByEmail(email)
                .filter(ev -> !ev.isUsed());

        if (existing.isPresent()) {
            // Token já existe e está ativo, pode reenviar o mesmo token ou abortar
            VerifyAcountDto dto = new VerifyAcountDto(email, existing.get().getToken());
            rabbitMqService.sendDto("queue-email-verify", dto);
            return;
        }

        // Caso não tenha token válido, cria novo
        EmailValidation emailValidation = new EmailValidation();
        emailValidation.setEmail(email);

        String token = UUID.randomUUID().toString();
        emailValidation.setToken(token);
        emailValidationRepository.save(emailValidation);

        VerifyAcountDto verifyAcountDto = new VerifyAcountDto(email, token);
        rabbitMqService.sendDto("queue-email-verify", verifyAcountDto);
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
