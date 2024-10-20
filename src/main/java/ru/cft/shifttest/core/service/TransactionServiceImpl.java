package ru.cft.shifttest.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.shifttest.api.dto.transaction.TransactionCreateDto;
import ru.cft.shifttest.api.dto.transaction.TransactionInfoDto;
import ru.cft.shifttest.core.entity.Seller;
import ru.cft.shifttest.core.entity.Transaction;
import ru.cft.shifttest.core.exception.ErrorCode;
import ru.cft.shifttest.core.exception.ServiceException;
import ru.cft.shifttest.core.mapper.TransactionMapper;
import ru.cft.shifttest.core.repository.SellerRepository;
import ru.cft.shifttest.core.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.cft.shifttest.core.Messages.*;
import static ru.cft.shifttest.core.exception.HttpStatuses.INVALID_REQUEST;
import static ru.cft.shifttest.core.exception.HttpStatuses.OBJECT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository _transactionRepository;
    private final TransactionMapper _transactionMapper;
    private final SellerRepository _sellerRepository;
    private static final ArrayList<String> _PAYMENT_TYPES = new ArrayList<String>(Arrays.asList("CASH", "CARD", "TRANSFER"));

    @Override
    public TransactionInfoDto create(TransactionCreateDto transactionCreateRequest) {
        Transaction transaction = buildNewTransaction(transactionCreateRequest);
        _transactionRepository.save(transaction);

        return _transactionMapper.map(transaction);
    }

    @Override
    public List<TransactionInfoDto> getAllTransactions() {
        Optional<List<Transaction>> result = _transactionRepository.getAllByAmountAfter(0);
        if (result.isPresent()) {
            return _transactionMapper.map(result.get());
        } else {
            throw new ServiceException(new ErrorCode(OBJECT_NOT_FOUND), "Transactions not found");
        }
    }

    @Override
    public TransactionInfoDto getById(int id) {
        return _transactionMapper.map(getByIdOrThrow(id));
    }

    @Override
    public List<TransactionInfoDto> getBySellerId(int sellerId) {
        return _transactionMapper.map(getBySellerIdOrThrow(sellerId));
    }

    private Transaction buildNewTransaction(TransactionCreateDto transactionCreateDto) {
        Transaction transaction = Transaction.builder()
                .amount(transactionCreateDto.amount())
                .transactionDate(LocalDateTime.now())
                .build();

        if (_PAYMENT_TYPES.contains(transactionCreateDto.paymentType())) {
            transaction.setPaymentType(transactionCreateDto.paymentType());
        } else {
            throw new ServiceException(new ErrorCode(INVALID_REQUEST), "Invalid payment type");
        }
        Optional<Seller> seller = _sellerRepository.findById(transactionCreateDto.seller());
        if (seller.isPresent()) {
            transaction.setSeller(seller.get());
        } else {
            throw new ServiceException(new ErrorCode(OBJECT_NOT_FOUND), String.format(SELLER_NOT_FOUND, transactionCreateDto.seller()));
        }

        return transaction;
    }

    private Transaction getByIdOrThrow(int id) {
        return _transactionRepository.findById(id)
                .orElseThrow(() -> new ServiceException(new ErrorCode(OBJECT_NOT_FOUND), String.format(TRANSACTION_NOT_FOUND, id)));
    }

    private List<Transaction> getBySellerIdOrThrow(int sellerId) {
        return _transactionRepository.findAllBySellerId(sellerId)
                .orElseThrow(() -> new ServiceException(new ErrorCode(OBJECT_NOT_FOUND), String.format(TRANSACTIONS_BY_SELLER_ID_NOT_FOUND, sellerId)));
    }
}
