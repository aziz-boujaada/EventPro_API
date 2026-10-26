package org.example.eventpro.mapper;

import org.example.eventpro.Domain.Administrator;
import org.example.eventpro.Domain.Client;
import org.example.eventpro.Domain.ControlAgent;
import org.example.eventpro.Domain.Organizer;
import org.example.eventpro.Domain.User;
import org.example.eventpro.dto.CreateUserRequestDto;
import org.example.eventpro.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toNewUser(CreateUserRequestDto request) {
        User user = switch (request.getRole()) {
            case ADMIN -> new Administrator();
            case CLIENT -> new Client();
            case ORGANIZER -> new Organizer();
            case CONTROL_AGENT -> new ControlAgent();
        };
        updateUser(user, request);
        user.setActive(true);
        return user;
    }

    public void updateUser(User user, CreateUserRequestDto request) {
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        if (user instanceof Client client && request.getPhone() != null) {
            client.setPhone(request.getPhone());
        }
        if (user instanceof Organizer organizer && request.getOrganisationName() != null) {
            organizer.setOrganisationName(request.getOrganisationName());
        }
    }

    public void updateExistingUser(User user, CreateUserRequestDto request) {
        updateUser(user, request);
    }

    public UserResponseDto toResponse(User user) {
        return new UserResponseDto(user);
    }
}
