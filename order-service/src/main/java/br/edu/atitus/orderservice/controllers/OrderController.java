package br.edu.atitus.orderservice.controllers;

import br.edu.atitus.orderservice.DTOs.ItemOrderDTO;
import br.edu.atitus.orderservice.DTOs.OrderDTO;
import br.edu.atitus.orderservice.clients.ProductClient;
import br.edu.atitus.orderservice.clients.ProductResponse;
import br.edu.atitus.orderservice.entities.ItemOrderEntity;
import br.edu.atitus.orderservice.entities.OrderEntity;
import br.edu.atitus.orderservice.services.ItemOrderService;
import br.edu.atitus.orderservice.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ws/orders")
public class OrderController {

    private final ItemOrderService itemOrderService;
    private final OrderService orderService;
    private final ProductClient productClient;

    public OrderController(ItemOrderService itemOrderService, OrderService orderService, ProductClient productClient) {
        this.itemOrderService = itemOrderService;
        this.orderService = orderService;
        this.productClient = productClient;
    }

    @Operation(description = "Cria novo pedido")
    @PostMapping("/create")
    public ResponseEntity<OrderEntity> create(
            @RequestBody OrderDTO orderDTO,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception {

        OrderEntity newOrder = new OrderEntity();
        BeanUtils.copyProperties(orderDTO, newOrder);

        newOrder.setCustomerId(userId);
        orderService.create(newOrder);

        List<ItemOrderEntity> newOrderItems = new ArrayList<>();
        double total = 0;
        for (ItemOrderDTO itemOrderDTO : orderDTO.getOrderItems()) {
            ItemOrderEntity itemOrderEntity = new ItemOrderEntity();

            BeanUtils.copyProperties(itemOrderDTO, itemOrderEntity);
            itemOrderEntity.setOrder(newOrder);

            ProductResponse product = productClient.getProduct(itemOrderEntity.getProductId());

            if (product != null) {
                itemOrderEntity.setUnitPrice(product.getValue());
                itemOrderEntity.setProductName(product.getName());
            } else {
                itemOrderEntity.setUnitPrice(0);
            }

            itemOrderEntity.setTotalPrice(itemOrderEntity.getUnitPrice() * itemOrderEntity.getQuantity());

            newOrderItems.add(itemOrderEntity);
            total = total + itemOrderEntity.getTotalPrice();
        }

        itemOrderService.addItems(newOrder, newOrderItems);

        newOrder.setOrder_items(newOrderItems);
        newOrder.setTotal(total);

        orderService.update(newOrder);

        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @Operation(description = "Pega todos os pedidos do usuário logado")
    @GetMapping()
    public ResponseEntity<List<OrderEntity>> getAll(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception {

        List<OrderEntity> orders = orderService.findAll(userId);

        if (userType != 0 ) {
            for (OrderEntity order : orders) {
                for (ItemOrderEntity item : order.getOrder_items()) {
                    ProductResponse product = productClient.getProduct(item.getProductId());
                    item.setProductName(product.getName());
                }
            }
            return ResponseEntity.ok(orderService.findAll(userId));
        }

        for (OrderEntity order : orders) {
            for (ItemOrderEntity item : order.getOrder_items()) {
                ProductResponse product = productClient.getProduct(item.getProductId());
                item.setProductName(product.getName());
            }
        }

        return ResponseEntity.ok(orders);
    }

    @Operation(description = "Pega um pedido")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> getById(@PathVariable UUID orderId) throws Exception {

        OrderEntity order = orderService.findById(orderId);

        for (ItemOrderEntity item : order.getOrder_items()) {
            ProductResponse product = productClient.getProduct(item.getProductId());
            item.setProductName(product.getName());
        }

        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/delete/{orderId}")
    public void delete(@PathVariable UUID orderId) throws Exception {
        orderService.delete(orderId);
    }
}
