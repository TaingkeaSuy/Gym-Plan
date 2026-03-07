package com.taingkea.fitness.service;

import com.taingkea.fitness.model.Subscription;
import com.taingkea.fitness.model.User;
import com.taingkea.fitness.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public static final double PRICE_STARTER = 29.00;
    public static final double PRICE_PRO     = 75.00;
    public static final double PRICE_ELITE   = 249.00;

    /**
     * Create a new subscription, cancelling any existing active ones first.
     *
     * @param user     the subscribing user
     * @param planName "Starter" | "Pro" | "Elite"
     * @param period   "1 Month" | "3 Months" | "1 Year"
     */
    public Subscription subscribe(User user, String planName, String period) {
        // Cancel existing active subscriptions
        List<Subscription> active = subscriptionRepository.findByUserAndStatus(user, "ACTIVE");
        active.forEach(s -> {
            s.setStatus("CANCELLED");
            subscriptionRepository.save(s);
        });

        double price = switch (planName) {
            case "Pro"   -> PRICE_PRO;
            case "Elite" -> PRICE_ELITE;
            default      -> PRICE_STARTER;
        };

        LocalDate start = LocalDate.now();
        LocalDate end = switch (period) {
            case "3 Months" -> start.plusMonths(3);
            case "1 Year"   -> start.plusYears(1);
            default         -> start.plusMonths(1);
        };

        Subscription sub = Subscription.builder()
                .user(user)
                .planName(planName)
                .period(period)
                .price(price)
                .startDate(start)
                .endDate(end)
                .build();

        return subscriptionRepository.save(sub);
    }

    public Optional<Subscription> getActiveSubscription(User user) {
        return subscriptionRepository
                .findByUserAndStatus(user, "ACTIVE")
                .stream()
                .filter(Subscription::isActive)
                .findFirst();
    }

    public List<Subscription> getAllForUser(User user) {
        return subscriptionRepository.findByUser(user);
    }

    public void cancel(Long subscriptionId) {
        subscriptionRepository.findById(subscriptionId).ifPresent(s -> {
            s.setStatus("CANCELLED");
            subscriptionRepository.save(s);
        });
    }
}