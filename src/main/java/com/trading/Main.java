package com.trading;

import com.trading.orderbook.*;
import com.trading.engine.*;

public class Main {
    public static void main(String[] args) {
        OrderBook orderBook = new OrderBook();
        MatchingEngine engine = new MatchingEngine(orderBook);

        orderBook.addOrder(new Order(10.0, 5, true));  // Buy order
        orderBook.addOrder(new Order(10.0, 10, false)); // Sell order

        System.out.println("Before matching:");
        System.out.println("Best Bid: " + orderBook.getBestBid().getPrice());
        System.out.println("Best Ask: " + orderBook.getBestAsk().getPrice());

        engine.matchOrders();

        System.out.println("After matching:");
        System.out.println("Best Bid: " + (orderBook.getBestBid() != null ? orderBook.getBestBid().getPrice() : "None"));
        System.out.println("Best Ask: " + (orderBook.getBestAsk() != null ? orderBook.getBestAsk().getPrice() : "None"));
    }
}
