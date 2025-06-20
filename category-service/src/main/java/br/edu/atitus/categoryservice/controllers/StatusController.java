package br.edu.atitus.categoryservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ping")
public class StatusController {

    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong");
    }
}
