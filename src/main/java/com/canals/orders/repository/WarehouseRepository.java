package com.canals.orders.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.canals.orders.entity.Warehouse;


public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}