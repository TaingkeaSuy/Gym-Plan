package com.taingkea.fitness.service;

import com.taingkea.fitness.model.Subscription;
import com.taingkea.fitness.model.User;
import com.taingkea.fitness.repository.SubscriptionRepository;
import com.taingkea.fitness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    public Subscription getLatestSubscription(Long userId) {
        return subscriptionRepository.findTopByUserIdOrderByStartDateDesc(userId);
    }

    public void subscribe(Long userId, String plan) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return;

        LocalDate start = LocalDate.now();
        LocalDate end;

        if (plan.equals("starter")) {
            end = start.plusMonths(1);
        } else if (plan.equals("pro")) {
            end = start.plusMonths(3);
        } else {
            end = start.plusYears(1);
        }

        Subscription sub = new Subscription();
        sub.setUser(user);
        sub.setPlan(plan);
        sub.setStartDate(start);
        sub.setEndDate(end);

        subscriptionRepository.save(sub);
    }
}