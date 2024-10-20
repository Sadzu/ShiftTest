package ru.cft.shiftlabtesttask.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.shiftlabtesttask.api.dto.transaction.TransactionCreateDto;
import ru.cft.shiftlabtesttask.api.dto.transaction.TransactionInfoDto;
import ru.cft.shiftlabtesttask.core.service.TransactionService;

import java.util.List;

import static api.Path.TRANSACTIONS;
import static api.Path.TRANSACTIONS_ID;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(TRANSACTIONS)
    public TransactionInfoDto create(@RequestBody TransactionCreateDto transactionCreateRequest) {
        return transactionService.create(transactionCreateRequest);
    }

    @GetMapping(TRANSACTIONS)
    public List<TransactionInfoDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping(TRANSACTIONS_ID)
    public TransactionInfoDto getTransactionById(@PathVariable Integer id) {
        return transactionService.getById(id);
    }

    @GetMapping(TRANSACTIONS)
    public List<TransactionInfoDto> getTransactionsBySellerId(@RequestParam Integer sellerId) {
        return transactionService.getBySellerId(sellerId);
    }
}
