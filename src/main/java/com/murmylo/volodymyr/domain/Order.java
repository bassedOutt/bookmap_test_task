package com.murmylo.volodymyr.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    private Integer price = 0;
    private Integer size = 0;
    private OrderType type;

    @Override
    public String toString() {
        return price + "," + size;
    }

    public enum OrderType {
        //There are people willing to buy at this price
        BID,
        //There are people willing to sell at this price
        ASK,
        SPREAD;

        public static OrderType fromString(String orderType) {
            if (BID.name().equalsIgnoreCase(orderType)) return BID;
            else if (ASK.name().equalsIgnoreCase(orderType)) return ASK;
            return null;
        }
    }
}
