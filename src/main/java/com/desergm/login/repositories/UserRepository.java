package com.desergm.login.repositories;

import com.desergm.login.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel,String> {
    UserModel findByEmail(String email);
}
