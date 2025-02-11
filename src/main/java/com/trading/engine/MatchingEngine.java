package com.trading.engine;

import com.trading.orderbook.*;

public class MatchingEngine {
    private final OrderBook orderBook;

    public MatchingEngine(OrderBook orderBook) { this.orderBook = orderBook; }

    public void matchOrders() {
        while (true) {
            Order bestBid = orderBook.getBestBid();
            Order bestAsk = orderBook.getBestAsk();

            if (bestBid == null || bestAsk == null || bestBid.getPrice() < bestAsk.getPrice()) break;

            int quantityToMatch = Math.min(bestBid.getQuantity(), bestAsk.getQuantity());

            bestBid.setQuantity(bestBid.getQuantity() - quantityToMatch);
            bestAsk.setQuantity(bestAsk.getQuantity() - quantityToMatch);

            System.out.println("Executed trade: " + quantityToMatch + " @ " + bestAsk.getPrice());

            if (bestBid.getQuantity() == 0) orderBook.deleteOrder(bestBid.getId());
            if (bestAsk.getQuantity() == 0) orderBook.deleteOrder(bestAsk.getId());
        }
    }
}
