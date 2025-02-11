# Limit Order Book & Matching Engine Design - RMB - Ndumiso Mathe

## Overview
This project is a Java SE implementation of a Limit Order Book (LOB) and Matching Engine 

## System Components
- **Order**: Represents a buy or sell order with a unique ID, price, quantity, and side.
- **OrderBook**: Manages the storage of orders using data structures optimized for efficient sorting and retrieval.
- **MatchingEngine**: Processes orders from the OrderBook, matches buy and sell orders, and executes trades.

## Data Structures & Design Choices

### 1. Order Storage: `TreeMap` + `LinkedList`
- **TreeMap<Double, LinkedList<Order>>**
  - **Purpose**: Automatically sorts orders by price.
  - **Usage**: 
    - For **buy orders**, the TreeMap is sorted in **descending order** (highest price first).
    - For **sell orders**, the TreeMap is sorted in **ascending order** (lowest price first).
  - **Big O Complexity**:
    - **Insertion/Deletion/Search**: O(log n) per price level.
  
- **LinkedList<Order>**
  - **Purpose**: Maintains FIFO order for orders at the same price level.
  - **Big O Complexity**:
    - **Insertion/Deletion at head or tail**: O(1)

### 2. Unique Order ID Generation: `AtomicLong`
- **AtomicLong**
  - **Purpose**: Generates unique IDs for orders in a thread-safe manner.
  - **Big O Complexity**: O(1)

## Operation Complexities

| **Operation**        | **Data Structures**                  | **Big O Complexity**         | **Explanation**                                                                        |
|----------------------|--------------------------------------|------------------------------|----------------------------------------------------------------------------------------|
| **Add Order**        | TreeMap + LinkedList                 | O(log n)                     | Inserting into the TreeMap is O(log n) and adding to the LinkedList is O(1).             |
| **Delete Order**     | TreeMap + LinkedList                 | O(log n) + O(m)              | O(log n) to locate the price level and O(m) for removal from the list (where m is orders at that level). |
| **Modify Order**     | Remove & Reinsert in TreeMap + LinkedList | O(log n)                     | Removing and re-adding the order resets its priority while maintaining sorted order.   |
| **Match Orders**     | TreeMap Iteration + LinkedList       | O(log n) + O(m)              | O(log n) to find the best bid/ask, and O(m) to iterate through orders at a price level.  |

## Optimization & Performance

- **Automatic Sorting**: Using `TreeMap` ensures that orders are always sorted by price, which is crucial for fast retrieval of the best bid/ask orders.
- **FIFO Maintenance**: `LinkedList` guarantees that orders at the same price level are processed in the order they were received, ensuring fairness.
- **Efficient Lookup and Updates**: Combining a `TreeMap` for price-based organization with a `LinkedList` for order management minimizes overhead during insertion, deletion, and matching.
- **Thread Safety**: `AtomicLong` is used for unique order ID generation to safely handle concurrent operations.
- **Overall Efficiency**: With operations such as adding, deleting, modifying, and matching orders 

## Overview Testing 
This section verifies that the core components—the OrderBook and the MatchingEngine—work correctly.

## OrderBook Tests
These tests focus on the OrderBook component and cover:

- **Adding Orders**: Verifies that when a new order is added, the OrderBook correctly registers it. It checks that the best bid (for buy orders) and best ask (for sell orders) are correctly identified.
  
- **Modifying Orders**: Ensures that when an order is modified (e.g., changing the quantity), the update is properly reflected in the OrderBook. Since modifying an order causes it to lose its original priority, the tests confirm that this reordering happens as expected.
  
- **Deleting Orders**: Confirms that when an order is deleted, it is completely removed from the OrderBook and that the best bid or ask is updated appropriately.
  
- **Retrieving Best Bid and Ask**: Checks that the OrderBook can accurately return the best available buy (bid) and sell (ask) orders after multiple operations have been performed.

## MatchingEngine Tests
These tests target the MatchingEngine component and simulate realistic trading scenarios:

- **Full Order Matching**: Validates that when matching buy and sell orders have equal quantities at the same price, a complete trade is executed, and the orders are removed or updated correctly.
  
- **Partial Order Matching**: Tests scenarios where one order's quantity exceeds the other’s. It verifies that the MatchingEngine handles partial fills correctly, updating the remaining order with the correct quantity.
  
- **No Matching Due to Spread**: Ensures that when there is a price spread (i.e., the best bid is lower than the best ask), no matching occurs, and both orders remain in the OrderBook.
  
- **High-Volume Stress Test**: Simulates the addition of a large number of random buy and sell orders to test the system's performance under heavy load. 
