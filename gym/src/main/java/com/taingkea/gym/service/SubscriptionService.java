package com.taingkea.gym.service;

import com.taingkea.gym.model.Plan;
import com.taingkea.gym.model.Subscription;
import com.taingkea.gym.model.User;
import com.taingkea.gym.repository.SubscriptionRepository;
import com.taingkea.gym.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public Subscription getLatestSubscription(Long userId) {
        return subscriptionRepository.findTopByUserIdOrderByStartDateDesc(userId);
    }

    @Transactional
    public void subscribe(Long userId, Plan plan) {  // ← String changed to Plan
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Subscription existing = subscriptionRepository.findTopByUserIdOrderByStartDateDesc(userId);
        if (existing != null && existing.getEndDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("User already has an active subscription.");
        }

        LocalDate start = LocalDate.now();

        Subscription sub = new Subscription();
        sub.setUser(user);
        sub.setPlan(plan);  // pass the enum directly        // ← store enum name as String in DB
        sub.setStartDate(start);
        sub.setEndDate(plan.calcEndDate(start));  // ← now works correctly

        subscriptionRepository.save(sub);
    }
}