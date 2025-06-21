package br.edu.atitus.sportservice.services;

import br.edu.atitus.sportservice.DTOs.SportDTO;
import br.edu.atitus.sportservice.entities.SportEntity;
import br.edu.atitus.sportservice.repositories.SportRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class SportService {

    private final SportRepository sportRepository;

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public SportEntity createSport(SportEntity sport) throws Exception {
        if (sport.getName() == null || sport.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome do esporte inválido.");
        }
        sport.setCreatedAt(Instant.now());
        return sportRepository.save(sport);
    }

    public SportEntity getSportById(UUID sportId) throws Exception {
        return sportRepository.findById(sportId)
                .orElseThrow(() -> new IllegalArgumentException("Esporte não encontrado."));
    }

    public List<SportEntity> getAllSports() {
        return sportRepository.findAll();
    }

    public List<SportEntity> getNameLike(String name) {
        return sportRepository.findByNameContaining(name);
    }

    public void deleteSport(UUID sportId) {
        sportRepository.deleteById(sportId);
    }

    public SportEntity updateSport(UUID sportId, SportDTO sportDTO) throws Exception {
        var sport = sportRepository.findById(sportId)
                .orElseThrow(() -> new IllegalArgumentException("Esporte não encontrado."));

        if (sportDTO.getName() == null || sportDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
        }

        sport.setName(sportDTO.getName());
        return sportRepository.save(sport);
    }
}
