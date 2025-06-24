package br.edu.atitus.orderservice.controllers;

import br.edu.atitus.orderservice.DTOs.ItemOrderDTO;
import br.edu.atitus.orderservice.DTOs.OrderDTO;
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
@RequestMapping("/orders")
public class OrderController {

    private final ItemOrderService itemOrderService;
    private final OrderService orderService;

    public OrderController(ItemOrderService itemOrderService, OrderService orderService) {
        this.itemOrderService = itemOrderService;
        this.orderService = orderService;
    }




    @Operation(description = "Cria novo pedido")
    @PostMapping("create")
    public ResponseEntity<OrderEntity> create(@RequestBody OrderDTO orderDTO)  throws Exception{

        OrderEntity newOrder = new OrderEntity();
        BeanUtils.copyProperties(orderDTO, newOrder);

        orderService.create(newOrder);


        List<ItemOrderEntity> newOrderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (ItemOrderDTO itemOrderDTO : orderDTO.getOrderItems()) {
            ItemOrderEntity itemOrderEntity = new ItemOrderEntity();

            BeanUtils.copyProperties(itemOrderDTO, itemOrderEntity);
            itemOrderEntity.setOrder(newOrder);

            //fazer requisição para o product-service para ter esses dados
            itemOrderEntity.setTotalPrice(BigDecimal.TEN);
            itemOrderEntity.setUnitPrice(BigDecimal.TEN);


            newOrderItems.add(itemOrderEntity);
            total = total.add(itemOrderEntity.getTotalPrice());
        }

        itemOrderService.addItems(newOrder, newOrderItems);

        newOrder.setOrder_items(newOrderItems);
        newOrder.setTotal(total);

        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @Operation(description = "Pega todos os pedidos do usuário logado")
    @GetMapping("/{customerId}")
    public ResponseEntity<List<OrderEntity>> getAll(@PathVariable UUID customerId) throws Exception {

        //buscar no user service para ver se o usuário é admin

        return ResponseEntity.ok(orderService.findAll(customerId));
    }

    @Operation(description = "Pega um pedido")
    @GetMapping("/{orderId}/{customerId}")
    public ResponseEntity<OrderEntity> getById(@PathVariable UUID orderId, @PathVariable UUID customerId) throws Exception {
        return ResponseEntity.ok(orderService.findById(orderId, customerId));
    }


}
