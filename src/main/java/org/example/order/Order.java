package org.example.order;

import java.time.LocalDateTime;

public record Order(LocalDateTime createdDateTime, String companyName, Integer amount) {

}
