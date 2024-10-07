package com.ust.tracker.repo;

import com.ust.tracker.dto.PaymentMethodCountDto;
import com.ust.tracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE MONTH(t.transactionDate) = :month AND YEAR(t.transactionDate) = :year AND t.transactionType = 'credit'")
    Double findTotalCreditsByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE MONTH(t.transactionDate) = :month AND YEAR(t.transactionDate) = :year AND t.transactionType = 'debit'")
    Double findTotalDebitsByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT new com.ust.tracker.dto.PaymentMethodCountDto(t.paymentMethod, COUNT(t)) " +
            "FROM Transaction t WHERE DATE(t.transactionDate) = :date " +
            "GROUP BY t.paymentMethod " +
            "ORDER BY COUNT(t) DESC")
    List<PaymentMethodCountDto> findPaymentMethodCountsByDate(@Param("date") Date date);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.transactionType = 'Credit' THEN t.amount ELSE 0 END), 0) - " +
            "COALESCE(SUM(CASE WHEN t.transactionType = 'Debit' THEN t.amount ELSE 0 END), 0) " +
            "FROM Transaction t")
    Double findTotalBalance();

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transactionType = 'Credit' AND DATE(t.transactionDate) = :date")
    Double findDailyCreditsByDate(@Param("date") Date date);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transactionType = 'Debit' AND DATE(t.transactionDate) = :date")
    Double findDailyDebitsByDate(@Param("date") Date date);

}
