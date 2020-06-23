package com.sda.enums;

public enum OrderStatus {
    ACCEPTED ("Przyjęte"),
    IN_PROGRESS ("W trakcie realizacji"),
    SHIPPED ("Wysłane"),
    DELIVERED ("Dostarczone");

    public String message;

    OrderStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
