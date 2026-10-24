package pt.utp.estg.pp.printhub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.utp.estg.pp.printhub.dto.UpdateProfileRequest;
import pt.utp.estg.pp.printhub.dto.UserResponse;
import pt.utp.estg.pp.printhub.entity.User;
import pt.utp.estg.pp.printhub.exception.InvalidCredentialsException;
import pt.utp.estg.pp.printhub.repository.UserRepository;

/** Dados e perfil do utilizador com sessão iniciada (RF-003). */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(Integer userId) {
        return UserResponse.from(findUser(userId));
    }

    @Transactional
    public UserResponse updateProfile(Integer userId, UpdateProfileRequest request) {
        User user = findUser(userId);
        user.setName(request.name().trim());
        user.setPhone(blankToNull(request.phone()));
        user.setAddress(blankToNull(request.address()));
        return UserResponse.from(user);
    }

    private User findUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new InvalidCredentialsException("Sessão inválida."));
    }

    /** Guarda campos opcionais vazios como null, em vez de texto em branco. */
    private static String blankToNull(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }
}