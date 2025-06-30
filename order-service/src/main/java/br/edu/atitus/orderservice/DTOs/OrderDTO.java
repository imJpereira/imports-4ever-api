package br.edu.atitus.orderservice.DTOs;

import java.util.List;
import java.util.UUID;

public class OrderDTO {

    private List<ItemOrderDTO> orderItems;

    public List<ItemOrderDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemOrderDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
