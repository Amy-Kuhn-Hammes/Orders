package com.canals.orders.service;

import org.springframework.stereotype.Service;

@Service
public class GeocodingService {

    public double[] geocode(String address) {

        // Mock

        return new double[] { -31.7654, -52.3376 };

    }

}
