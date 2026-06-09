package org.example.order;

public record OrderInvoice(String company, Double cost) {

    @Override
    public String toString() {
        return String.format("%s - %.0f", company, cost);
    }
}
