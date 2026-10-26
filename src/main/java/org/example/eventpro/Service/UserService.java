package org.example.eventpro.Service;

import org.example.eventpro.Domain.User;
import org.example.eventpro.Enums.UserRole;
import org.example.eventpro.Exception.ResourceNotFoundException;
import org.example.eventpro.Repository.UserRepository;
import org.example.eventpro.dto.CreateUserRequestDto;
import org.example.eventpro.dto.UserResponseDto;
import org.example.eventpro.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDto create(CreateUserRequestDto request) {
        validateRoleFields(request);
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A user with this email already exists");
        }
        return userMapper.toResponse(userRepository.save(userMapper.toNewUser(request)));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        return userMapper.toResponse(getUser(id));
    }

    public UserResponseDto update(Long id, CreateUserRequestDto request) {
        User user = getUser(id);
        if (user.getRole() != request.getRole()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A user's role cannot be changed");
        }
        validateRoleFields(request);
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A user with this email already exists");
        }

        userMapper.updateExistingUser(user, request);
        return userMapper.toResponse(userRepository.save(user));
    }

    public void delete(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private void validateRoleFields(CreateUserRequestDto request) {
        if (request.getRole() == UserRole.CLIENT && (request.getPhone() == null || request.getPhone().isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone is required for a client");
        }
        if (request.getRole() == UserRole.ORGANIZER
                && (request.getOrganisationName() == null || request.getOrganisationName().isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Organisation name is required for an organizer");
        }
    }
}
