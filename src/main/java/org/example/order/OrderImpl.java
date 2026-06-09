package org.example.order;

import java.time.LocalDateTime;

public record OrderImpl(LocalDateTime createdDateTime, String companyName, Integer amount) {

}
