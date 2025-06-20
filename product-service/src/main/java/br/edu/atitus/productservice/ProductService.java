package br.edu.atitus.productservice;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.edu.atitus.productservice.entities.ProductEntity;

public interface ProductService {
    List<ProductEntity> findAll();
    Optional<ProductEntity> findById(UUID id);
    ProductEntity save(ProductEntity product);
    ProductEntity update(UUID id, ProductEntity product);
    void delete(UUID id);
}
