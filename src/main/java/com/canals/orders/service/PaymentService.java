package com.canals.orders.service;


import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean charge(
            String creditCardNumber,
            double amount,
            String description) {

        // Mock

        return true;

    }


}