package com.sam.drools.samdroolspersistence;

import java.io.Serializable;

public class Order implements Serializable {

    private String name;
    private String cardType;
    private int discount;
    private int price;

    public Order(String cardType, int price) {
        this.cardType = cardType;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", cardType='" + cardType + '\'' +
                ", discount=" + discount +
                ", price=" + price +
                '}';
    }
}
