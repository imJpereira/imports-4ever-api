package br.edu.atitus.teamservice.services;

import br.edu.atitus.teamservice.DTOs.TeamDTO;
import br.edu.atitus.teamservice.entities.TeamEntity;
import br.edu.atitus.teamservice.repositories.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamEntity createTeam(TeamEntity team) throws Exception {

        if (team.getName() == null) throw new IllegalArgumentException("Nome do time inválido");

        team.setCreated_at(Instant.now());

        teamRepository.save(team);
        return team;
    }

    public List<TeamEntity> getNameLike(String name) throws Exception {
        return teamRepository.findByNameContaining(name);
    }

    public TeamEntity getTeamById(UUID teamId) throws Exception {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Time não encontrado!"));
    }

    public List<TeamEntity> getTeams() throws Exception {
        return teamRepository.findAll();
    }

    public void deleteTeamById(UUID teamId) throws Exception {
        teamRepository.deleteById(teamId);
    }

    public TeamEntity updateTeam(UUID teamId, TeamDTO teamEdited) throws Exception {
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Time não encontrado!"));

        if (teamEdited.getName().isEmpty()) throw new IllegalArgumentException("Nome inválido!");   

        team.setName(teamEdited.getName());
        teamRepository.save(team);

        return team;
    }
}
