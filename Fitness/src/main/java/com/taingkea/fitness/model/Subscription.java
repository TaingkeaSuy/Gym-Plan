package com.taingkea.fitness.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plan;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getPlanName() {
        if (plan == null) return "";
        if (plan.equals("starter")) return "Starter";
        if (plan.equals("pro"))     return "Pro";
        if (plan.equals("elite"))   return "Elite";
        return plan;
    }

    public boolean isActive() {
        return endDate != null && !LocalDate.now().isAfter(endDate);
    }

    public long getDaysRemaining() {
        if (!isActive()) return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }

    public int getProgressPercent() {
        if (startDate == null || endDate == null) return 0;
        long total = ChronoUnit.DAYS.between(startDate, endDate);
        long used  = ChronoUnit.DAYS.between(startDate, LocalDate.now());
        if (total <= 0) return 100;
        return (int) Math.min(100, (used * 100) / total);
    }
}