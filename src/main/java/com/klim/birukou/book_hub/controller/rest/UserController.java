package com.klim.birukou.book_hub.controller.rest;


import com.klim.birukou.book_hub.controller.dto.user.UpdateUserDataDto;
import com.klim.birukou.book_hub.controller.dto.user.UserSignInDto;
import com.klim.birukou.book_hub.controller.dto.user.UserSignUpDto;
import com.klim.birukou.book_hub.domain.ums.User;
import com.klim.birukou.book_hub.domain.ums.UserData;
import com.klim.birukou.book_hub.service.ums.UserClientService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserClientService userClientService;

    @PostMapping("/sign/up")
    public ResponseEntity<@NonNull UUID> signUp(
        @Valid @RequestBody UserSignUpDto dto
    ) {
        var uid = userClientService.signUp(dto);
        return new ResponseEntity<>(uid, HttpStatus.CREATED);
    }

    @PostMapping("/sign/in")
    public ResponseEntity<@NonNull User> signIn(
        @Valid @RequestBody UserSignInDto dto
    ) {
        var user = userClientService.signIn(dto);
        return  ResponseEntity.ok(user);
    }

    @PatchMapping("/data")
    public ResponseEntity<@NonNull UserData> updateUserData(
        @Valid @RequestBody UpdateUserDataDto dto
    ) {
        var userUpdatedData = userClientService.updateUserData(dto);
        return ResponseEntity.ok(userUpdatedData);
    }

    @GetMapping("/data/{uid}")
    public ResponseEntity<@NonNull UserData> getUserData(
        @PathVariable UUID uid
    ) {
        var userData = userClientService.getUserData(uid);
        return ResponseEntity.ok(userData);
    }
}
