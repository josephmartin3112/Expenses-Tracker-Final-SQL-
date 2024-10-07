package com.ust.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyAccountSummaryDto {
    private double currentBalance;
    private double dailyCredits;
    private double dailyDebits;
}
