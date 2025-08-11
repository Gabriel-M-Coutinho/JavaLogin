package com.desergm.login.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponseDto {
    private String message;
    private String status;
    private String userId;
}
