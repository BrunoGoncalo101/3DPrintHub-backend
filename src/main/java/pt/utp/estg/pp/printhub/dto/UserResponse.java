package pt.utp.estg.pp.printhub.dto;

import pt.utp.estg.pp.printhub.entity.User;
import pt.utp.estg.pp.printhub.model.Role;

import java.time.LocalDateTime;

public record UserResponse(Integer id, String name, String email, Role role,
                           String phone, LocalDateTime createdAt) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(),
                user.getRole(), user.getPhone(), user.getCreatedAt());
    }
}
