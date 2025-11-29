package com.today.api.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MigrationData {
    private String localId;
    private String type; // SCHEDULE, etc.
    private String data; // JSON string of content
}
