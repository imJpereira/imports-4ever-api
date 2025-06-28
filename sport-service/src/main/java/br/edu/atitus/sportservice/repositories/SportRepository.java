package br.edu.atitus.sportservice.repositories;

import br.edu.atitus.sportservice.entities.SportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SportRepository extends JpaRepository<SportEntity, UUID> {

    List<SportEntity> findByNameContaining(String name);

    List<SportEntity> findByNameContainingIgnoreCase(String name);
}
