package br.edu.atitus.productservice.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.edu.atitus.productservice.DTOs.ProductDTO;
import br.edu.atitus.productservice.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.atitus.productservice.entities.ProductEntity;
import br.edu.atitus.productservice.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

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
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<ProductEntity> findByCategoryId(UUID categoryId) {
        return repository.findAllByCategory(categoryId);
    }

    @Override
    public List<ProductEntity> findByTeamId(UUID teamId) {
        return repository.findAllByTeam(teamId);
    }

    @Override
    public List<ProductEntity> findBySportId(UUID sportId) {
        return repository.findAllBySport(sportId);
    }

    @Override
    public List<ProductEntity> findHighlightProducts() {
        return repository.findByHighlight(true);
    }
}
