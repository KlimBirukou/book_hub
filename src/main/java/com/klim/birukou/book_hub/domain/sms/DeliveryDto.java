package com.klim.birukou.book_hub.domain.sms;

import java.util.UUID;

public final class DeliveryDto {

    private UUID uid;
    private String id;

    private UUID invokerUid;
    private String invokerName;

    private UUID toWarehouseUid;
    private String toWarehouseName;

    private UUID fromWarehouseUid;
    private String fromWarehouseName;

    private UUID publicationUid;
    private UUID bookUid;
    private String bookName;

    private UUID authorUid;
    private String authorName;

    private UUID publisherUid;
    private String publisherName;
}
