package ru.cft.shifttest.core.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.cft.shifttest.api.dto.transaction.TransactionInfoDto;
import ru.cft.shifttest.core.entity.Transaction;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-19T19:51:29+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

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
