package br.edu.atitus.categoryservice.repositories;

import br.edu.atitus.categoryservice.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    List<CategoryEntity> findByNameContainingIgnoreCase(String name);
}
