package com.klim.birukou.book_hub.controller.rest;

import com.klim.birukou.book_hub.domain.bms.Author;
import com.klim.birukou.book_hub.domain.bms.Book;
import com.klim.birukou.book_hub.domain.bms.BookWithAuthor;
import com.klim.birukou.book_hub.domain.bms.Publication;
import com.klim.birukou.book_hub.domain.bms.PublicationWithPublisher;
import com.klim.birukou.book_hub.service.bms.AuthorClientService;
import com.klim.birukou.book_hub.service.bms.BookClientService;
import com.klim.birukou.book_hub.service.bms.PublicationClientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/searches")
@RequiredArgsConstructor
public class SearchController {

    private final AuthorClientService authorClientService;
    private final BookClientService bookClientService;
    private final PublicationClientService publicationClientService;

    @GetMapping("/authors/name/{name}")
    public ResponseEntity<@NonNull Page<@NonNull Author>> searchAuthorsByName(
        @PathVariable String name,
        @PageableDefault(sort = {"firstName", "lastName"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var pageOfAuthors = authorClientService.getAuthorsByName(name, pageable);
        return ResponseEntity.ok(pageOfAuthors);
    }

    @GetMapping("/books/author/{uid}")
    public ResponseEntity<@NonNull Page<@NonNull Book>> searchBooksAuthorUid(
        @PathVariable UUID uid,
        @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var pageOfBooks = bookClientService.getByAuthorUid(uid, pageable);
        return ResponseEntity.ok(pageOfBooks);
    }

    @GetMapping("/books/title/{title}")
    public ResponseEntity<@NonNull Page<@NonNull BookWithAuthor>> searchBooksByTitle(
        @PathVariable String title,
        @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var pageOfBooks = bookClientService.getBooksWithAuthors(title, pageable);
        return ResponseEntity.ok(pageOfBooks);
    }

    @GetMapping("/publications/book/{uid}")
    public ResponseEntity<@NonNull Page<@NonNull PublicationWithPublisher>> searchPublicationsByBookUid(
        @PathVariable UUID uid,
        @PageableDefault(sort = "publicationDate", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var pageOfPublications = publicationClientService.getPublicationsWithPublisher(uid, pageable);
        return ResponseEntity.ok(pageOfPublications);
    }
}
