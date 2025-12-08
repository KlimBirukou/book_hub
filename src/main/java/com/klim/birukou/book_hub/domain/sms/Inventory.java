package com.klim.birukou.book_hub.domain.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public final class Inventory {

    private UUID uid;
    private int quantity;
    @JsonProperty("publication_uid")
    private UUID publicationUid;
    @JsonProperty("warehouse_uid")
    private UUID warehouseUid;
}
