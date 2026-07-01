package com.canals.orders.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.canals.orders.dto.CreateOrderRequest;
import com.canals.orders.dto.OrderProductRequest;
import com.canals.orders.entity.Customer;
import com.canals.orders.entity.Order;
import com.canals.orders.entity.OrderProduct;
import com.canals.orders.entity.Product;
import com.canals.orders.entity.Warehouse;
import com.canals.orders.repository.CustomerRepository;
import com.canals.orders.repository.OrderRepository;
import com.canals.orders.repository.ProductRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final WarehouseService warehouseService;
    private final PaymentService paymentService;
    private final GeocodingService geocodingService;

    public OrderService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            WarehouseService warehouseService,
            PaymentService paymentService,
            GeocodingService geocodingService) {

        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.warehouseService = warehouseService;
        this.paymentService = paymentService;
        this.geocodingService = geocodingService;

    }

    @Transactional
    public String createOrder(CreateOrderRequest request) {

    	Customer customer = customerRepository.findByName(request.getCustomerName()).orElse(null);

    	if (customer == null) {
    	    customer = new Customer();
    	    customer.setName(request.getCustomerName());
    	    customer = customerRepository.save(customer);
    	}

        List<Product> requestedProducts = new ArrayList<>();

        List<OrderProduct> orderProducts = new ArrayList<>();

        double total = 0;

        for (OrderProductRequest item : request.getProducts()) {

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow();

            requestedProducts.add(product);

            total += product.getPrice() * item.getQuantity();

            OrderProduct op = new OrderProduct();

            op.setProduct(product);
            op.setQuantity(item.getQuantity());
            op.setUnitPrice(product.getPrice());

            orderProducts.add(op);

        }

        double[] coordinates = geocodingService.geocode(request.getAddress());

        Warehouse warehouse = warehouseService.findWarehouse(
                requestedProducts,
                coordinates[0],
                coordinates[1]);

        if (warehouse == null)
            return "No warehouse found.";

        boolean payment = paymentService.charge(
                request.getCreditCardNumber(),
                total,
                "Order");

        if (!payment)
            return "Payment failed.";

        Order order = new Order();

        order.setCustomer(customer);
        order.setAddress(request.getAddress());
        order.setWarehouse(warehouse);
        order.setStatus("PAID");
        order.setTotal(total);

        for (OrderProduct op : orderProducts) {

            op.setOrder(order);

        }

        order.setProducts(orderProducts);

        orderRepository.save(order);

        return "Order created successfully.";

    }

}