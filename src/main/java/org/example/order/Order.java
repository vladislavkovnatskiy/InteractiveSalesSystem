package org.example.order;

import java.time.LocalDateTime;

public class Order {
    private final LocalDateTime orderDate;
    private final String company;
    private final int quantityKg;

    public Order(LocalDateTime orderDate, String company, int quantityKg) {
        this.orderDate = orderDate;
        this.company = company;
        this.quantityKg = quantityKg;
    }

    public LocalDateTime getOrderDate() {return orderDate;}
    public String getCompany() {return company;}
    public int getQuantityKg() {return quantityKg;}
}
