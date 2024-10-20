package ru.cft.shifttest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.cft.shifttest.api.dto.transaction.TransactionCreateDto;
import ru.cft.shifttest.api.dto.transaction.TransactionInfoDto;
import ru.cft.shifttest.core.entity.Seller;
import ru.cft.shifttest.core.entity.Transaction;
import ru.cft.shifttest.core.exception.ErrorCode;
import ru.cft.shifttest.core.exception.ServiceException;
import ru.cft.shifttest.core.mapper.TransactionMapper;
import ru.cft.shifttest.core.repository.SellerRepository;
import ru.cft.shifttest.core.repository.TransactionRepository;
import ru.cft.shifttest.core.service.TransactionService;
import ru.cft.shifttest.core.service.TransactionServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionsServiceTests {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testCreateTransactionSuccess() {
        TransactionInfoDto returned = new TransactionInfoDto(1, 1, 1, "CARD", LocalDateTime.of(1000, 10, 10, 10, 10));
        TransactionCreateDto toSave = new TransactionCreateDto(1, 1, "CARD");
        when(transactionMapper.map(any(Transaction.class))).thenReturn(returned);
        when(sellerRepository.findById(1)).thenReturn(Optional.of(new Seller()));
        Assertions.assertEquals(transactionService.create(toSave), returned);
    }

    @Test
    public void testCreateTransactionFailByPaymentType() {
        Assertions.assertThrows(ServiceException.class, () -> transactionService.create(new TransactionCreateDto(1, 1, "sosal?")));
    }

    @Test
    public void testCreateTransactionFailBySellerNotFound() {
        Assertions.assertThrows(ServiceException.class, () -> transactionService.create(new TransactionCreateDto(-2, 1, "CARD")));
    }
}
