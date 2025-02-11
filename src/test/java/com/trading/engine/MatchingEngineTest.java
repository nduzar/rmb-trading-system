package com.trading.engine;
import java.util.Random;
import com.trading.orderbook.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchingEngineTest {
    private OrderBook orderBook;
    private MatchingEngine engine;

    @BeforeEach
    void setUp() {
        orderBook = new OrderBook();
        engine = new MatchingEngine(orderBook);
    }

    @Test
    void testMatchingOrders() {
        orderBook.addOrder(new Order(100.0, 10, true));  // Buy @ 100 (10 qty)
        orderBook.addOrder(new Order(100.0, 5, false));  // Sell @ 100 (5 qty)

        engine.matchOrders();

        assertEquals(5, orderBook.getBestBid().getQuantity());  // 5 remaining on buy side
        assertNull(orderBook.getBestAsk());  // No more asks left
    }

    @Test
    void testPartialMatching() {
        orderBook.addOrder(new Order(101.0, 10, true));  // Buy @ 101 (10 qty)
        orderBook.addOrder(new Order(101.0, 20, false)); // Sell @ 101 (20 qty)

        engine.matchOrders();

        assertEquals(10, orderBook.getBestAsk().getQuantity()); // 10 remaining on sell side
        assertNull(orderBook.getBestBid());  // No more buy orders left
    }

    @Test
    void testNoMatchingWhenSpreadExists() {
        orderBook.addOrder(new Order(100.0, 10, true));  // Buy @ 100
        orderBook.addOrder(new Order(105.0, 5, false));  // Sell @ 105

        engine.matchOrders();

        assertEquals(10, orderBook.getBestBid().getQuantity());  // No match, orders remain unchanged
        assertEquals(5, orderBook.getBestAsk().getQuantity());
    }

    @Test
    void testLargeNumberOfOrders() {
        Random random = new Random();

        // Adding 100 random buy orders
        for (int i = 0; i < 100; i++) {
            orderBook.addOrder(new Order(95.0 + random.nextDouble() * 10, random.nextInt(50) + 1, true));
        }

        // Adding 100 random sell orders
        for (int i = 0; i < 100; i++) {
            orderBook.addOrder(new Order(95.0 + random.nextDouble() * 10, random.nextInt(50) + 1, false));
        }

        System.out.println("Before Matching:");
        System.out.println("Best Bid: " + (orderBook.getBestBid() != null ? orderBook.getBestBid().getPrice() : "None"));
        System.out.println("Best Ask: " + (orderBook.getBestAsk() != null ? orderBook.getBestAsk().getPrice() : "None"));

        engine.matchOrders();

        System.out.println("After Matching:");
        System.out.println("Best Bid: " + (orderBook.getBestBid() != null ? orderBook.getBestBid().getPrice() : "None"));
        System.out.println("Best Ask: " + (orderBook.getBestAsk() != null ? orderBook.getBestAsk().getPrice() : "None"));
    }
}
