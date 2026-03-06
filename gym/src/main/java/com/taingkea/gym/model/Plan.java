package com.taingkea.gym.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum Plan {
    STARTER(1, ChronoUnit.MONTHS),
    PRO(3, ChronoUnit.MONTHS),
    ELITE(1, ChronoUnit.YEARS);

    private final int amount;
    private final ChronoUnit unit;

    Plan(int amount, ChronoUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public LocalDate calcEndDate(LocalDate start) {
        return start.plus(amount, unit);
    }
}