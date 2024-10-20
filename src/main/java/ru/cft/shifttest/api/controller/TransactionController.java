package ru.cft.shifttest.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.shifttest.api.dto.transaction.TransactionCreateDto;
import ru.cft.shifttest.api.dto.transaction.TransactionInfoDto;
import ru.cft.shifttest.core.service.TransactionService;

import java.util.List;

import static ru.cft.shifttest.api.Path.*;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(TRANSACTIONS_CREATE)
    public TransactionInfoDto create(@RequestBody TransactionCreateDto transactionCreateRequest) {
        return transactionService.create(transactionCreateRequest);
    }

    @GetMapping(TRANSACTIONS_GETALL)
    public List<TransactionInfoDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping(TRANSACTIONS_ID)
    public TransactionInfoDto getTransactionById(@PathVariable Integer id) {
        return transactionService.getById(id);
    }

    @GetMapping(TRANSACTIONS_BYSELLER)
    public List<TransactionInfoDto> getTransactionsBySellerId(@PathVariable Integer id) {
        return transactionService.getBySellerId(id);
    }
}
