package com.klim.birukou.book_hub.domain.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public final class Delivery {

    private UUID uid;
    @JsonProperty("order_id")
    private String orderId;
    private DeliveryStatus status;
    @JsonProperty("publication_uid")
    private UUID publicationUid;
    @JsonProperty("invoker_user_uid")
    private UUID invokerUserUid;
    @JsonProperty("order_datetime")
    private LocalDateTime orderDatetime;
    @JsonProperty("finish_datetime")
    private LocalDateTime finishDatetime;
    @JsonProperty("from_warehouse_uid")
    private UUID fromWarehouseUid;
    @JsonProperty("to_warehouse_uid")
    private UUID toWarehouseUid;
}
