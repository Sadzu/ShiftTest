package ru.cft.shiftlabtesttask.api.dto.transaction;

public record TransactionCreateDto(
    Integer seller,
    Integer amount,
    String paymentType
) {
}
