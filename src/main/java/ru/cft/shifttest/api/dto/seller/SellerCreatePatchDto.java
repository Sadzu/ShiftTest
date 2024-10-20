package ru.cft.shifttest.api.dto.seller;

public record SellerCreatePatchDto(
        String name,
        String contactInfo
) {
}
