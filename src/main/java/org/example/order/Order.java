package org.example.order;

import java.time.LocalDateTime;

public record Order(LocalDateTime orderDate, String company, int quantityKg) {
    public Order(LocalDateTime orderDate, String company, int quantityKg) {
        if(orderDate.isBefore(LocalDateTime.now()) || company.isEmpty() || quantityKg < 1) {
            throw new IllegalArgumentException();
        }
        this.orderDate = orderDate;
        this.company = company;
        this.quantityKg = quantityKg;
    }
}
