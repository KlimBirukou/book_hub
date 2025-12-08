package com.klim.birukou.book_hub.controller.dto.user;

import com.klim.birukou.book_hub.controller.dto.user.validator.MinAge;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateUserDataDto(

    @NotNull(message = "User UID must not be null")
    UUID userUid,

    @Size(min = 2, max = 100, message = "First name must be between 1 and 100 characters")
    @Pattern(regexp = "^[A-Z].*", message = "First name must start with a capital letter")
    String firstName,

    @Size(min = 2, max = 100, message = "Last name must be between 1 and 100 characters")
    @Pattern(regexp = "^[A-Z].*", message = "Last name must start with a capital letter")
    String lastName,

    @Past(message = "Birthdate must be in the past")
    @MinAge(value = 6, message = "User must be at least 6 years old")
    LocalDate birthdate
) {
}
