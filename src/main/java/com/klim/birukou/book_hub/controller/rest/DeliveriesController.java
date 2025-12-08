package com.klim.birukou.book_hub.controller.rest;

import com.klim.birukou.book_hub.controller.dto.delivery.CreateDeliveryDto;
import com.klim.birukou.book_hub.domain.sms.Delivery;
import com.klim.birukou.book_hub.domain.sms.DeliveryStatus;
import com.klim.birukou.book_hub.service.sms.DeliveriesClientService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("deliveries")
@RequiredArgsConstructor
public class DeliveriesController {

    private final DeliveriesClientService deliveriesClientService;

    @GetMapping("/pending")
    public ResponseEntity<@NonNull Page<@NonNull Delivery>> getPendingDeliveries(
        @PageableDefault(sort = {"status", "orderDatetime", "finishDatetime"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var pageOfDeliveries = deliveriesClientService.getByStatus(DeliveryStatus.IN_PROGRESS, pageable);
        return ResponseEntity.ok(pageOfDeliveries);
    }

    @GetMapping("/finished")
    public ResponseEntity<@NonNull Page<@NonNull Delivery>> getFinishedDeliveries(
        @PageableDefault(sort = {"status", "orderDatetime", "finishDatetime"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var pageOfDeliveries = deliveriesClientService.getByStatus(DeliveryStatus.FINISHED, pageable);
        return ResponseEntity.ok(pageOfDeliveries);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<@NonNull Page<@NonNull Delivery>> getDeliveriesByOrderId(
        @PathVariable String id,
        @PageableDefault(sort = {"status", "orderDatetime", "finishDatetime"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var pageOfDeliveries = deliveriesClientService.getByOrderUid(id, pageable);
        return ResponseEntity.ok(pageOfDeliveries);
    }

    @GetMapping("/user/{uid}")
    public ResponseEntity<@NonNull Page<@NonNull Delivery>> getDeliveriesByUserId(
        @PathVariable UUID uid,
        @PageableDefault(sort = {"status", "orderDatetime", "finishDatetime"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var pageOfDeliveries = deliveriesClientService.getByUserUid(uid, pageable);
        return ResponseEntity.ok(pageOfDeliveries);
    }

    @PostMapping
    public ResponseEntity<@NonNull UUID> createNewDelivery(
        @Valid @RequestBody CreateDeliveryDto dto
    ) {
        var uid = deliveriesClientService.createDelivery(dto);
        return ResponseEntity.ok(uid);
    }

    @PatchMapping("/{deliveryId}/complete")
    public ResponseEntity<@NonNull Void> completeDelivery(
        @PathVariable UUID deliveryId
    ) {
        deliveriesClientService.completeDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }

}
