package br.edu.atitus.teamservice.controllers;

import br.edu.atitus.teamservice.DTOs.TeamDTO;
import br.edu.atitus.teamservice.entities.TeamEntity;
import br.edu.atitus.teamservice.services.TeamService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.flywaydb.core.internal.publishing.PublishingConfigurationExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ws/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    private boolean validadeUserType(Long userType) {
        return userType != 0;
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

    @PostMapping("/create")
    public ResponseEntity<TeamEntity> createTeam(
            @RequestBody TeamDTO teamDTO,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception {
        if (validadeUserType(userType)) throw new AuthenticationException("Usuário não tem nível de acesso");

        TeamEntity newTeam = new TeamEntity();
        BeanUtils.copyProperties(teamDTO, newTeam);

        var createdTeam = teamService.createTeam(newTeam);
        return ResponseEntity.ok(createdTeam);
    }

    @DeleteMapping("/delete/{teamId}")
    public void deleteTeam(
            @PathVariable UUID teamId,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception {
        if (validadeUserType(userType)) throw new AuthenticationException("Usuário não tem nível de acesso");
        teamService.deleteTeamById(teamId);
    }

    @PutMapping("/update/{teamId}")
    public TeamEntity updateTeam(
            @PathVariable UUID teamId,
            @RequestBody TeamDTO teamDTO,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception {
        if (validadeUserType(userType)) throw new AuthenticationException("Usuário não tem nível de acesso");
        return  teamService.updateTeam(teamId, teamDTO);
    }


}
