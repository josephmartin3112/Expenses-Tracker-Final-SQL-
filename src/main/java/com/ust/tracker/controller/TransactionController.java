package com.ust.tracker.controller;

import com.ust.tracker.dto.DailyAccountSummaryDto;
import com.ust.tracker.dto.MonthlyTotalsDto;
import com.ust.tracker.dto.PaymentMethodCountDto;
import com.ust.tracker.dto.TransactionDto;
import com.ust.tracker.exception.TransactionNotFoundException;
import com.ust.tracker.model.Transaction;
import com.ust.tracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody @Valid TransactionDto transactionDto){
        Transaction createdTransaction = transactionService.addTransaction(transactionDto);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable UUID id) throws TransactionNotFoundException {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @GetMapping("/month/{month}/{year}")
    public ResponseEntity<MonthlyTotalsDto> getTotalsForMonth(@PathVariable int month, @PathVariable int year) {
        MonthlyTotalsDto totals = transactionService.getTotalsForMonth(month, year);
        return ResponseEntity.ok(totals);
    }

    @GetMapping("/payment-methods/counts")
    public ResponseEntity<List<PaymentMethodCountDto>> getPaymentMethodCounts(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Date sqlDate = (date != null) ? java.sql.Date.valueOf(date) : new Date();

        List<PaymentMethodCountDto> counts = transactionService.getPaymentMethodCountsByDate(sqlDate);
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/summary")
    public ResponseEntity<DailyAccountSummaryDto> getDailyAccountSummary(
            @RequestParam(value = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Date sqlDate = java.sql.Date.valueOf(date);
        DailyAccountSummaryDto summary = transactionService.getDailyAccountSummary(sqlDate);

        return ResponseEntity.ok(summary);
    }
}

