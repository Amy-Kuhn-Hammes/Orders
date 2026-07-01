package com.canals.orders.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canals.orders.entity.Product;
import com.canals.orders.entity.Warehouse;
import com.canals.orders.repository.WarehouseRepository;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse findWarehouse(
            List<Product> products,
            double latitude,
            double longitude) {

        List<Warehouse> warehouses = warehouseRepository.findAll();

        Warehouse best = null;

        double bestDistance = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouses) {

            if (!warehouse.getProducts().containsAll(products))
                continue;

            double distance = Math.sqrt(
                    Math.pow(latitude - warehouse.getLatitude(), 2)
                  + Math.pow(longitude - warehouse.getLongitude(), 2));

            if (distance < bestDistance) {

                bestDistance = distance;
                best = warehouse;

            }

        }

        return best;

    }

}