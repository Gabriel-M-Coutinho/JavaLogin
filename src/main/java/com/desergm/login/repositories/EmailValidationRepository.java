package com.desergm.login.repositories;

import com.desergm.login.models.EmailValidation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmailValidationRepository extends MongoRepository<EmailValidation,String > {
    Optional<EmailValidation> findByEmail(String email);
}
