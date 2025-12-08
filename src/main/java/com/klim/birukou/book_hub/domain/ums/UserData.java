package com.klim.birukou.book_hub.domain.ums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserData {

    private UUID uid;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private UUID userUid;
}
