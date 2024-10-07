package com.ust.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentMethodCountDto {
    private String paymentMethod;
    private long count;
}
