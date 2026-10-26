package org.example.eventpro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.eventpro.Enums.UserRole;

@Getter
@Setter
public class CreateUserRequestDto {
    @NotBlank(message = "le nom est obligatoire")
    private String name;

    @NotBlank(message = "email est obligatoire ")
    @Email(message = "email est invalid")
    private String email;

    @Pattern(regexp = "^[0-9]{8,15}$" , message = "Le numéro doit contenir entre 8 et 15 chiffres")
    private String phone ;

    @NotBlank(message = "mot de pass est obligatoire ")
    @Size(min =  6 , message = "la tail de mot de pass doit etre 6 character minimun")
    private String password;

    @NotNull(message = "role is required")
    private UserRole role;

    private String organisationName;

}


