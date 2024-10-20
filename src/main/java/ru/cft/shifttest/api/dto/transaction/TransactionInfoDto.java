package ru.cft.shifttest.api.dto.transaction;

import java.time.LocalDateTime;

public record TransactionInfoDto(
        Integer id,
        Integer seller,
        Integer amount,
        String paymentType,
        LocalDateTime transactionDate
) {
}
