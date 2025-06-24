package br.edu.atitus.sportservice.controllers;

import br.edu.atitus.sportservice.DTOs.SportDTO;
import br.edu.atitus.sportservice.entities.SportEntity;
import br.edu.atitus.sportservice.services.SportService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sports")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @PostMapping("/create")
    public ResponseEntity<SportEntity> createSport(@RequestBody SportDTO sportDTO) throws Exception {
        SportEntity sport = new SportEntity();
        BeanUtils.copyProperties(sportDTO, sport);

        var created = sportService.createSport(sport);
        return ResponseEntity.ok(created);
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
    public void deleteSport(@PathVariable UUID sportId) {
        sportService.deleteSport(sportId);
    }

    @PutMapping("/update/{sportId}")
    public SportEntity updateSport(@PathVariable UUID sportId, @RequestBody SportDTO sportDTO) throws Exception {
        return sportService.updateSport(sportId, sportDTO);
    }
}
