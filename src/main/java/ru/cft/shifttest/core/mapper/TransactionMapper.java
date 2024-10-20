package ru.cft.shifttest.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.cft.shifttest.api.dto.transaction.TransactionInfoDto;
import ru.cft.shifttest.core.entity.Transaction;

import java.util.List;

@Mapper
public interface TransactionMapper {
    @Mapping(target = "seller", source = "transaction", qualifiedByName = "mapSellerId")
    TransactionInfoDto map(Transaction transaction);

    List<TransactionInfoDto> map(List<Transaction> transactions);

    @Named("mapSellerId")
    default Integer mapSellerId(Transaction transaction) {
        return transaction.getSeller().getId();
    }
}
