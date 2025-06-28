package br.edu.atitus.categoryservice.controllers;

import br.edu.atitus.categoryservice.DTOs.CategoryDTO;
import br.edu.atitus.categoryservice.entities.CategoryEntity;
import br.edu.atitus.categoryservice.services.CategoryService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ws/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    private boolean validadeUserType(Long userType) {
        return userType != 0;
    }

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/like/{name}")
    public ResponseEntity<List<CategoryEntity>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getByNameLike(name));
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryEntity> create(
            @RequestBody CategoryDTO dto,
            @PathVariable UUID teamId,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception {
        if (validadeUserType(userType)) throw new AuthenticationException("Usuário não tem nível de acesso");

        CategoryEntity entity = new CategoryEntity();
        BeanUtils.copyProperties(dto, entity);
        return ResponseEntity.ok(service.create(entity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception {
        if (validadeUserType(userType)) throw new AuthenticationException("Usuário não tem nível de acesso");
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryEntity> update(
            @PathVariable UUID id,
            @RequestBody CategoryDTO dto,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception {
        if (validadeUserType(userType)) throw new AuthenticationException("Usuário não tem nível de acesso");
        return ResponseEntity.ok(service.update(id, dto));
    }
}
