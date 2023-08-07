package com.murmylo.volodymyr.service.impl;

import com.murmylo.volodymyr.domain.Order;
import com.murmylo.volodymyr.domain.OrderBook;
import com.murmylo.volodymyr.service.CommandExecutorService;
import com.murmylo.volodymyr.service.OutputService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandExecutorServiceImpl implements CommandExecutorService {
    private final OrderBook orderBook;

    private final OutputService outputService;

    public void execute(String line) {
        String[] elements = line.split(",");
        String operationType = elements[0];
        //price update
        switch (operationType) {
            case "u" -> executeUpdate(elements);
            case "q" -> executeQuery(elements);
            case "o" -> executeMarketOrder(elements);
        }
    }

    private void executeMarketOrder(String[] elements) {
        String marketOrderType = elements[1];
        int size = Integer.parseInt(elements[2]);
        if ("buy".equals(marketOrderType)) {
            orderBook.buy(size);
        } else if ("sell".equals(marketOrderType)) {
            orderBook.sell(size);
        }
    }

    private void executeUpdate(String[] elements) {
        Order order = Order.builder()
                .price(Integer.parseInt(elements[1]))
                .size(Integer.parseInt(elements[2]))
                .type(Order.OrderType.fromString(elements[3]))
                .build();
        orderBook.updateOrderBook(order);
    }

    private void executeQuery(String[] elements) {
        String queryType = elements[1];
        switch (queryType) {
            case "best_bid" -> outputService.write(orderBook.bestBid().toString());
            case "best_ask" -> outputService.write(orderBook.bestAsk().toString());
            case "size" -> {
                int price = Integer.parseInt(elements[2]);
                Integer sizeAtPrice = orderBook.getSizeAtPrice(price);
                if (sizeAtPrice != null) {
                    outputService.write(String.valueOf(sizeAtPrice));
                } else {
                    outputService.write("0");
                }
            }
        }
    }
}
