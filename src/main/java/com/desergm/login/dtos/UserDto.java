package com.desergm.login.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Schema(description = "Email de para cadastro", example = "exemple@gmail.com")
    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    @Schema(description = "Senha de no mínimo 6 caracteres", example = "rodkwl@!Z")
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;
    @Schema(description = "Nome", example = "rogerinho")
    private String name;
}