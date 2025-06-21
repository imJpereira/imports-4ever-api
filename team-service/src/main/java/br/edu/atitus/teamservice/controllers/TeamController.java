package br.edu.atitus.teamservice.controllers;

import br.edu.atitus.teamservice.DTOs.TeamDTO;
import br.edu.atitus.teamservice.entities.TeamEntity;
import br.edu.atitus.teamservice.services.TeamService;
import org.flywaydb.core.internal.publishing.PublishingConfigurationExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("create")
    public ResponseEntity<TeamEntity> createTeam(@RequestBody TeamDTO teamDTO) throws Exception {
        TeamEntity newTeam = new TeamEntity();
        BeanUtils.copyProperties(teamDTO, newTeam);

        var createdTeam = teamService.createTeam(newTeam);

        return ResponseEntity.ok(createdTeam);
    }

    @GetMapping("/{teamid}")
    public ResponseEntity<TeamEntity> getTeamById(@PathVariable UUID teamid) throws Exception {
        return ResponseEntity.ok(teamService.getTeamById(teamid));
    }

    @GetMapping("/like/{name}")
    public ResponseEntity<List<TeamEntity>> getByNameLike(@PathVariable String name) throws Exception {
        return ResponseEntity.ok(teamService.getNameLike(name));
    }

    @GetMapping()
    public ResponseEntity<List<TeamEntity>> getTeams() throws Exception {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @DeleteMapping("/delete/{teamId}")
    public void deleteTeam(@PathVariable UUID teamId) throws Exception {
        teamService.deleteTeamById(teamId);
    }

    @PutMapping("/update/{teamId}")
    public TeamEntity updateTeam(@PathVariable UUID teamId, @RequestBody TeamDTO teamDTO) throws Exception {
        return  teamService.updateTeam(teamId, teamDTO);
    }


}
