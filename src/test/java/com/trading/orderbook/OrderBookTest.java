package com.trading.orderbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderBookTest {
    private OrderBook orderBook;

    @BeforeEach
    void setUp() {
        orderBook = new OrderBook();
    }

    @Test
    void testAddOrder() {
        Order order = new Order(100.5, 10, true); // Buy order
        orderBook.addOrder(order);

        assertEquals(100.5, orderBook.getBestBid().getPrice());
        assertEquals(10, orderBook.getBestBid().getQuantity());
    }

    @Test
    void testModifyOrder() {
        Order order = new Order(100.5, 10, true);
        orderBook.addOrder(order);
        orderBook.modifyOrder(order.getId(), 5);

        assertEquals(5, orderBook.getBestBid().getQuantity());
    }

    @Test
    void testDeleteOrder() {
        Order order1 = new Order(100.5, 10, true);
        orderBook.addOrder(order1);
        orderBook.deleteOrder(order1.getId());

        assertNull(orderBook.getBestBid());
    }

    @Test
    void testBestBidAndAsk() {
        orderBook.addOrder(new Order(99.0, 5, true));  // Buy @ 99
        orderBook.addOrder(new Order(101.0, 5, false)); // Sell @ 101

        assertEquals(99.0, orderBook.getBestBid().getPrice());
        assertEquals(101.0, orderBook.getBestAsk().getPrice());
    }
}
