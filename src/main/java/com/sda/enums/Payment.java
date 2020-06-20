package com.sda.enums;

public enum Payment {
    CASH("Gotówka przy odbiorze"), VISA ("Kartą przy odbiorze");

    String name;

    Payment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
