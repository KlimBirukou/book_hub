package com.klim.birukou.book_hub.service.sms;

import com.klim.birukou.book_hub.domain.sms.Inventory;
import com.klim.birukou.book_hub.domain.sms.InventoryDto;
import com.klim.birukou.book_hub.domain.sms.Warehouse;
import com.klim.birukou.book_hub.domain.sms.WarehousesType;
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
import java.util.stream.Collectors;

@Service
public class AvailabilitiesClientService extends AbstractPageableClientService {

    private static final String PATH = "/inventories/publications";
    private static final String WAREHOUSES = "/warehouses";
    private static final String PUBLICATIONS = "/publication";
    private static final ParameterizedTypeReference<@NonNull RestPage<@NonNull Warehouse>> WAREHOUSES_BODY_TYPE
        = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<@NonNull RestPage<@NonNull Inventory>> INVENTORIES_BODY_TYPE
        = new ParameterizedTypeReference<>() {
    };

    public AvailabilitiesClientService(RestClient smsRestClient) {
        super(smsRestClient);
    }

    public @NonNull Page<@NonNull InventoryDto> getByUserUid(@NonNull UUID uid, Pageable pageable) {
        var warehouses = getWarehousesByPublicationUid(uid, pageable);
        var warehouseTypeMap = warehouses.get()
            .collect(Collectors.toMap(
                Warehouse::getUid,
                warehouse -> WarehousesType.valueOf(warehouse.getType())
            ));
        var warehousesUidString = warehouseTypeMap.keySet().stream()
            .map(UUID::toString)
            .collect(Collectors.joining(","));
        var inventories = getInventories(warehousesUidString, uid, pageable);
        return inventories.map(inventory -> InventoryDto.builder()
            .uid(inventory.getUid())
            .quantity(inventory.getQuantity())
            .publicationUid(inventory.getPublicationUid())
            .warehouseUid(inventory.getWarehouseUid())
            .type(warehouseTypeMap.get(inventory.getWarehouseUid()))
            .build());
    }

    private @NonNull Page<@NonNull Warehouse> getWarehousesByPublicationUid(@NonNull UUID uid, Pageable pageable) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(WAREHOUSES + PUBLICATIONS + "/" + uid)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .body(WAREHOUSES_BODY_TYPE));
    }

    private @NonNull Page<@NonNull Inventory> getInventories(String warehousesUid, UUID uid, Pageable pageable) {
        return Objects.requireNonNull(restClient.method(HttpMethod.GET)
            .uri(uriBuilder -> uriBuilder
                .path(PATH + "/" + uid + WAREHOUSES + "/" + warehousesUid)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .body(INVENTORIES_BODY_TYPE));
    }
}