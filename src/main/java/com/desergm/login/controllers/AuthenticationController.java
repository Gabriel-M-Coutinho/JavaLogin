package com.desergm.login.controllers;

import com.desergm.login.dtos.EmailValidationDto;
import com.desergm.login.dtos.RegisterResponseDto;
import com.desergm.login.dtos.UserDto;
import com.desergm.login.models.UserModel;
import com.desergm.login.services.EmailValidationService;
import com.desergm.login.services.RabbitMqService;
import com.desergm.login.services.auth.TokenService;
import com.desergm.login.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RabbitMqService rabbitMqService;
    @Autowired
    private EmailValidationService emailValidationService;

    /*
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(),data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        NewResponseDto responseDto = new NewResponseDto(
                "Login Realizado com sucesso",
                "OK",
                token
        );

        return ResponseEntity.ok().body(responseDto);
    }*/


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody UserDto userDto) {
        RegisterResponseDto response = new RegisterResponseDto();
        UserModel existingUser = userService.getUserByEmail(userDto.getEmail().toLowerCase());

        if (existingUser == null) {
            // Cadastra novo usuário
            UserModel newUser = userService.ParseDtoToUser(userDto);
            response.setMessage("Verifique seu e-mail para ativar sua conta");
            response.setStatus("OK");
            response.setUserId(newUser.getId());

            emailValidationService.createEmailValidation(userDto.getEmail());

            // Aqui referencie o envio do e-mail de verificação
            // rabbitMqService.sendDto("queue-email-verify", newUser);
            return ResponseEntity.accepted().body(response);
        } else if (!existingUser.isEmailVerified()) {
            // Usuário existe, mas não está verificado
            response.setMessage("E-mail já cadastrado, mas não verificado. Verifique seu e-mail.");
            response.setStatus("ERROR");
            response.setUserId(existingUser.getId());
            emailValidationService.createEmailValidation(userDto.getEmail());
            // Aqui referencie o envio do e-mail de verificação
            // rabbitMqService.sendDto("queue-email-verify", existingUser);
            return ResponseEntity.badRequest().body(response);
        } else {
            // Usuário já cadastrado e verificado
            response.setMessage("Usuário já cadastrado");
            response.setStatus("ERROR");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /*@GetMapping("/token/{token}")
    public ResponseEntity<NewResponseDto> verifyToken(@PathVariable String token){
        var validatetoken = tokenService.validateToken(token);
        if(validatetoken == ""){
            throw new RuntimeException("TokenExpirado");
        }
        if(validatetoken != null|| validatetoken != ""){
            NewResponseDto responseDto1 = new NewResponseDto("TokenExpirado","OK",validatetoken);
            return ResponseEntity.ok().body(responseDto1);
        }
        throw new RuntimeException("Erro ao verificar o Token");

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<NewResponseDto> forgotPass(@RequestBody ForgotPassDto forgotPassDto){
        User user = userService.getUserByEmail(forgotPassDto.email());
        if(user == null) {
            throw  new RuntimeException("o email informado não é cadastrado no sistema");
        }
        else {
            Email email = emailService.sendRecoveryPass(forgotPassDto.email().toLowerCase());
            emailService.sendEmail(email);
            NewResponseDto responseDto =    new NewResponseDto("email enviado","OK",null);
            return ResponseEntity.ok().body(responseDto);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<NewResponseDto> resetPassword(@RequestBody UpdatePasswordDto updatePasswordDto){

        User user =  recoveryTokenService.getUserWithToken(updatePasswordDto.token());
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(updatePasswordDto.password());
        user.setPassword(encryptedPassword);
        userService.addUserorUpdate(user);
        NewResponseDto responseDto = new NewResponseDto("Senha atualizada.","OK",null);
        return ResponseEntity.ok().body(responseDto);
    }
    */



}