package com.canals.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canals.orders.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}