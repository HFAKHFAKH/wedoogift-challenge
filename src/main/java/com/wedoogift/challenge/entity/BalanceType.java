package com.wedoogift.challenge.entity;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public enum BalanceType {
    MEAL {
        @Override
        public LocalDate getEndDate() {
            return LocalDate.now().minusDays(1).plusYears(1);
        }
    },
    GIFT {
        @Override
        public LocalDate getEndDate() {
            return LocalDate.now().withMonth(2).plusYears(1).with(TemporalAdjusters.lastDayOfMonth());
        }
    };

    public abstract LocalDate getEndDate();
}