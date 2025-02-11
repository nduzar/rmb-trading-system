package com.trading.orderbook;

public class Order {
    private static long idCounter = 1;
    private final long id;
    private final double price;
    private int quantity;
    private final boolean isBuy;

    public Order(double price, int quantity, boolean isBuy) {
        this.id = idCounter++;
        this.price = price;
        this.quantity = quantity;
        this.isBuy = isBuy;
    }

    public long getId() { return id; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public boolean isBuy() { return isBuy; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
