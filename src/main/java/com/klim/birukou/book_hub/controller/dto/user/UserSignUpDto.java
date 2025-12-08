package com.klim.birukou.book_hub.controller.dto.user;

import com.klim.birukou.book_hub.controller.dto.user.validator.MinAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserSignUpDto(

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must be at most 255 characters")
    String email,

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Z].*", message = "Name must start with a capital letter")
    String name,

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    String password,

    @NotBlank(message = "First name must not be blank")
    @Size(min = 2, max = 100, message = "First name must be between 1 and 100 characters")
    @Pattern(regexp = "^[A-Z].*", message = "First name must start with a capital letter")
    String firstName,

    @NotBlank(message = "Last name must not be blank")
    @Size(min = 2, max = 100, message = "Last name must be between 1 and 100 characters")
    @Pattern(regexp = "^[A-Z].*", message = "Last name must start with a capital letter")
    String lastName,

    @Past(message = "Birthdate must be in the past")
    @MinAge(value = 6, message = "User must be at least 6 years old")
    LocalDate birthdate
) {
}
