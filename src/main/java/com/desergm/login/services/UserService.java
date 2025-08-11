package com.desergm.login.services;

import com.desergm.login.dtos.UserDto;
import com.desergm.login.models.UserModel;
import com.desergm.login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void addUserorUpdate(UserModel user){

        userRepository.save(user);
    }

    public UserModel getUserByEmail(String email){
        return userRepository.findByEmail(email.toLowerCase());
    }


    public UserModel getUserById(String id){
        Optional<UserModel> user0 =  userRepository.findById(id);

        if(user0.isEmpty()){
            throw new Error("id enviado não existe");
        }
        return user0.get();

    }


    public boolean userExist(String email){
        UserModel user = userRepository.findByEmail(email);;
        return user != null;
    }



    public UserModel ParseDtoToUser(UserDto userDto){

        if(this.userExist(userDto.getEmail().toLowerCase())) throw new Error("usuario já está cadastrado no sistema");
        else {
            String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());

            UserModel user = new UserModel();
            user.setName(userDto.getName());
            user.setPassword(encryptedPassword);
            user.setEmail(userDto.getEmail().toLowerCase());

            this.addUserorUpdate(user);
            return  user;
        }

    }
}
