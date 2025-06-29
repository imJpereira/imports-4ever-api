package br.edu.atitus.productservice.controllers;

import br.edu.atitus.productservice.DTOs.ProductDTO;
import br.edu.atitus.productservice.clients.CurrencyClient;
import br.edu.atitus.productservice.clients.CurrencyResponse;
import br.edu.atitus.productservice.exceptions.UnauthorizedException;
import feign.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.atitus.productservice.entities.ProductEntity;
import br.edu.atitus.productservice.services.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ws/products")
public class OpenProductController {
    private final  ProductService service;

    public OpenProductController(ProductService service) {
        this.service = service;
    }

    private boolean validadeUserType(Long userType) {
        return userType != 0;
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}/{targetCurrency}")
    public ResponseEntity<ProductEntity> getById(@PathVariable UUID id, @PathVariable String targetCurrency) {
        return service.findById(id,targetCurrency)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/like/{name}")
    public ResponseEntity<List<ProductEntity>> getNameLike(@PathVariable String name) {
        return ResponseEntity.ok(service.getNameLike(name));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductEntity>> findByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(service.findByCategoryId(categoryId));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<ProductEntity>> findByTeam(@PathVariable UUID teamId) {
        return ResponseEntity.ok(service.findByTeamId(teamId));
    }

    @GetMapping("/sport/{sportId}")
    public ResponseEntity<List<ProductEntity>> findBySport(@PathVariable UUID sportId) {
        return ResponseEntity.ok(service.findBySportId(sportId));
    }

    @GetMapping("/highlight")
    public ResponseEntity<List<ProductEntity>> findHighlight() throws Exception {
        return ResponseEntity.ok(service.findHighlightProducts());
    }

    @PostMapping("/create")
    public ResponseEntity<ProductEntity> create(
            @RequestBody ProductDTO productDTO,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws UnauthorizedException
    {
        if (validadeUserType(userType)) throw new UnauthorizedException();

        ProductEntity newProduct = new ProductEntity();
        BeanUtils.copyProperties(productDTO, newProduct);

        var createdProduct = service.save(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductEntity> update(
            @PathVariable UUID id,
            @RequestBody ProductDTO productDTO,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws UnauthorizedException
    {
        if (validadeUserType(userType)) throw new UnauthorizedException();

        var updatedProduct = service.update(id, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws UnauthorizedException
    {
        if (validadeUserType(userType)) throw new UnauthorizedException();

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
