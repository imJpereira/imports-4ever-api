package br.edu.atitus.productservice.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.edu.atitus.productservice.DTOs.ProductDTO;
import br.edu.atitus.productservice.clients.CurrencyClient;
import br.edu.atitus.productservice.clients.CurrencyResponse;
import br.edu.atitus.productservice.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import br.edu.atitus.productservice.entities.ProductEntity;
import br.edu.atitus.productservice.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CurrencyClient currencyClient;
    private final CacheManager cacheManager;

    public ProductServiceImpl(ProductRepository repository, CurrencyClient currencyClient, CacheManager cacheManager) {
        this.repository = repository;
        this.currencyClient = currencyClient;
        this.cacheManager = cacheManager;
    }

    @Override
    public List<ProductEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ProductEntity> findById(UUID id, String targetCurrency) throws ProductNotFoundException {
        targetCurrency = targetCurrency.toUpperCase();
        String nameCache = "Product";
        String keyCache = id + "_" + targetCurrency;

            ProductEntity product = null; // cacheManager.getCache(nameCache).get(keyCache, ProductEntity.class);

        if (product == null) {
            product = repository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));

            if (targetCurrency.equals(product.getCurrency())) {
                product.setConvertedPrice(product.getValue());
            } else {
                CurrencyResponse currency = currencyClient.getCurrency(product.getValue(), product.getCurrency(), targetCurrency);

                if (currency != null) {
                    product.setConvertedPrice(currency.getConvertedValue());
                } else {
                    product.setConvertedPrice(-1);
                }
            }

            cacheManager.getCache(nameCache).put(keyCache, product);
        }

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
        return repository.findByNameContainingIgnoreCase((name));
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
