package br.edu.atitus.productservice.controllers;

import br.edu.atitus.productservice.entities.ProductEntity;
import br.edu.atitus.productservice.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}/{targetCurrency}")
    public ResponseEntity<ProductEntity> getById(@PathVariable UUID id, @PathVariable String targetCurrency) {
        return service.findById(id,targetCurrency)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
