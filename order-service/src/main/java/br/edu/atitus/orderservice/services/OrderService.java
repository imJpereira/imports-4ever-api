package br.edu.atitus.orderservice.services;

import br.edu.atitus.orderservice.entities.OrderEntity;
import br.edu.atitus.orderservice.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderEntity create(OrderEntity orderEntity) throws Exception {
        //validaçoẽs

        orderEntity.setTotal(0);
        orderEntity.setOrderDate(LocalDateTime.now());

        orderRepository.save(orderEntity);

        return orderEntity;
    }

    public List<OrderEntity> findAll(Long customerId) throws Exception {
        return orderRepository.findAllByCustomerId(customerId);
    }

    public List<OrderEntity> findAll() throws Exception {
        return orderRepository.findAll();
    }

    public OrderEntity findById(UUID orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public OrderEntity update(OrderEntity orderEntity) throws Exception {
        return orderRepository.save(orderEntity);
    }

    public void delete(UUID orderId) throws Exception {
        orderRepository.deleteById(orderId);
    }



}
