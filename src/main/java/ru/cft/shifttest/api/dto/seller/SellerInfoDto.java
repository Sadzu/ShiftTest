package ru.cft.shifttest.api.dto.seller;

import java.time.LocalDateTime;

public record SellerInfoDto(
        Integer id,
        String name,
        String contactInfo,
        LocalDateTime registrationDate
) {
}
