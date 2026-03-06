package com.taingkea.gym.repository;

import com.taingkea.gym.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findTopByUserIdOrderByStartDateDesc(Long userId);
}