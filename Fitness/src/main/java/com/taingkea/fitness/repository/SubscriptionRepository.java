package com.taingkea.fitness.repository;

import com.taingkea.fitness.model.Subscription;
import com.taingkea.fitness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUser(User user);

    List<Subscription> findByUserAndStatus(User user, String status);
}