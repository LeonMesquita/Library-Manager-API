package com.library_manager.api.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    PENDING(1, "PENDING"),
    APPROVED(2, "APPROVED"),
    REJECTED(3, "REJECTED"),
    RETURNED(4, "RETURNED");

    private final Integer code;
    private final String description;


}
