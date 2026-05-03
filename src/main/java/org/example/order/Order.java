package org.example.order;

import java.time.LocalDateTime;

public record Order(LocalDateTime orderDate, String company, int quantityKg) {
}
