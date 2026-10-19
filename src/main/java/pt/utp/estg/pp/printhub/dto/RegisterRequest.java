package pt.utp.estg.pp.printhub.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Email @Size(max = 150) String email,
        @NotBlank @Size(min = 8, max = 72) String password,
        @Size(max = 20) String phone,
        @AssertTrue(message = "Os termos têm de ser aceites") boolean acceptedTerms
) {
}
