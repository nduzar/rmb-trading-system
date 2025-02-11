package com.trading.orderbook;

import java.util.*;

public class OrderBook {
    private final TreeMap<Double, LinkedList<Order>> buyOrders = new TreeMap<>(Collections.reverseOrder());
    private final TreeMap<Double, LinkedList<Order>> sellOrders = new TreeMap<>();

    public void addOrder(Order order) {
        var orders = order.isBuy() ? buyOrders : sellOrders;
        orders.computeIfAbsent(order.getPrice(), k -> new LinkedList<>()).add(order);
    }

    public void modifyOrder(long orderId, int newQuantity) {
        for (var orders : List.of(buyOrders, sellOrders)) {
            for (var entry : orders.entrySet()) {
                LinkedList<Order> queue = entry.getValue();
                for (Order order : queue) {
                    if (order.getId() == orderId) {
                        queue.remove(order);
                        order.setQuantity(newQuantity);
                        queue.addLast(order);  // Loses priority
                        return;
                    }
                }
            }
        }
    }

    public void deleteOrder(long orderId) {
        for (var orders : List.of(buyOrders, sellOrders)) {
            for (var entry : orders.entrySet()) {
                LinkedList<Order> queue = entry.getValue();
                queue.removeIf(order -> order.getId() == orderId);
                if (queue.isEmpty()) orders.remove(entry.getKey());
            }
        }
    }

    public Order getBestBid() { return buyOrders.isEmpty() ? null : buyOrders.firstEntry().getValue().getFirst(); }
    public Order getBestAsk() { return sellOrders.isEmpty() ? null : sellOrders.firstEntry().getValue().getFirst(); }
}
