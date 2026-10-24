package pt.utp.estg.pp.printhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Dados que o cliente pode editar no seu perfil. O email e a palavra-passe não se alteram aqui. */
public record UpdateProfileRequest(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 20) String phone,
        @Size(max = 255) String address
) {
}