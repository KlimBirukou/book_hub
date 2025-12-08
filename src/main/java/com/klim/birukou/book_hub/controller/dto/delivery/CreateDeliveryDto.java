package com.klim.birukou.book_hub.controller.dto.delivery;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateDeliveryDto(

    @NotNull(message = "Publication uid must not be null")
    @org.hibernate.validator.constraints.UUID(message = "Incorrect UUID format")
    UUID publicationUid,

    @NotNull(message = "Invoker uid must not be null")
    @org.hibernate.validator.constraints.UUID(message = "Incorrect UUID format")
    UUID invokerUserUid,

    @NotNull(message = "From warehouse uid must not be null")
    @org.hibernate.validator.constraints.UUID(message = "Incorrect UUID format")
    UUID fromWarehouseUid,

    @NotNull(message = "To warehouse uid must not be null")
    @org.hibernate.validator.constraints.UUID(message = "Incorrect UUID format")
    UUID toWarehouseUid
) {
}
