package br.edu.atitus.productservice.controllers;

import br.edu.atitus.productservice.DTOs.ProductDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.atitus.productservice.entities.ProductEntity;
import br.edu.atitus.productservice.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class OpenProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<ProductEntity> create(@RequestBody ProductDTO productDTO) {
        ProductEntity newProduct = new ProductEntity();
        BeanUtils.copyProperties(productDTO, newProduct);
        return ResponseEntity.ok(service.save(newProduct));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductEntity> update(@PathVariable UUID id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.update(id, productDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/like/{name}")
    public List<ProductEntity> getNameLike(@PathVariable String name) {
        return service.getNameLike(name);
    }
}
