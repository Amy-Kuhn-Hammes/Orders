package com.canals.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canals.orders.entity.OrderProduct;


public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}