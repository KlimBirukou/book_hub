package com.klim.birukou.book_hub.controller.rest;

import com.klim.birukou.book_hub.domain.sms.Inventory;
import com.klim.birukou.book_hub.domain.sms.InventoryDto;
import com.klim.birukou.book_hub.service.sms.AvailabilitiesClientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/availabilities")
@RequiredArgsConstructor
public class AvailabilitiesController {

    private final AvailabilitiesClientService availabilitiesClientService;

    @GetMapping("/publication/{publication_uid}/check")
    public ResponseEntity<@NonNull Page<@NonNull InventoryDto>> getAvailabilitiesByPublication(
        @PathVariable UUID publication_uid,
        @PageableDefault Pageable pageable
    ) {
        var pageOfAvailabilities = availabilitiesClientService.getByUserUid(publication_uid, pageable);
        return ResponseEntity.ok(pageOfAvailabilities);
    }
}
