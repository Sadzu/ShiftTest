package ru.cft.shifttest.core.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cft.shifttest.api.dto.transaction.TransactionCreateDto;
import ru.cft.shifttest.api.dto.transaction.TransactionInfoDto;

import java.util.List;

@Service
public interface TransactionService {

    @Transactional
    TransactionInfoDto create(TransactionCreateDto transactionCreateRequest);

    List<TransactionInfoDto> getAllTransactions();

    TransactionInfoDto getById(int id);

    List<TransactionInfoDto> getBySellerId(int sellerId);
}
