package br.edu.atitus.teamservice.repositories;

import br.edu.atitus.teamservice.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {


    List<TeamEntity> findByNameContaining(String name);

    List<TeamEntity> findByNameContainingIgnoreCase(String name);
}
