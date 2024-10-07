package com.ust.tracker.service;

import com.ust.tracker.dto.DailyAccountSummaryDto;
import com.ust.tracker.dto.MonthlyTotalsDto;
import com.ust.tracker.dto.PaymentMethodCountDto;
import com.ust.tracker.dto.TransactionDto;
import com.ust.tracker.exception.TransactionNotFoundException;
import com.ust.tracker.model.Transaction;
import com.ust.tracker.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;

    public Transaction addTransaction(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setName(transactionDto.getName());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setPaymentMethod((transactionDto.getPaymentMethod()));

        if (transactionDto.getTransactionDate() != null) {
            transaction.setTransactionDate(transactionDto.getTransactionDate());
        } else {
            transaction.setTransactionDate(new Date());
        }
        return transactionRepo.save(transaction);
    }

    public Transaction getTransaction(UUID id) throws TransactionNotFoundException {
        return transactionRepo.findById(id)
                .orElseThrow(()->new TransactionNotFoundException("Transaction not found for the given id: " + id));
    }

    public MonthlyTotalsDto getTotalsForMonth(int month, int year) {
        Double totalCredits = transactionRepo.findTotalCreditsByMonthAndYear(month, year);
        Double totalDebits = transactionRepo.findTotalDebitsByMonthAndYear(month, year);

        return new MonthlyTotalsDto(
                totalCredits != null ? totalCredits : 0.0,
                totalDebits != null ? totalDebits : 0.0
        );
    }

    public List<PaymentMethodCountDto> getPaymentMethodCountsByDate(Date date) {
        return transactionRepo.findPaymentMethodCountsByDate(date);
    }

    public DailyAccountSummaryDto getDailyAccountSummary(Date date) {
        Double dailyCredits = transactionRepo.findDailyCreditsByDate(date);
        Double dailyDebits = transactionRepo.findDailyDebitsByDate(date);
        Double currentBalance = transactionRepo.findTotalBalance();

        return new DailyAccountSummaryDto(
                currentBalance != null ? currentBalance : 0.0,
                dailyCredits != null ? dailyCredits : 0.0,
                dailyDebits != null ? dailyDebits : 0.0
        );
    }
}
