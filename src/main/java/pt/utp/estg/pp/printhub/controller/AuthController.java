package pt.utp.estg.pp.printhub.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pt.utp.estg.pp.printhub.dto.RegisterRequest;
import pt.utp.estg.pp.printhub.dto.UserResponse;
import pt.utp.estg.pp.printhub.service.AuthService;

/** Endpoints de autenticação: registo (e login, na próxima tarefa). */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registo")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
