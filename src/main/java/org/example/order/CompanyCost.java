package org.example.order;

public record CompanyCost(String company, Integer cost) {

    @Override
    public String toString() {
        return String.format("%s - %d", company, cost);
    }
}
