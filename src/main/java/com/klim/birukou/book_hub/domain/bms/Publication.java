package com.klim.birukou.book_hub.domain.bms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public final class Publication {

    private UUID uid;
    private LocalDate publicationDate;
    private int pageCount;
    private BigDecimal price;
    private UUID publisherUid;
    private UUID bookUid;
}
