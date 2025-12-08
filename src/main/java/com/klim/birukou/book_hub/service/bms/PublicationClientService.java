package com.klim.birukou.book_hub.service.bms;

import com.klim.birukou.book_hub.domain.bms.Publication;
import com.klim.birukou.book_hub.domain.bms.PublicationWithPublisher;
import com.klim.birukou.book_hub.domain.bms.Publisher;
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
public class PublicationClientService extends AbstractPageableClientService {

    private static final String PUBLICATIONS_PATH = "/publications/book";
    private static final String PUBLISHER = "/publishers/";
    private static final ParameterizedTypeReference<@NonNull RestPage<@NonNull Publication>> BODY_TYPE_PUBLICATION
        = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<@NonNull Publisher> BODY_TYPE_PUBLISHER
        = new ParameterizedTypeReference<>() {
    };

    public PublicationClientService(RestClient bmsRestClient) {
        super(bmsRestClient);
    }

    public @NonNull Page<@NonNull Publication> getPublicationsByBookUid(@NonNull UUID uid, Pageable pageable) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(PUBLICATIONS_PATH + "/" + uid)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .body(BODY_TYPE_PUBLICATION));
    }

    public @NonNull Publisher getPublisherByUid(@NonNull UUID uid) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(PUBLISHER + "/" + uid)
                .build())
            .retrieve()
            .body(BODY_TYPE_PUBLISHER));
    }

    public @NonNull Page<@NonNull PublicationWithPublisher> getPublicationsWithPublisher(@NonNull UUID bookUid, Pageable pageable) {
        return getPublicationsByBookUid(bookUid, pageable)
            .map(publication -> {
                Publisher publisher = getPublisherByUid(publication.getPublisherUid());
                return PublicationWithPublisher.builder()
                    .uid(publication.getUid())
                    .publicationDate(publication.getPublicationDate())
                    .pageCount(publication.getPageCount())
                    .price(publication.getPrice())
                    .bookUid(publication.getBookUid())
                    .publisher(publisher)
                    .build();
            });
    }
}
