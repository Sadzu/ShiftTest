package ru.cft.shifttest.core.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.BindParam;
import ru.cft.shifttest.api.dto.seller.BestPeriodDto;
import ru.cft.shifttest.core.entity.Transaction;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findById(int id);

    Optional<List<Transaction>> findAllBySellerId(Integer sellerId);

    Optional<List<Transaction>> getAllByAmountAfter(Integer amount);

    @Query(nativeQuery = true, value = "SELECT SUM(amount) FROM transactions WHERE seller_id=?")
    Optional<Integer> getSumAmountBySellerId(Integer sellerId);

    @Query(nativeQuery = true, value = "SELECT SUM(amount) FROM transactions WHERE seller_id = ? AND transaction_date > (CURRENT_TIMESTAMP - CAST(? AS INTERVAL))")
    Optional<Integer> getSumAmountBySellerIdWithPeriod(Integer sellerId, String period);

    @Query(nativeQuery = true, value = "SELECT SUM(amount) FROM transactions WHERE seller_id = ? AND transaction_date >= CAST(? AS TIMESTAMP) AND transaction_date <= CAST(? AS TIMESTAMP)")
    Optional<Integer> getSumAmountBySellerIdWithPeriod(Integer sellerId, String begin, String end);

    @Query(value = """
        SELECT 
            DATE_TRUNC(CAST(? AS VARCHAR), transaction_date) AS period_start,
            COUNT(*) AS transaction_count
        FROM transactions
        WHERE seller_id = ?
        GROUP BY period_start
        ORDER BY transaction_count DESC
        LIMIT 1
    """, nativeQuery = true)
    Optional<String[]> getBestPeriodOfSeller(String period, Integer sellerId);
}
