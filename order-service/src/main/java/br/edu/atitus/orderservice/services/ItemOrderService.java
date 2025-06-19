package br.edu.atitus.orderservice.services;

import br.edu.atitus.orderservice.entities.ItemOrderEntity;
import br.edu.atitus.orderservice.entities.OrderEntity;
import br.edu.atitus.orderservice.repositories.ItemOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemOrderService {

    private final ItemOrderRepository itemOrderRepository;

    public ItemOrderService(ItemOrderRepository itemOrderRepository) {
        this.itemOrderRepository = itemOrderRepository;
    }

    public List<ItemOrderEntity> addItems(OrderEntity orderEntity, List<ItemOrderEntity> itemOrderEntities ) {

        itemOrderRepository.saveAll(itemOrderEntities);
        return itemOrderEntities;
    }

}
