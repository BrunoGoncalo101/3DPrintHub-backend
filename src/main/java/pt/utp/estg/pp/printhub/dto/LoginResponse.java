package pt.utp.estg.pp.printhub.dto;

/** O que a API devolve no login: o token e os dados do utilizador. */
public record LoginResponse(
        String token,
        String tokenType,
        long expiresIn,
        UserResponse user
) {
}
