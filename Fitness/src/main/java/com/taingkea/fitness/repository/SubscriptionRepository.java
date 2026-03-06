package com.taingkea.fitness.repository;

import com.taingkea.fitness.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findTopByUserIdOrderByStartDateDesc(Long userId);
}