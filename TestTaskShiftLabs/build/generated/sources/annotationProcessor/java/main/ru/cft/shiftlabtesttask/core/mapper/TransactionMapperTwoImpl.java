package ru.cft.shiftlabtesttask.core.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ru.cft.shiftlabtesttask.api.dto.transaction.TransactionInfoDto;
import ru.cft.shiftlabtesttask.core.entity.Transaction;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-18T21:03:22+0700",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21.0.5 (Amazon.com Inc.)"
)
public class TransactionMapperTwoImpl implements TransactionMapperTwo {

    @Override
    public TransactionInfoDto map(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        Integer seller = null;
        Integer id = null;
        Integer amount = null;
        String paymentType = null;
        LocalDateTime transactionDate = null;

        seller = mapSellerId( transaction );
        id = transaction.getId();
        amount = transaction.getAmount();
        paymentType = transaction.getPaymentType();
        transactionDate = transaction.getTransactionDate();

        TransactionInfoDto transactionInfoDto = new TransactionInfoDto( id, seller, amount, paymentType, transactionDate );

        return transactionInfoDto;
    }

    @Override
    public List<TransactionInfoDto> map(List<Transaction> transactions) {
        if ( transactions == null ) {
            return null;
        }

        List<TransactionInfoDto> list = new ArrayList<TransactionInfoDto>( transactions.size() );
        for ( Transaction transaction : transactions ) {
            list.add( map( transaction ) );
        }

        return list;
    }
}
