package com.klim.birukou.book_hub.service.bms;

import com.klim.birukou.book_hub.domain.bms.Author;
import com.klim.birukou.book_hub.service.AbstractPageableClientService;
import com.klim.birukou.book_hub.domain.RestPage;
import lombok.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuthorClientService extends AbstractPageableClientService {

    private static final String AUTHORS = "/authors";
    private static final String NAME = "/name";
    private static final ParameterizedTypeReference<@NonNull RestPage<@NonNull Author>> AUTHORS_BODY_TYPE
        = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<@NonNull Author> AUTHOR_BODY_TYPE
        = new ParameterizedTypeReference<>() {
    };

    public AuthorClientService(RestClient bmsRestClient) {
        super(bmsRestClient);
    }

    public @NonNull Page<@NonNull Author> getAuthorsByName(@NonNull String name, Pageable pageable) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(AUTHORS + "/" + NAME + "/" + name)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .body(AUTHORS_BODY_TYPE));
    }

    public @NonNull Author getAuthorsByUid(@NonNull UUID  uid) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(AUTHORS + "/" + uid)
                .build())
            .retrieve()
            .body(AUTHOR_BODY_TYPE));
    }
}
