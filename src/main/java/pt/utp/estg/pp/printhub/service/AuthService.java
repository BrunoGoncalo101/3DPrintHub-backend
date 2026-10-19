package pt.utp.estg.pp.printhub.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.utp.estg.pp.printhub.dto.LoginRequest;
import pt.utp.estg.pp.printhub.dto.LoginResponse;
import pt.utp.estg.pp.printhub.dto.RegisterRequest;
import pt.utp.estg.pp.printhub.dto.UserResponse;
import pt.utp.estg.pp.printhub.entity.User;
import pt.utp.estg.pp.printhub.exception.EmailAlreadyExistsException;
import pt.utp.estg.pp.printhub.exception.InvalidCredentialsException;
import pt.utp.estg.pp.printhub.model.Role;
import pt.utp.estg.pp.printhub.repository.UserRepository;

import java.time.LocalDateTime;

/** Registo, login e dados do utilizador com sessão iniciada. */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        user.setRole(Role.CUSTOMER);
        user.setActive(true);
        user.setTermsAcceptedAt(now);
        user.setCreatedAt(now);

        return UserResponse.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(request.password(), u.getPasswordHash()))
                .orElseThrow(InvalidCredentialsException::new);

        String token = tokenService.generateToken(user);
        return new LoginResponse(token, "Bearer", tokenService.getExpirationSeconds(), UserResponse.from(user));
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(Integer userId) {
        return userRepository.findById(userId)
                .map(UserResponse::from)
                .orElseThrow(() -> new InvalidCredentialsException("Sessão inválida."));
    }
}
