package com.klim.birukou.book_hub.domain.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public final class InventoryDto {

    private UUID uid;
    private int quantity;
    private UUID publicationUid;
    private UUID warehouseUid;
    private WarehousesType type;
}
