package br.edu.atitus.sportservice.controllers;

import br.edu.atitus.sportservice.DTOs.SportDTO;
import br.edu.atitus.sportservice.entities.SportEntity;
import br.edu.atitus.sportservice.services.SportService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ws/sports")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    private boolean validadeUserType(Long userType) {
        return userType != 0;
    }

    @GetMapping("/{sportId}")
    public ResponseEntity<SportEntity> getSportById(@PathVariable UUID sportId) throws Exception {
        return ResponseEntity.ok(sportService.getSportById(sportId));
    }

    @GetMapping("/like/{name}")
    public ResponseEntity<List<SportEntity>> getNameLike(@PathVariable String name) {
        return ResponseEntity.ok(sportService.getNameLike(name));
    }

    @GetMapping
    public ResponseEntity<List<SportEntity>> getAllSports() {
        return ResponseEntity.ok(sportService.getAllSports());
    }

    @DeleteMapping("/delete/{sportId}")
    public void deleteSport(
            @PathVariable UUID sportId,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception
    {
        if (validadeUserType(userType)) throw new AuthenticationException("Usuário sem nível de acesso");
        sportService.deleteSport(sportId);
    }

    @PostMapping("/create")
    public ResponseEntity<SportEntity> createSport(
            @RequestBody SportDTO sportDTO,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception
    {
        if (validadeUserType(userType)) throw new AuthenticationException("Usuário sem nível de acesso");

        SportEntity sport = new SportEntity();
        BeanUtils.copyProperties(sportDTO, sport);

        var created = sportService.createSport(sport);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{sportId}")
    public SportEntity updateSport(
            @PathVariable UUID sportId,
            @RequestBody SportDTO sportDTO,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email") String userEmail,
            @RequestHeader("X-User-Type") Long userType
    ) throws Exception
    {
        if (validadeUserType(userType)) throw new AuthenticationException("Usuário sem nível de acesso");
        return sportService.updateSport(sportId, sportDTO);
    }
}
