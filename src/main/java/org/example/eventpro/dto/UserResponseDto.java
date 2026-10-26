package org.example.eventpro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.example.eventpro.Domain.Client;
import org.example.eventpro.Domain.Organizer;
import org.example.eventpro.Domain.User;
import org.example.eventpro.Enums.UserRole;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private boolean active;
    private String phone;
    private String organisationName;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.active = user.isActive();

        if (user instanceof Client client) {
            this.phone = client.getPhone();
        } else if (user instanceof Organizer organizer) {
            this.organisationName = organizer.getOrganisationName();
        }

    }

}
