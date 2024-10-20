package ru.cft.shifttest.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Path {
    // ----- Seller -----
    public static final String SELLERS = "/sellers";
    public static final String SELLERS_PATCH_ID = "/sellers/patch/{id}";
    public static final String SELLERS_GET_ID = "/sellers/get/{id}";
    public static final String SELLERS_DELETE_ID = "/sellers/delete/{id}";
    public static final String SELLERS_BEST = "/sellers/best";
    public static final String SELLERS_COST = "/sellers/{cost}";

    // ----- Transaction -----
    public static final String TRANSACTIONS_CREATE = "/transactions/create";
    public static final String TRANSACTIONS_GETALL = "/transactions/getall";
    public static final String TRANSACTIONS_ID = "/transactions/{id}";
    public static final String TRANSACTIONS_BYSELLER = "/transactions/byseller/{id}";
}
