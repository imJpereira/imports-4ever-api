package br.edu.atitus.orderservice.repositories;

import br.edu.atitus.orderservice.entities.ItemOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrderEntity, UUID> {
}
