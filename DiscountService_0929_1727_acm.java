// 代码生成时间: 2025-09-29 17:27:23
package com.example.discount;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DiscountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountService.class);

    public double applyDiscount(double originalPrice, double discountRate) {
        // Check for invalid discount rate
        if (discountRate < 0 || discountRate > 1) {
            LOGGER.error("Invalid discount rate: {}", discountRate);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid discount rate");
        }

        // Calculate discounted price
        double discountedPrice = originalPrice * (1 - discountRate);
        LOGGER.info("Original price: {}, Discount rate: {}, Discounted price: {}", originalPrice, discountRate, discountedPrice);

        return discountedPrice;
    }

    // Additional methods for discount promotions can be added here
    
    // Example of an additional method to check promotion eligibility
    public boolean isEligibleForPromotion(String customerType) {
        // Assume we have a list of eligible customer types
        String[] eligibleTypes = {"VIP", "Gold", "Silver"};
        for (String type : eligibleTypes) {
            if (type.equals(customerType)) {
                LOGGER.info("Customer type {} is eligible for promotions", customerType);
                return true;
            }
        }
        LOGGER.info("Customer type {} is not eligible for promotions", customerType);
        return false;
    }
}
