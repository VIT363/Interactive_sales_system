package org.example.order;

import java.time.LocalDateTime;

public interface Order {

    LocalDateTime createdDateTime();

    String companyName();

    Integer amount();
}
