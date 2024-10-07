package com.ust.tracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class TransactionDto {
    @NotNull
    private String name;

    @NotBlank(message = "type of transaction is mandatory")
    private String transactionType;

    @NotBlank(message = "Payment method is mandatory")
    private String paymentMethod;

    @Min(value = 1, message="amount should be greater than 1")
    private double amount;

    @Size(min=5, max=300)
    private String description;

    private Date transactionDate;
}
