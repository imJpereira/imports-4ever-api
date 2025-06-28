package br.edu.atitus.orderservice.DTOs;

import java.util.List;
import java.util.UUID;

public class OrderDTO {

    private Long customerId;
    private List<ItemOrderDTO> orderItems;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<ItemOrderDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemOrderDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
