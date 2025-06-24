package br.edu.atitus.productservice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.edu.atitus.productservice.DTOs.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.atitus.productservice.entities.ProductEntity;
import br.edu.atitus.productservice.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<ProductEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ProductEntity> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        return repository.save(product);
    }

    @Override
    public ProductEntity update(UUID id, ProductDTO updatedProduct) {
        ProductEntity existing = repository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setUrl(updatedProduct.getUrl());
        existing.setValue(updatedProduct.getValue());
        existing.setDiscountValue(updatedProduct.getDiscountValue());
        existing.setStatus(updatedProduct.getStatus());
        existing.setTeam(updatedProduct.getTeam());
        existing.setCategory(updatedProduct.getCategory());
        existing.setSport(updatedProduct.getSport());
        existing.setHighlight(updatedProduct.getHighlight());  // importante

        existing.setUpdatedAt(LocalDateTime.now());

        return repository.save(existing);
    }


    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProductEntity> getNameLike(String name) {
        return repository.findByNameContaining(name);
    }
}
