package com.g96.ftms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;


import java.util.Date;
import java.util.Set;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;

    @NotEmpty(message = "Full name is required")
    private String fullName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String imgAva;

    @NotEmpty(message = "Phone number is required")
    private String phone;

    private String emergencyPhone;

    private Date dateOfBirth;

    private Date createdDate;

    private String address;

    @NotEmpty(message = "Account is required")
    private String account;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotNull(message = "Roles are required")
    private Set<String> roles;

    private Boolean status;
}
