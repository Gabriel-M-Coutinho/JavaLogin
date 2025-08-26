package com.desergm.login.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyAcountDto {

    @NonNull
    private String email;
    @NonNull
    private String token;

}
