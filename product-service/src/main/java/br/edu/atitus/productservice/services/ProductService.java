package br.edu.atitus.productservice.services;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.edu.atitus.productservice.DTOs.ProductDTO;
import br.edu.atitus.productservice.entities.ProductEntity;

public interface ProductService {

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(UUID id);

    ProductEntity save(ProductEntity product);

    ProductEntity update(UUID id, ProductDTO updatedProduct);

    void delete(UUID id);

    List<ProductEntity> getNameLike(String name);

    List<ProductEntity> findByCategoryId(UUID categoryId);

    List<ProductEntity> findByTeamId(UUID teamId);

    List<ProductEntity> findBySportId(UUID sportId);

    List<ProductEntity> findHighlightProducts();
}
