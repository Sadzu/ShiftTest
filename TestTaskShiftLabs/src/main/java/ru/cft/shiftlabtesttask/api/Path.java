package api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Path {
    // ----- Seller -----
    public static final String SELLERS = "/sellers";
    public static final String SELLERS_ID = "/sellers/{id}";
    public static final String SELLERS_BEST = "/sellers/best";
    public static final String SELLERS_COST = "/sellers/{cost}";

    // ----- Transaction -----
    public static final String TRANSACTIONS = "/transactions";
    public static final String TRANSACTIONS_ID = "/transactions/{id}";
}
