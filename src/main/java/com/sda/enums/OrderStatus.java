package com.sda.enums;

public enum OrderStatus {
    RECEIVED("Przyjęte"),
    IN_PROGRESS ("W trakcie realizacji"),
    IN_DELIVERY ("Wysłane"),
    DELIVERED ("Dostarczone"),
    CANCELLED("Anulowane");

    public String message;

    OrderStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
