package com.taingkea.fitness.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String planName;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    /** ACTIVE | EXPIRED | CANCELLED */
    @Column(nullable = false)
    @Builder.Default
    private String status = "ACTIVE";

    public boolean isActive() {
        return "ACTIVE".equals(status) && !LocalDate.now().isAfter(endDate);
    }
}