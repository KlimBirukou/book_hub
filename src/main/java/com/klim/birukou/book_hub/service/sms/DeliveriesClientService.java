package com.klim.birukou.book_hub.service.sms;

import com.klim.birukou.book_hub.controller.dto.delivery.CreateDeliveryDto;
import com.klim.birukou.book_hub.domain.sms.Delivery;
import com.klim.birukou.book_hub.domain.sms.DeliveryStatus;
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
public class DeliveriesClientService extends AbstractPageableClientService {

    private static final String PATH = "/deliveries";
    private static final String STATUS = "status";
    private static final String USER_UID = "user_uid";
    private static final String ORDER_ID = "order_id";
    private static final ParameterizedTypeReference<@NonNull RestPage<@NonNull Delivery>> BODY_TYPE
        = new ParameterizedTypeReference<>() {
    };

    public DeliveriesClientService(RestClient smsRestClient) {
        super(smsRestClient);
    }

    public @NonNull Page<@NonNull Delivery> getByStatus(@NonNull DeliveryStatus status, Pageable pageable) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(PATH)
                .queryParam(STATUS, status)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .body(BODY_TYPE));
    }

    public @NonNull Page<@NonNull Delivery> getByUserUid(@NonNull UUID uid, Pageable pageable) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(PATH)
                .queryParam(USER_UID, uid)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .body(BODY_TYPE));
    }

    public @NonNull Page<@NonNull Delivery> getByOrderUid(@NonNull String id, Pageable pageable) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(PATH)
                .queryParam(ORDER_ID, id)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .body(BODY_TYPE));
    }

    public UUID createDelivery(@NonNull CreateDeliveryDto dto) {
        return Objects.requireNonNull(restClient.method(HttpMethod.POST)
            .uri(PATH)
            .body(dto)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            }));
    }

    public void completeDelivery(@NonNull UUID deliveryUid) {
        restClient.method(HttpMethod.PATCH)
            .uri(uriBuilder -> uriBuilder
                .path(PATH + "/" + deliveryUid + "/complete")
                .build())
            .retrieve()
            .body(Void.class);
    }
}
