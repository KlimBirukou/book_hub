package com.klim.birukou.book_hub.service.bms;

import com.klim.birukou.book_hub.domain.bms.Author;
import com.klim.birukou.book_hub.domain.bms.Book;
import com.klim.birukou.book_hub.domain.bms.BookWithAuthor;
import com.klim.birukou.book_hub.service.AbstractPageableClientService;
import com.klim.birukou.book_hub.domain.RestPage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.UUID;

@Service
public class BookClientService extends AbstractPageableClientService {

    @Autowired
    private AuthorClientService authorClientService;

    private static final String BOOK = "/books";
    private static final String TITLE = "/title";
    private static final String AUTHOR = "/author";
    private static final ParameterizedTypeReference<@NonNull RestPage<@NonNull Book>> BODY_TYPE
        = new ParameterizedTypeReference<>() {
    };

    public BookClientService(RestClient bmsRestClient) {
        super(bmsRestClient);
    }

    public @NonNull Page<@NonNull Book> getByAuthorUid(@NonNull UUID uid, Pageable pageable) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(BOOK + AUTHOR + "/" + uid)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .body(BODY_TYPE));
    }

    public @NonNull Page<@NonNull Book> getByTitle(@NonNull String title, Pageable pageable) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(BOOK + TITLE + "/" + title)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .body(BODY_TYPE));
    }

    public @NonNull Page<@NonNull BookWithAuthor> getBooksWithAuthors(String title, Pageable pageable) {
        return getByTitle(title, pageable)
            .map(book -> {
                Author author = authorClientService.getAuthorsByUid(book.getAuthorUid());
                return BookWithAuthor.builder()
                    .uid(book.getUid())
                    .title(book.getTitle())
                    .author(author)
                    .build();
            });
    }

}
