package com.sda.enums;

public enum Delivery {
    PICKUP_AS_STORE("Odbiór osobisty", 0d),
    PICKUP_AT_HOME("Dostawa do domu", 8d);

    String name;
    Double price;

    Delivery(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
