package com.ust.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyTotalsDto {
    private double totalCredits;
    private double totalDebits;
}
