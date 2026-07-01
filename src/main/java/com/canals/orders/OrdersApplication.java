package com.canals.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;

import com.canals.orders.entity.Product;
import com.canals.orders.entity.Warehouse;
import com.canals.orders.repository.ProductRepository;
import com.canals.orders.repository.WarehouseRepository;



@SpringBootApplication
public class OrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository) {

        return args -> {

            if (productRepository.count() > 0) {
                return;
            }

            Product keyboard = new Product();
            keyboard.setName("Keyboard");
            keyboard.setPrice(100);

            Product mouse = new Product();
            mouse.setName("Mouse");
            mouse.setPrice(50);

            Product monitor = new Product();
            monitor.setName("Monitor");
            monitor.setPrice(800);

            productRepository.save(keyboard);
            productRepository.save(mouse);
            productRepository.save(monitor);

            Warehouse warehouse1 = new Warehouse();
            warehouse1.setName("Warehouse A");
            warehouse1.setLatitude(-31.76);
            warehouse1.setLongitude(-52.33);
            warehouse1.setProducts(Set.of(keyboard, mouse));

            Warehouse warehouse2 = new Warehouse();
            warehouse2.setName("Warehouse B");
            warehouse2.setLatitude(-30.03);
            warehouse2.setLongitude(-51.23);
            warehouse2.setProducts(Set.of(keyboard, mouse, monitor));

            warehouseRepository.save(warehouse1);
            warehouseRepository.save(warehouse2);

        };
    }
}