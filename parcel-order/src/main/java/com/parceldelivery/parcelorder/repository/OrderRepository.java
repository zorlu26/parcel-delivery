package com.parceldelivery.parcelorder.repository;

import com.parceldelivery.parcelorder.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndUserId(Long id, Long userId);

    List<Order> findByUserId(long userId);

    List<Order> findByCourierId(long courierId);

    Order findByIdAndCourierId(long orderId,long courierId);
}
