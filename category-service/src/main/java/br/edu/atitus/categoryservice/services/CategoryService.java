package br.edu.atitus.categoryservice.services;

import br.edu.atitus.categoryservice.DTOs.CategoryDTO;
import br.edu.atitus.categoryservice.entities.CategoryEntity;
import br.edu.atitus.categoryservice.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity create(CategoryEntity category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório.");
        }
        return categoryRepository.save(category);
    }

    public List<CategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    public CategoryEntity getById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));
    }

    public List<CategoryEntity> getByNameLike(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    public void deleteById(UUID id) {
        categoryRepository.deleteById(id);
    }

    public CategoryEntity update(UUID id, CategoryDTO dto) {
        CategoryEntity category = getById(id);
        category.setName(dto.getName());
        category.setUrl(dto.getUrl());
        return categoryRepository.save(category);
    }
}
