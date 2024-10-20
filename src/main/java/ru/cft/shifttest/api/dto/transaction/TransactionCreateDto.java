package ru.cft.shifttest.api.dto.transaction;

public record TransactionCreateDto(
        Integer seller,
        Integer amount,
        String paymentType
) {
}
