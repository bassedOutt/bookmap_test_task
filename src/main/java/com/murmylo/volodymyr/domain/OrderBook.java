package com.murmylo.volodymyr.domain;


import com.murmylo.volodymyr.domain.Order.OrderType;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;


public class OrderBook {
    private final TreeMap<Integer, Order> bids = new TreeMap<>();
    private final TreeMap<Integer, Order> asks = new TreeMap<>();

    public void updateOrderBook(Order order) {
        if (order.getType() == OrderType.BID) {
            this.bids.put(order.getPrice(), order);
            this.asks.remove(order.getPrice());
        } else if (order.getType() == OrderType.ASK) {
            this.asks.put(order.getPrice(), order);
            this.bids.remove(order.getPrice());
        }
    }

    public Integer getSizeAtPrice(Integer price) {
        return Optional.ofNullable(bids.get(price))
                .map(Order::getSize)
                .orElseGet(() -> Optional.ofNullable(asks.get(price))
                        .map(Order::getSize)
                        .orElse(0));
    }

    public Order bestBid() {
        Map.Entry<Integer, Order> entry = bids.lastEntry();
        Order bestBid = entry.getValue();
        while (bestBid.getSize() == 0
                && (entry = bids.lowerEntry(entry.getKey())) != null) {
            bestBid = entry.getValue();
        }
        if (entry == null) {
            return bids.lastEntry().getValue();
        }
        return bestBid;
    }

    public Order bestAsk() {
        Map.Entry<Integer, Order> entry = asks.firstEntry();
        Order bestBid = entry.getValue();
        while (bestBid.getSize() == 0
                && (entry = asks.higherEntry(entry.getKey())) != null) {
            bestBid = entry.getValue();
        }
        if (entry == null) {
            return asks.firstEntry().getValue();
        }
        return bestBid;
    }

    public void sell(Integer size) {
        Order bestBid = bestBid();
        while (size != 0) {
            int newSize = bestBid.getSize() - size;
            if (newSize <= 0) {
                size = Math.abs(newSize);
                bids.remove(bestBid.getPrice());
                bestBid = bestBid();
            } else {
                size = 0;
                bestBid.setSize(newSize);
            }
        }
    }

    public void buy(Integer size) {
        Order bestAsk = bestAsk();
        while (size != 0) {
            int newSize = bestAsk.getSize() - size;
            if (newSize <= 0) {
                size = Math.abs(newSize);
                asks.remove(bestAsk.getPrice());
                bestAsk = bestAsk();
            } else {
                size = 0;
                bestAsk.setSize(newSize);
            }
        }
    }
}
