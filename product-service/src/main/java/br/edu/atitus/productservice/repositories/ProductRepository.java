package br.edu.atitus.productservice.repositories;

import br.edu.atitus.productservice.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByNameContaining(String name);

    List<ProductEntity> findAllByCategory(UUID category);

    List<ProductEntity> findAllByTeam(UUID team);

    List<ProductEntity> findAllBySport(UUID sport);

    List<ProductEntity> findByHighlight(Boolean highlight);
}
