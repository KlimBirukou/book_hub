package com.klim.birukou.book_hub.service.ums;

import com.klim.birukou.book_hub.controller.dto.user.UpdateUserDataDto;
import com.klim.birukou.book_hub.controller.dto.user.UserSignInDto;
import com.klim.birukou.book_hub.controller.dto.user.UserSignUpDto;
import com.klim.birukou.book_hub.domain.ums.User;
import com.klim.birukou.book_hub.domain.ums.UserData;
import lombok.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserClientService {

    private static final String SIGN_UP = "/sign/up";
    private static final String SIGN_IN = "/sign/in";
    private static final String USER_DATA = "/users/data";
    private static final ParameterizedTypeReference<@NonNull UUID> BODY_TYPE_UUID
        = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<@NonNull User> BODY_TYPE_USER
        = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<@NonNull UserData> BODY_TYPE_USER_DATA
        = new ParameterizedTypeReference<>() {
    };

    private final RestClient restClient;

    public UserClientService(RestClient umsRestClient) {
        this.restClient = umsRestClient;
    }

    public UUID signUp(UserSignUpDto dto) {
        return Objects.requireNonNull(restClient.method(HttpMethod.POST)
            .uri(uriBuilder -> uriBuilder
                .path(SIGN_UP)
                .build())
            .body(dto)
            .retrieve()
            .body(BODY_TYPE_UUID));
    }

    public User signIn(UserSignInDto dto) {
        return Objects.requireNonNull(restClient.method(HttpMethod.POST)
            .uri(uriBuilder -> uriBuilder
                .path(SIGN_IN)
                .build())
            .body(dto)
            .retrieve()
            .body(BODY_TYPE_USER));
    }

    public UserData updateUserData(UpdateUserDataDto dto) {
        return Objects.requireNonNull(restClient.method(HttpMethod.PATCH)
            .uri(uriBuilder -> uriBuilder
                .path(USER_DATA)
                .build())
            .body(dto)
            .retrieve()
            .body(BODY_TYPE_USER_DATA));
    }

    public UserData getUserData(UUID uid) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(USER_DATA + "/" + uid)
                .build())
            .retrieve()
            .body(BODY_TYPE_USER_DATA));
    }
}
