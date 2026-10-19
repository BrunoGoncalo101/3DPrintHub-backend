package pt.utp.estg.pp.printhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** O que o front envia para iniciar sessão. */
public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
