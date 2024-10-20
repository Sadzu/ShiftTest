package ru.cft.shifttest.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {
    public static final String SELLER_NOT_FOUND = "Seller with id %d not found";
    public static final String SELLER_LESS_AMOUNT_NOT_FOUNT = "Sellers with amount less than %d are not found";
    public static final String TRANSACTION_NOT_FOUND = "Transaction with id %d not found";
    public static final String TRANSACTIONS_BY_SELLER_ID_NOT_FOUND = "Transactions by seller with id %d not found";
}
