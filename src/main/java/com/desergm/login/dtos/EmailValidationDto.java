package com.desergm.login.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
public class EmailValidationDto {

    @Email
    @NotBlank
    public String email;
    @NotBlank
    @NotNull
    public String token;

}
